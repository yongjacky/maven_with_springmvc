package com.nexstream.helloworld.service;

import java.util.List;

import com.nexstream.helloworld.entity.Image;

public interface ImageService {
	final static String beanName="imageService";

	public List<Image> geImages()throws Exception;
	public Image saveOrUpdate(Image image)throws Exception;
	public Image getImage(Long id)throws Exception;
	public void deleteImage(Long id)throws Exception;
	public List<Image> getImagesByLoginId(String loginId)throws Exception;

}
