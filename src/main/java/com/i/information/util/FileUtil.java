package com.i.information.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	/**
	 * 
	 * 创建目录
	 * 
	 * @param path 文件目录路径
	 * 
	 * @return  路径已经是文件夹时返回true，文件夹创建成功时返回true 路径不是是文件夹，文件夹创失败时返回false
	 * */
	public static boolean createfolder(String path){
		if(new File(path).isDirectory()){
			return true;
		}else{
			if(!new File(path).isFile()){
				new File(path).mkdirs();
			}
			if(new File(path).isDirectory()){
				return true;
			}
		}
		return false;
	}
	
	
	/****
	 * 文件拷贝
	 *
	 *@param fp1 源文件
	 *
	 *@param fp2 目标文件
	 *
	 *@return 成功返回true  失败返回false
	 * 
	 * */
	public static boolean filecopy(String fp1,String fp2){
		FileInputStream in=null;
		FileOutputStream out=null;
		try {
			in=new FileInputStream(new File(fp1));
			File f2=new File(fp2);
			
			 out=new FileOutputStream(f2);
			
			byte[] b=new byte[4096];
			
			try {
				int len=0;
				while((len=in.read(b))>0){
					
					out.write(b,0,len);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException();
			}
			
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			
			if(f2.canRead()){
				return true;
				
			}else{
				return false;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			
			return false;
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			
		}
	}
	
	
	/**
	 * 下载文件到服务器
	 * 
	 * @param url 文件在网络地址
	 * 
	 * @param filepath 保存的文件路径
	 * 
	 * @return 成功 true 失败 false
	 * 
	 * */
	public static boolean downFile(String url,String filepath){
		FileOutputStream out=null;
		InputStream ins=null;
		try{
		URL u = new URL(url);
        URLConnection con = u.openConnection();
         out = new FileOutputStream(filepath);
         ins = con.getInputStream();
        byte[] b = new byte[1024];
        int i=0;
        while((i=ins.read(b))!=-1){
            out.write(b, 0, i);
        }
        ins.close();
        out.close();
        return true;
        
		}catch(Exception e){
			return false;
			
		}finally{
			try {
				ins.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
			try{
	        out.close();
			}catch(IOException e){}
			
		}
	}
	
	
	
	
	
	
	/**
	 * 
	 * 写文件        多线程读写一个文件安全 阻塞同步
	 * */
	public static boolean writefile(String file,String buf,boolean isappend,String charSet) throws IOException{
		if(charSet==null)charSet="UTF-8";
        Writer fw=null;
		try {
            fw = new BufferedWriter(//同步
                    new OutputStreamWriter(
                            new FileOutputStream(file, isappend), charSet));
            fw.write(buf);
            fw.flush();
        }catch (Exception e){
		    throw new RuntimeException(e);
        }
        finally {
            fw.close();
        }

		return true;
	}

    public static String readFileString(String fp, String charSet) throws IOException {
        if (charSet == null) charSet = "UTF-8";
        StringBuilder sb = new StringBuilder();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fp), charSet));
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);

            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        finally {
            br.close();
        }

        return sb.toString();
    }

        /**
         *
         * 读文件   多线程读写一个文件安全 阻塞同步
         * */
	public static List<String> readFileLine(String fp, String charSet) throws IOException{
		if(charSet==null)charSet="UTF-8";
		List<String> res=new ArrayList();
        BufferedReader br=null;
        try {
            br=new BufferedReader(new InputStreamReader(new FileInputStream(fp), charSet));
            String s;
            while((s=br.readLine())!=null){
                res.add(s);

            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        finally {
            br.close();
        }
		return res;
	}
	
	
	
	
	
	
	
	
	/**
	 * 
	 * 删除文件
	 * */
	public static boolean delfile(String fp){
		File f=new File(fp);
		if(f.isFile()){
			f.delete();
		}
		return true;
	}
	
	/**
	 * 
	 * 删除文件夹
	 * @throws IOException 
	 * 
	 * */
	public static boolean delfolder(String fp) throws IOException{
		File f=new File(fp);
		
		if(f.isDirectory()){
			if(f.listFiles().length==0){
				f.delete();
			}else{
				for (File f1:f.listFiles()) {
					delfolder(f1.getCanonicalPath());
					
	            }
				f.delete();
			}
		}else{
			f.delete();
		}
		return true;
	}
	
}
