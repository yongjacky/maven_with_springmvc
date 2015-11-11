package com.nexstream.helloworld.controller;
 
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class HelloWorldController {
	String message = "Welcome to Spring MVC!";
	String message3 = "try 3";
	String newMessage = "Added by Jacky";//Added by Jacky Y. 11.Nov.15.1307
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(
			//@RequestParam(value = "name", required = false, defaultValue = "World") String name
			//@RequestParam(value = "message2", required = false, defaultValue = "message2") String message2
			@RequestParam Map<String, String> requestparam
			) {
		System.out.println("in controller");
		System.out.println("changes 2");
		
		String name = requestparam.get("name");
		String message2 = requestparam.get("message2");
		//String message4 = requestparam.get("message4");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		mv.addObject("message2", message2);
		mv.addObject("message3", message3);
		//mv.addObject("message4", message4);
		return mv;
	}
	
	@RequestMapping("/bello")
	public ModelAndView showMessage2(
			@RequestParam Map<String, String> requestparam2){
		String message4 = requestparam2.get("message4");
		
		ModelAndView mv2 = new ModelAndView("helloworld");
		mv2.addObject("message4", message4);
		return mv2;
	}
	
}
