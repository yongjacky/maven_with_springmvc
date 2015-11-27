package com.nexstream.helloworld.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexstream.helloworld.dao.ImageDao;
import com.nexstream.helloworld.entity.Image;

//@Service(ImageServiceImpl.beanName)
@Service(ImageServiceImpl.beanName)
public class ImageServiceImpl implements ImageService{
	@Resource
	private ImageDao imageDao;

	@Transactional
	public List<Image> geImages()throws Exception{
		return imageDao.geImages();
	}
	
	@Transactional
	public Image saveOrUpdate(Image image)throws Exception{
		return imageDao.saveOrUpdate(image);
	}
	
	@Transactional
	public Image getImage(Long id)throws Exception{
		return imageDao.getImage(id);
	}
	
	@Transactional
	public void deleteImage(Long id)throws Exception{
		imageDao.deleteImage(id);
	}
	
	@Transactional
	public List<Image> getImagesByLoginId(String loginId)throws Exception{
		return imageDao.getImagesByLoginId(loginId);
	}
}
