package com.chinaoly.frm.core.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class AjaxUtil {
	protected static Logger log = LoggerFactory.getLogger(AjaxUtil.class);
	
	public static void responseOut(HttpServletResponse response,Object obj) {  
		String json = new Gson().toJson(obj);
		response.setContentType("application/json; charset=utf-8");  
        PrintWriter out = null;  
        try {  
            out = response.getWriter();  
            out.append(json);  
        } catch (IOException e) {  
            log.info(e.getStackTrace().toString());  
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
    }  
    
	public static void responseDownload(HttpServletResponse response,String fileName){
		response.setContentType("APPLICATION/OCTET-STREAM");     
        response.setHeader("Content-Disposition","attachment;filename=\"" + fileName + "\"");
        final int BUFSIZE = 1024;
        byte[] by = new byte[BUFSIZE];
        
        File file = new File(fileName);
        PrintWriter out = null;  
        InputStreamReader is = null;
        FileInputStream fis = null;
        try {  
            fis = new FileInputStream(file);
            while(fis.read(by)>=0){
            	response.getOutputStream().write(by);
            }
            
        } catch (IOException e) {  
            log.info(e.getStackTrace().toString());  
        } finally {  
        	if(fis != null){
                try {
					fis.close();
				} catch (IOException e) {
					log.error(e.getStackTrace().toString());
				}
        	}
            if (out != null) {  
            	try {
            		out.close();
            	} catch (Exception e){
            		log.error(e.getStackTrace().toString());
            	}
            }  
            if(is!=null){
            	try {
					is.close();
				} catch (IOException e) {
					log.error(e.getStackTrace().toString());
				}  
            }
        }  
	}
	
}
