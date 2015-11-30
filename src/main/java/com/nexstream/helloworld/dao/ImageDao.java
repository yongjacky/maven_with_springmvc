package com.nexstream.helloworld.dao;

import java.util.List;

import com.nexstream.helloworld.entity.Image;

public interface ImageDao {
	public final String beanName = "imageDao";
	public List<Image> geImages()throws Exception;
	public Image saveOrUpdate(Image iamge)throws Exception;
	public Image getImage(Long id)throws Exception;
	public void deleteImage(Long id)throws Exception;
	public List<Image> getImagesByLoginId(String loginId)throws Exception;
	public Image getImageByLoginIdAndId(String loginId, Long id) throws Exception;
}
