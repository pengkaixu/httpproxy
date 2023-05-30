package com.i.information.proxy;

import com.i.information.util.Httpclient;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: 2021/12/13 0013 10:45
 * @Description:
 */
public class MainInterceptor implements HandlerInterceptor {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MainInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle){

        String url=null;
        Map<String,String> map=new HashMap<>();

        String name;
        String uri=request.getRequestURI();
        String adder=request.getHeader("proxy-Adder");
        if(adder==null||adder.isEmpty()){
            return false;
        }
        String scheme=request.getHeader("proxy-Scheme");
        if(scheme==null||scheme.isEmpty()){
            return false;
        }
        String method=request.getMethod().toUpperCase();
        if(method.equals("GET")){
            String queryString=request.getQueryString();
            if(queryString!=null&&!queryString.isEmpty()){
                url=String.format("%s://%s/%s?%s",scheme,adder,uri,queryString);
            }
        }
        if(url==null){
            url=String.format("%s://%s/%s",scheme,adder,uri);
        }
        Enumeration enumeration =request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            name=(String)enumeration.nextElement();
            map.put(name,request.getHeader(name));
        }
        map.put("host",adder);
        logger.info("proxy time[{}] url[{}] method[{}]",System.currentTimeMillis(),url,method);
        try {
            Httpclient.sendClient(url, method,map, request.getInputStream(), response);
        }catch (Exception e){
            logger.error("error proxy ",e);
        }
        return false;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response,
//                           Object handle, ModelAndView modelAndView)throws Exception{
//        //运行某个方法
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
//                                Object handle, Exception e)throws Exception {
//        //完成某个方法
//    }
}
