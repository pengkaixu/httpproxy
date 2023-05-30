package com.i.information.util;

import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Auther: peng
 * @Date: 2020/9/30 0030 20:25
 * @Description:
 */
public class Httpclient {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Httpclient.class);
    //text/html ： HTML格式
    //text/plain ：纯文本格式
    //text/xml ： XML格式
    //image/gif ：gif图片格式
    //image/jpeg ：jpg图片格式
    //image/png：png图片格式
    //以application开头的媒体格式类型：
    //
    //application/xhtml+xml ：XHTML格式
    //application/xml： XML数据格式
    //application/atom+xml ：Atom XML聚合格式
    //application/json： JSON数据格式
    //application/pdf：pdf格式
    //application/msword ： Word文档格式
    //application/octet-stream ： 二进制流数据（如常见的文件下载）
    //application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
    //另外一种常见的媒体格式是上传文件之时使用的：
    //multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
    ///-------------------------------------util--------------------------------------

    public  static void sendClient(String url,String method, Map<String,String> requestHeader, InputStream in, HttpServletResponse response){
        HttpURLConnection conn =null;
        OutputStream connOutStream=null;
        OutputStream out=null;
        try {
            byte[] b=new byte[4096];
            int num;
            URL url1 = new URL(url);
            conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);//主机的超时时间
            conn.setReadTimeout(60000);//从主机读取数据的超时时间
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(method);

            for(Map.Entry<String,String> en:requestHeader.entrySet()){
                conn.setRequestProperty(en.getKey(),en.getValue());
            }

            if(in!=null&&method.equals("POST")){
                try {
                    int l=0;
                    connOutStream = conn.getOutputStream();
                    {
                        while ((num = in.read(b)) != -1) {
                            connOutStream.write(b, 0, num);
                            l+=num;
                        }
                    }
                    if(l>0) connOutStream.flush();
                }catch (Exception e){
                    throw new RuntimeException("connOutStream.write error",e);
                }finally {
                    try {
                        connOutStream.close();
                    }catch (Exception e){}
//                    try{
//                        in.close();
//                    }catch (Exception e){}
                }
            }
            //获取返回信息
            int code=conn.getResponseCode();
            if(code>=200&&code<400){
                response.setStatus(code);
                try {
                    StringBuilder value;
                    int len;
                    Map<String, List<String>> map = conn.getHeaderFields();
                    for (Map.Entry<String, List<String>> en : map.entrySet()) {
                        value=new StringBuilder();
                        for(String s:en.getValue()){
                            value.append(s);
                            value.append(",");
                        }
                        len=value.length();
                        response.addHeader(en.getKey(),len>0?value.substring(0,len-1):value.toString());
                    }
                }catch (Exception e){}

                try {
                    InputStream responseInputStream=conn.getInputStream();
                    out = response.getOutputStream();
                    while ((num = responseInputStream.read(b)) != -1) {
                        out.write(b, 0, num);
                    }
                    out.flush();
                }catch (Exception e){}
            }else{
                response.setStatus(code);
                response.getWriter().write(conn.getResponseMessage());
            }

        } catch (Exception e) {
            logger.error("request error",e);
            throw new RuntimeException("request error",e);
        }finally {
            try {
                conn.disconnect();
            }catch (Exception e){}
//            try {
//                out.close();
//            }catch (Exception e){}
        }
    }

//    public static void main(String[] args) throws Exception{
//
//            URL httpurl=new URL( "http://www.txt81.com//Zip_90723/%E6%88%91%E8%A2%AB%E5%A5%B3%E5%8F%8B%E9%99%84%E8%BA%AB%E4%BA%86.zip");
//        HttpClient httpClient=HttpClient.New(httpurl);
//
//            HttpURLConnection httpConn=(HttpURLConnection)httpurl.openConnection();
//            httpConn.setDoOutput(true);// 使用 URL 连接进行输出
//            httpConn.setDoInput(true);// 使用 URL 连接进行输入
//            httpConn.setUseCaches(false);// 忽略缓存
//            httpConn.setRequestMethod("GET");// 设置URL请求方法
//            //可设置请求头
//            httpConn.setRequestProperty("Content-Type", "application/octet-stream");
//            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
//            httpConn.setRequestProperty("Charset", "UTF-8");
//
//        InputStream inputStream=httpConn.getInputStream();
//
//        byte[] b=new byte[4096];
//        int num;
//        InputStreamReader reader=new InputStreamReader(inputStream,"utf-8");
//        char[] cc=new char[4];
//        StringBuilder sb=new StringBuilder();
//        while(reader.read(cc)!=-1){
//            sb.append(cc);
//        }
//        System.out.println(sb);
//    }
}
