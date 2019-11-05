package com.example.hello.config.webfilter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

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

        //方法一：字符串读取
//        BufferedReader br = null;
//        StringBuilder sb = new StringBuilder("");
//        try {
//            br = request.getReader();
//            String str = "";
//            while((str = br.readLine()) != null){
//                sb.append(str);
//            }
//            br.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        } finally {
//            if(null != br){
//                try {
//                    br.close();
//                } catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//        System.out.println("字符串的方式读取request的body:"+sb.toString());
        //方法二： 二进制读取
        int len = request.getContentLength();
        byte[] buffer = new byte[len];
        ServletInputStream in = null;
        try {
            in = request.getInputStream();
            in.read(buffer,0,len);
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("buffer:"+new String(buffer));

        filterChain.doFilter(request,servletResponse);
    }
}
