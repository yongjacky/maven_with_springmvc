package com.nexstream.helloworld.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nexstream.helloworld.domains.BaseResp;

@RestController
public class FileController {
	ApplicationContext context = new ClassPathXmlApplicationContext("springModules.xml");
	
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("name") String name, @RequestParam("file") MultipartFile file)throws Exception{
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String filePath = new String(context.getMessage("path.upload", null, Locale.US));
                File dir = new File(filePath);
                if (!dir.exists()) System.out.println("directory not exist");
 
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()+ File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                
                BaseResp resp = new BaseResp();
                resp.setCode("200");
                resp.setMessage("file upload success");
                return resp;
            } catch (Exception e) {
            	System.out.println("exception throwed");
            }
        }
		else {
        	BaseResp resp = new BaseResp();
        	resp.setCode("500");
            resp.setMessage("file upload not success/ empty file");
            return resp;
        }
		
		BaseResp resp = new BaseResp();
    	resp.setCode("500");
        resp.setMessage("program got error");
        return resp;
	}
	
    @RequestMapping(value = "/displayFileGetResponse/{name}", method = RequestMethod.GET)
    @ResponseBody
    public void displayFileGetResponse(@PathVariable String name, HttpServletResponse response ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        if (!dir.exists()) System.out.println("directory not exist");

        File serverFile = new File(dir.getAbsolutePath()+ File.separator + name +".png");
        System.out.println("serverFile: "+serverFile);
        InputStream in = new FileInputStream(serverFile);
        byte[] byteArray = IOUtils.toByteArray(in);
        response.setContentType( "image/png" );
        response.getOutputStream().write( byteArray );
    }
    
    @RequestMapping(value = "/displayFilePostResponse", method = RequestMethod.POST)
    @ResponseBody
    public void displayFilePostResponse(@RequestParam("name") String name, HttpServletResponse response ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        if (!dir.exists()) System.out.println("directory not exist");

        File serverFile = new File(dir.getAbsolutePath()+ File.separator + name +".png");
        System.out.println("serverFile: "+serverFile);
        InputStream in = new FileInputStream(serverFile);
        byte[] byteArray = IOUtils.toByteArray(in);
        response.setContentType( "image/png" );
        response.getOutputStream().write( byteArray );
    }
    
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteFile(@RequestParam("name") String name ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        BaseResp resp = new BaseResp();
        resp.setCode("200");
        
        if (!dir.exists()) System.out.println("directory not exist");

        File serverFile = new File(dir.getAbsolutePath()+ File.separator + name +".png");
        if(serverFile.delete()){
        resp.setMessage("delete success");
        return resp;
        }
        
        resp.setMessage("delete fail");
        return resp;
     }


}	
	
