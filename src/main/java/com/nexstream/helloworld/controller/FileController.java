package com.nexstream.helloworld.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
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
import com.nexstream.helloworld.entity.Image;
import com.nexstream.helloworld.service.ImageService;

@RestController
public class FileController {
	ApplicationContext context = new ClassPathXmlApplicationContext("springModules.xml");
	
	@Resource
	private ImageService imageService;
	
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("name") String name, @RequestParam("loginId") String loginId,@RequestParam("file") MultipartFile file)throws Exception{
		if (!file.isEmpty()) {
			
			Image image = new Image();
			image.setFileName(name);
			image.setTimeStamp(new Date());
			image.setLoginId(loginId);
			
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String filePath = new String(context.getMessage("path.upload", null, Locale.US));
                File dir = new File(filePath);
                if (!dir.exists()) System.out.println("directory not exist");
                
                //save record to database
                image.setFilePath(dir.getAbsolutePath()+ File.separator + name);
                Image imageTemp = imageService.saveOrUpdate(image);
 
                // Create the file
                File serverFile = new File(dir.getAbsolutePath()+ File.separator + imageTemp.getId()+".png");
                
                //write the file
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                
                
                BaseResp resp = new BaseResp();
                resp.setCode("200");
                resp.setMessage("file upload success, and image id= "+imageTemp.getId());
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
	
	//display file using get method
    @RequestMapping(value = "/displayFileGetResponse/{name}", method = RequestMethod.GET)
    @ResponseBody
    public void displayFileGetResponse(@PathVariable String name, HttpServletResponse response ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        if (!dir.exists()) System.out.println("directory not exist");
        File serverFile = new File(dir.getAbsolutePath()+ File.separator + name +".png");
        InputStream in = new FileInputStream(serverFile);
        byte[] byteArray = IOUtils.toByteArray(in);
        response.setContentType( "image/png" );
        response.getOutputStream().write( byteArray );
    }
    
    //display file using post method
    @RequestMapping(value = "/displayFilePostResponse", method = RequestMethod.POST)
    @ResponseBody
    public void displayFilePostResponse(@RequestParam("id") Long id, HttpServletResponse response ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        if (!dir.exists()) System.out.println("directory not exist");

        //get file by passing the path
        File serverFile = new File(dir.getAbsolutePath()+ File.separator + id +".png");
        //convert file to byte
        InputStream in = new FileInputStream(serverFile);
        byte[] byteArray = IOUtils.toByteArray(in);
        //set the expected content to user 
        response.setContentType( "image/png" );
        //display the image to user
        response.getOutputStream().write( byteArray );
    }
    
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteFile(@RequestParam("id") Long id ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload", null, Locale.US));
        File dir = new File(filePath);
        BaseResp resp = new BaseResp();
        resp.setCode("200");
        
        if (!dir.exists()) System.out.println("directory not exist");

        //get file by input path 
        File serverFile = new File(dir.getAbsolutePath()+ File.separator + id +".png");
        //delete record from database
        imageService.deleteImage(id);
        //delete file
        if(serverFile.delete()){
        resp.setMessage("delete success");
        return resp;
        }
        
        resp.setMessage("delete fail");
        return resp;
     }

    @RequestMapping(value="/getImageByLoginId", method=RequestMethod.POST)
	@ResponseBody
	public List<Image> getImageByLoginId(@RequestParam("loginId") String loginId )throws Exception{
		return imageService.getImagesByLoginId(loginId);
	}


}	
	
