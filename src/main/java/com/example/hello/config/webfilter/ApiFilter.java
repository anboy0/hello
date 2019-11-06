package com.example.hello.config.webfilter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Component
public class ApiFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if(!request.getRequestURI().startsWith("/v1/api")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //拦截所有以api开头的接口
        String val = request.getParameter("name");
        System.out.println(val);
        MultiReadHttpServletRequest requestWrapper = new MultiReadHttpServletRequest(request);

        //方法一：字符串读取
        StringBuilder sb = new StringBuilder("");
        try( BufferedReader br = requestWrapper.getReader()) {
            String str = "";
            while((str = br.readLine()) != null){
                sb.append(str);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("字符串的方式读取request的body:"+sb.toString());
        Map mapTypes = JSONObject.parseObject(sb.toString());
        String name = String.valueOf(mapTypes.get("name"));
        System.out.println("过滤器中获取name："+name);
        //修改body的内容
        mapTypes.put("name","修改人");
        String str = mapTypes.toString();
        requestWrapper.setBody(str);
        //方法二： 二进制读取
//        int len = request.getContentLength();
//        byte[] buffer = new byte[len];
//        ServletInputStream in = null;
//        try {
//            in = request.getInputStream();
//            in.read(buffer,0,len);
//            in.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            if(null != in){
//                try {
//                    in.close();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("buffer:"+new String(buffer));

        filterChain.doFilter(requestWrapper,servletResponse);
    }
}
