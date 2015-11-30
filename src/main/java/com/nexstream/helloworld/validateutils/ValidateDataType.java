package com.nexstream.helloworld.validateutils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateDataType {
	public static Boolean validateDataType(String value, Object type){
		Boolean isValid = true;
		
		try{
			if (type instanceof Integer){
				Integer.parseInt(value);
				return isValid;
			}
			if(type instanceof Double){
				Double.parseDouble(value);
				return isValid;
			}
			if(type instanceof Date){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				@SuppressWarnings("unused")
				Date date = df.parse(value);
				return isValid;
			}
		}
		catch(Exception ex){
			isValid = false;
		}
		return isValid;
	}
	
	public static Boolean validateTimeStamp(String value, Object type){
		Boolean isValid = true;
		
		try{
				if(type instanceof Date){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
				@SuppressWarnings("unused")
				Date date = df.parse(value);
				return isValid;
			}
		}
		catch(Exception ex){
			isValid = false;
		}
		return isValid;
	}
}
