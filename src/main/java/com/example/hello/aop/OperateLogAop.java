package com.example.hello.aop;


import com.alibaba.fastjson.JSONObject;
import com.example.hello.aop.log.LogObjectHolder;
import com.example.hello.aop.log.bean.AbstractLogDict;
import com.example.hello.aop.log.bean.OperateLogBean;
import com.example.hello.aop.log.enums.FunctionName;
import com.example.hello.aop.log.enums.OperateType;
import com.example.hello.util.CompareUtil;
import com.example.hello.util.ReflectUtil;
import io.swagger.annotations.ApiModelProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AOP记录操作日志
 */
@Aspect
@Component
public class OperateLogAop {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogAsyncTask logAsyncTask;

    @Autowired
    private MessageSource messageSource;

    //拦截方法queryById 保存查询结果 等修改时记录日志用到
    @AfterReturning(returning = "result", pointcut = "execution(* com.example.hello.controller.*.queryById(..))")
    public void afterReturning(Object result) throws Throwable {
        LogObjectHolder.me().set(result);
    }


    //controller层切点
    @Pointcut(value = "@annotation(com.example.hello.aop.OperateLogAspect)")
    public void controllerAspect() {

    }

    //环绕通知 拦截controller层 记录用户的操作日志
    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();

        try {
            handle(point);
        } catch (Exception e) {
            logger.error("AOP写日志报错", e);
        }
        return result;
    }

    //记录操作日志的业务处理
    private void handle(ProceedingJoinPoint point) throws Exception {
        //aop拦截记录操作日志
        Signature sig = point.getSignature();
        MethodSignature msig = null;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        //获取拦截方法的参数
        Object[] params = point.getArgs();
        String[] paramNames = ((CodeSignature) point.getSignature()).getParameterNames();
        Map<String, String> obj2 = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object param = params[i];
            String paramsStr = null;
            if (param.getClass().isArray()) {
                Object[] objs = (Object[]) param;
                StringBuilder builder = new StringBuilder();
                int n = 0;
                for (Object record : objs) {
                    n++;
                    builder.append(String.valueOf(record));
                    if (n < objs.length) {
                        builder.append(",");
                    }
                }
                paramsStr = builder.toString();
            } else if (param instanceof ArrayList) {
                ArrayList<Object> list = (ArrayList) param;
                StringBuilder builder = new StringBuilder();
                int n = 0;
                for (Object record : list) {
                    n++;
                    builder.append(String.valueOf(record));
                    if (n < list.size()) {
                        builder.append(",");
                    }
                }
                paramsStr = builder.toString();
            } else {
                paramsStr = String.valueOf(params[i]);
            }
            obj2.put(paramNames[i], paramsStr);
        }

        OperateLogAspect annotation = currentMethod.getAnnotation(OperateLogAspect.class);
        String keyField = "id";
        if (params != null && params.length > 0) {
            Object object = params[0];
            Class<?> aClass = null;
            if (object.getClass().isArray()) {
                Object[] array = (Object[]) object;
            } else if (object instanceof ArrayList) {
                ArrayList<Object> list = (ArrayList<Object>) object;
                aClass = list.get(0).getClass();
            } else {
                aClass = object.getClass();
            }
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(annotation.key())) {
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    keyField = apiModelProperty != null ? apiModelProperty.value() : "id";
                }
            }
            obj2.putAll(ReflectUtil.objectToMap(params[0]));
        }
        // 获取操作名称
        FunctionName functionName = annotation.functionName();
        OperateType operateType = annotation.operateType();
        String key = annotation.key();
        Class dictClass = annotation.dict();

        //如果涉及到修改 比对变化
        JSONObject msgJsonObj = null;
        AbstractLogDict logDict = (AbstractLogDict) dictClass.newInstance();
        if (OperateType.UPDATE.equals(operateType)) {
            Object obj1 = LogObjectHolder.me().get();
            if (obj1 != null) {
                msgJsonObj = CompareUtil.compareObj(dictClass, key, obj1, obj2);
            } else {
                msgJsonObj = CompareUtil.parseKey(logDict, key, obj2);
            }
        } else {
            msgJsonObj = CompareUtil.parseKey(logDict, key, obj2);
        }

        String username = "张三";
        String operatorType = operateType.getMessage();
        String functionNameType = functionName.getMessage();

        OperateLogBean logBean = new OperateLogBean();
        logBean.setFunctionName(functionNameType);
        logBean.setOperateType(operatorType);
        logBean.setCreateByName(username);
        logBean.setCreateTime(new Date());
        StringBuilder builder = new StringBuilder();
        builder.append(logBean.getCreateByName())
                .append(logBean.getOperateType())
                .append("[" + logBean.getFunctionName() + "]")
                .append(keyField + ":")
                .append("\'" + obj2.get(key) + "\'" + "的记录");
        logBean.setDescribe(builder.toString());
        //仅仅记录接口参数
        ArrayList<Object> list = new ArrayList<>();
        for (Object param : params) {
            if (param instanceof MultipartFile) {
                continue;
            } else if (param instanceof HttpServletRequest) {
                continue;
            } else if (param instanceof HttpServletResponse) {
                continue;
            } else if (param instanceof MultipartFile[]) {
                continue;
            }
            list.add(param);
        }
        logBean.setMsg(JSONObject.toJSONString(list));
        logBean.setCompareValue(msgJsonObj.toString());
        logAsyncTask.saveLog(logBean, "t_operate_log");

    }

}
