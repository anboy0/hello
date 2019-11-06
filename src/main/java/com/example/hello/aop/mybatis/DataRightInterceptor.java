package com.example.hello.aop.mybatis;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class,method = "prepare",args = {Connection.class,Integer.class})})
public class DataRightInterceptor implements Interceptor {


    public static DataRightAop getPremissionByDelegate(MappedStatement mappedStatement){
        DataRightAop dataRightAop = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0,id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".")+1,id.length());
            final Class cls = Class.forName(className);
            final Method[] methods = cls.getMethods();
            for (Method me :methods){
                if(me.getName().equals(methodName) && me.isAnnotationPresent(DataRightAop.class)){
                    dataRightAop = me.getAnnotation(DataRightAop.class);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return dataRightAop;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        DataRightAop dataRightAop = getPremissionByDelegate(mappedStatement);
        if(dataRightAop == null){
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql)metaObject.getValue("delegate.boundSql");
        String originalSql = boundSql.getSql();
        StringBuilder sbSql = new StringBuilder(originalSql);
        //查询记录ID为1的数据 判断条件无所谓
        Integer id = 1;
        if(id != null && id.intValue() == 1 ){
            sbSql = new StringBuilder("select * from (").append(originalSql).append(")  t1 where t1.id = 2");
        }
        metaObject.setValue("delegate.boundSql.sql",sbSql.toString());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof StatementHandler){
            return Plugin.wrap(target,this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
