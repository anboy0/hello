package com.example.hello.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.hello.aop.log.bean.AbstractLogDict;
import com.example.hello.aop.log.factory.ConstantWarpperFactory;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 比较工具类
 */
public class CompareUtil {

    public static final String separator = ";";

    /**
     * 比较两个对象，并输出不一致信息
     *
     * @param dictClass  日志字典
     * @param key        主键
     * @param oldDataObj 修改前的操作对象值
     * @param newDataMap 修改后的操作对象值
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static JSONObject compareObj(Class<?> dictClass, String key, Object oldDataObj, Map<String, String> newDataMap) throws IllegalAccessException, InstantiationException {
        AbstractLogDict logDict = (AbstractLogDict) dictClass.newInstance();
        JSONObject keyJsonObj = parseKey(logDict, key, newDataMap);
        JSONArray jsonArray = new JSONArray();
        try {
            Class<?> clazz = oldDataObj.getClass();
            Field[] fields = oldDataObj.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(oldDataObj);
                Object o2 = newDataMap.get(firstCharToLowerCase(getMethod.getName().substring(3)));
                if (o1 == null || o2 == null) {
                    continue;
                }
                if (o1 instanceof Date) {
                    o1 = DateUtil.getDay((Date) o1);
                }
                if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                    String fieldName = logDict.getFieldName(field.getName());
                    if (fieldName != null && !"".equals(fieldName)) {
                        JSONObject jsonObj = new JSONObject();
                        String methodName = logDict.getMethodName(field.getName());
                        if (methodName != null) {
                            Object o1Value = ConstantWarpperFactory.create(o1, methodName);
                            Object o2Value = ConstantWarpperFactory.create(o2, methodName);
                            jsonObj.put("字段名称", fieldName);
                            jsonObj.put("旧值", o1Value);
                            jsonObj.put("新值", o2Value);
                        } else {
                            jsonObj.put("字段名称", fieldName);
                            jsonObj.put("旧值", o1);
                            jsonObj.put("新值", o2);
                        }
                        jsonArray.add(jsonObj);
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        keyJsonObj.put("content", jsonArray);

        return keyJsonObj;
    }

    /**
     * 解析指定的key
     *
     * @param logDict    日志对象字典
     * @param key        主键
     * @param newDataMap 修改后的操作对象值
     * @return
     */
    public static JSONObject parseKey(AbstractLogDict logDict, String key, Map<String, String> newDataMap) {
        JSONObject jsonObj = new JSONObject();
        String value = getKey(logDict, key, newDataMap);
        jsonObj.put("key", logDict.getFieldName(key) + "=" + value);
        return jsonObj;
    }

    /**
     * 獲取主鍵的value值
     *
     * @param logDict
     * @param key
     * @param newDataMap
     * @return
     */
    public static String getKey(AbstractLogDict logDict, String key, Map<String, String> newDataMap) {
        Object obj = newDataMap.get(key);
        String fieldWarpperMethodName = logDict.getMethodName(key);
        if (fieldWarpperMethodName != null) {
            obj = ConstantWarpperFactory.create(newDataMap.get(key), fieldWarpperMethodName);
        }

        return String.valueOf(obj);
    }

    public static String getFieldName(Object obj,String key){
        Field f = null;
        try {
            f = obj.getClass().getDeclaredField(key);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(null == f){
            return "";
        }
        ApiModelProperty amp =  f.getAnnotation(ApiModelProperty.class);
        String value = amp.value();
        if(StringUtils.isNotEmpty(value)){
            int index = value.indexOf(",");
            if(index > 0){
                value = value.substring(0,index);
            }
        }
        return  value;
    }

    /**
     * 獲取主鍵的value值
     *
     * @param logDict
     * @param key
     * @param newDataMap
     * @return
     */
    public static String getKey(Object obj, String key) {
        Object result = null;
        try {
            Map<String, String> map = ReflectUtil.objectToMap(obj);
            result = map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null != result){
            return String.valueOf(result);
        }

        return null;
    }


    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 去掉指定后缀
     *
     * @param str    字符串
     * @param suffix 后缀
     * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
     */
    public static String removeSuffix(String str, String suffix) {
        if (str == null || "".equals(str)) {
            return str;
        }

        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }
}