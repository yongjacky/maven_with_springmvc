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

import org.apache.commons.io.FilenameUtils;
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
import com.nexstream.helloworld.entity.User;
import com.nexstream.helloworld.service.ImageService;
import com.nexstream.helloworld.service.UserService;

@RestController
public class FileController {
	ApplicationContext context = new ClassPathXmlApplicationContext("springModules.xml");
	
	@Resource
	private ImageService imageService;
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	@ResponseBody
	public Object uploadFile(@RequestParam("name") String name, @RequestParam("authToken") String authToken,@RequestParam("file") MultipartFile file)throws Exception{
		BaseResp resp = new BaseResp();
		if (!file.isEmpty()) {
			
			Image image = new Image();
			image.setFileName(name);
			image.setTimeStamp(new Date());
			
			User userDb = userService.getUserByAuthenticationToken(authToken);
			if(userDb==null){
				resp.setCode("500");
	            resp.setMessage(context.getMessage("token.status", new Object[] {"invalid"}, Locale.US));
	            return resp;
			}
			
			image.setLoginId(userDb.getLoginId());
			
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String filePath = new String(context.getMessage("path.upload.appCon", null, Locale.US));
                File dir = new File(filePath);
                if (!dir.exists()) dir.mkdir();
                
                //save record to database
                image.setFilePath(dir.getAbsolutePath()+ File.separator);
                Image imageTemp = imageService.saveOrUpdate(image);
                 
                // Create the file
                File serverFile = new File(dir.getAbsolutePath()+ File.separator + imageTemp.getId());
                
                //write the file
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                
                resp.setCode("200");
                resp.setMessage(context.getMessage("file.upload.status", new Object[] {"success","file id is "+imageTemp.getId()}, Locale.US));
                return resp;
            } catch (Exception e) {
            	resp.setCode("500");
                resp.setMessage("exception is throwed");
                return resp;
            }
        }
		else {
        	resp.setCode("500");
            resp.setMessage(context.getMessage("file.upload.status", new Object[] {"not success","empty file"}, Locale.US));
            return resp;
        }
	}
	
	//display file using get method
    @RequestMapping(value = "/displayFileGetResponse/{id}/{authToken}", method = RequestMethod.GET)
    @ResponseBody
    public Object displayFileGetResponse(@PathVariable Long id,@PathVariable String authToken, HttpServletResponse response ) throws Exception{
    	BaseResp resp = new BaseResp();
    	resp.setCode("500");
    	User userDb = userService.getUserByAuthenticationToken(authToken);
    	if(userDb==null){
            resp.setMessage(context.getMessage("token.status", new Object[] {"invalid"}, Locale.US));
            return resp;
		}
    	
    	Image imagesLoginId = imageService.getImageByLoginIdAndId(userDb.getLoginId(),id);
    	if(imagesLoginId!=null){
    	
	    	String filePath = new String(context.getMessage("path.upload.appCon", null, Locale.US));
	        File dir = new File(filePath);
	        if (!dir.exists()) System.out.println("directory not found");
	        File serverFile = new File(dir.getAbsolutePath()+ File.separator + id +".png");
	        InputStream in = new FileInputStream(serverFile);
	        byte[] byteArray = IOUtils.toByteArray(in);
	        response.setContentType( "image/png" );
	        response.getOutputStream().write( byteArray );
      	}
    	resp.setMessage(context.getMessage("database.status", null, Locale.US));
    	return resp;
    }
    
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteFile(@RequestParam("id") Long id, @RequestParam("authToken") String authToken ) throws Exception{
    	String filePath = new String(context.getMessage("path.upload.appCon", null, Locale.US));
        File dir = new File(filePath);
        BaseResp resp = new BaseResp();
        resp.setCode("200");
        
        if (!dir.exists()) System.out.println("directory not exist");

        User userDb = userService.getUserByAuthenticationToken(authToken);
    	if(userDb==null){
            resp.setMessage(context.getMessage("token.status", new Object[] {"invalid"}, Locale.US));
            return resp;
		}
    	
    	Image imagesLoginId = imageService.getImageByLoginIdAndId(userDb.getLoginId(),id);
    	if(imagesLoginId!=null){
	        //get file by input path 
	        File serverFile = new File(dir.getAbsolutePath()+ File.separator + id +".png");
	        //delete record from database
	        imageService.deleteImage(id);
	        //delete file
	        if(serverFile.delete()){
	        resp.setMessage("delete success");
	        return resp;
	        }
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
	
