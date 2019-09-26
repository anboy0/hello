package com.example.hello.util;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import org.springframework.context.MessageSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zs on 18-7-12.
 * 公共工具类
 */
public class CommonUtil {
    /**
     * 获取当前组户的唯一标示companyId
     *
     * @param
     * @return
     */
    public static String getCompanyId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes){
            return getCompanyId(requestAttributes.getRequest());
        }

        return null;
    }


    /**
     * 获取当前组户的唯一标示companyId
     *
     * @param request
     * @return
     */
    public static String getCompanyId(HttpServletRequest request) {
        String companyId = null;

        Object obj = request.getSession().getAttribute("companyId");
        if (null != obj) {
            companyId = String.valueOf(obj);
        }

        return companyId;
    }

    /**
     * 获取当前语言类型
     *
     * @return
     */
    public static Locale getLocale() {
        String language = "zh_CN";
        Locale local = Locale.CHINA;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes){
            Object obj = requestAttributes.getRequest().getSession().getAttribute("language");
            if (null != obj) {
                language = String.valueOf(obj);
            }
        }

        if("en_US".equals(language)){
            local =Locale.US;
        }

        return local;
    }

    /**
     * 获取当前语言类型
     * @return
     */
    public static String getLanguage() {
        String language = "zh_CN";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes){
            Object obj = requestAttributes.getRequest().getSession().getAttribute("language");
            if (null != obj) {
                language = String.valueOf(obj);
            }
        }

        return language;
    }

    /**
     * 获取mongodb多组户库
     *
     * @param request
     * @return
     */
//    public static String getMongodbName(HttpServletRequest request) {
//        String collectionName = CommonConstant.OPERATE_LOG_DB;
//        String companyId = getCompanyId(request);
//
//        if (null != companyId) {
//            collectionName += "_" + companyId;
//        }
//
//        return collectionName;
//    }

    /**
     * 获取mongodb多组户库
     *
     * @param request
     * @return
     */
//    public static String getMongodbName(HttpServletRequest request, String key) {
//        String collectionName =null;
//        //系统登录日志
//        if("sys_login_log".equals(key)){
//            collectionName = CommonConstant.SYS_LOGIN_LOG_DB;
//            //系统日志
//        }else if("sys_log".equals(key)) {
//            collectionName = CommonConstant.SYS_LOG_DB;
//            //设备日志
//        }else{
//            collectionName = CommonConstant.DERIVE_LOGIN_LOG_DB;
//        }
//        String companyId = getCompanyId(request);
//
//        if (null != companyId) {
//            collectionName += "_" + companyId;
//        }
//
//        return collectionName;
//    }

    /**
     * 判断是否包含中文字符
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if(m.find()){
            return true;
        }

        return false;
    }

    /**
     * 获取key的国际化资源文件内容
     * @param messageSource
     * @param key
     * @return
     */
    public static String getMessage(MessageSource messageSource, String key){
        if(!isContainChinese(key)){
            return messageSource.getMessage(key, null, getLocale());
        }

        return key;
    }

//    /**
//     * 解密druid密码
//     * @param password
//     * @return
//     */
//    public static String druidDecrypt(String password){
//        if(password.length()<15){//临时测试代码,需要删除的.
//            return password;
//        }
//
//        try {
//            password = ConfigTools.decrypt(CommonConstant.DRUID_PUBLIC_KEY, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return password;
//    }


    /**
     * 将DBObject转换成Bean对象
     */
    public static <T> T dbObjectToBean(DBObject dbObject, T bean) {
        if (bean == null) {
            return null;
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String varName = field.getName();
            Object object = dbObject.get(varName);
            if (object != null) {
                setProperty(bean, varName, object);
            }

        }
        return bean;
    }

    // 取出Mongo中的属性值，为bean赋值
    private static <T> void setProperty(T bean, String varName, T object) {
        varName = varName.substring(0, 1).toUpperCase() + varName.substring(1);
        try {
            String type = object.getClass().getName();
            // 类型为String
            if (type.equals("java.lang.String")) {
                Method m = bean.getClass().getMethod("get" + varName);
                String value = (String) m.invoke(bean);
                if (value == null) {
                    m = bean.getClass().getMethod("set" + varName, String.class);
                    m.invoke(bean, object);
                }
            }
            // 类型为Integer
            if (type.equals("java.lang.Integer")) {
                Method m = bean.getClass().getMethod("get" + varName);
                String value = (String) m.invoke(bean);
                if (value == null) {
                    m = bean.getClass().getMethod("set" + varName, Integer.class);
                    m.invoke(bean, object);
                }
            }
            // 类型为Boolean
            if (type.equals("java.lang.Boolean")) {
                Method m = bean.getClass().getMethod("get" + varName);
                String value = (String) m.invoke(bean);
                if (value == null) {
                    m = bean.getClass().getMethod("set" + varName, Boolean.class);
                    m.invoke(bean, object);
                }
            }
            // 类型为Boolean
            if (type.equals("java.util.Date")) {
                Method m = bean.getClass().getMethod("get" + varName);
                String value = (String) m.invoke(bean);
                if (value == null) {
                    m = bean.getClass().getMethod("set" + varName, Date.class);
                    m.invoke(bean, object);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将list转换为制定clazz类型list集合
     * @param list 原始list
     * @param clazz 转后后的List对象类型
     */
    public static<T> List<T>  toList(List list,Class<T> clazz){
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(list));
        return JSONObject.parseArray(jsonArray.toJSONString(), clazz);
    }
}


