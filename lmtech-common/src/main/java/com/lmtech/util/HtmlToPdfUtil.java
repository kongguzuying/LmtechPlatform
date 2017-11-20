package com.lmtech.util;
import java.io.File;

import javax.servlet.http.HttpServlet;

public class HtmlToPdfUtil extends HttpServlet{  
	
	private static final long serialVersionUID = -5513942926404693289L;
	//wkhtmltopdf在系统中的路径  
//    private static final String toPdfTool = "C:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";  
    
	//linux路径，windows或OS需另外下载并安装，重新设置路径   下载地址：http://wkhtmltopdf.org/downloads.html
    private static final String toPdfTool = PropertyUtil.getProperty("wkhtmltopdPath");  
      
    /** 
     * html转pdf 
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径 
     * @param destPath pdf保存路径 
     * @return 转换成功返回true 
     */  
    public static boolean convert(String srcPath, String destPath){  
        File file = new File(destPath);  
        File parent = file.getParentFile();  
        //如果pdf保存路径不存在，则创建路径  
        if(!parent.exists()){  
            parent.mkdirs();  
        }  
          
        StringBuilder cmd = new StringBuilder();  
        cmd.append(toPdfTool);  
        cmd.append(" ");  
        cmd.append(srcPath);  
        cmd.append(" ");  
        cmd.append(destPath);  
          
        boolean result = true;  
        try{  
            Process proc = Runtime.getRuntime().exec(cmd.toString());  
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());  
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());  
            error.start();  
            output.start();  
            proc.waitFor();  
        }catch(Exception e){  
            result = false;  
            e.printStackTrace();  
        }  
          
        return result;  
    }
    
}  