package com.nexstream.helloworld.dao;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.nexstream.helloworld.entity.Image;

@Repository(ImageDaoImpl.beanName)
public class ImageDaoImpl implements ImageDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public List<Image> geImages()throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		@SuppressWarnings("unchecked")
		List<Image> images = session.createQuery("from Image").list();
		ListIterator<Image> iter = images.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		return images;
	}
	
	public Image saveOrUpdate(Image image)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(image);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String dateTime = df.format(image.getTimeStamp());
		
		@SuppressWarnings("unchecked")
		List<Image> images = session.createQuery("FROM Image im WHERE im.timeStamp = '" + dateTime+"'").list();
		if (images.size()>0)
			image = images.get(0);
		
		return image;

	}
	
	public Image getImage(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Image image = (Image) session.get(Image.class, id);
		return image;
	}
	
	public void deleteImage(Long id)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Image image = (Image) session.get(Image.class, id);
		
		if (image!=null)
			session.delete(image);
	}
	
	public List<Image> getImagesByLoginId(String loginId)throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Image> images = session.createQuery("FROM Image im WHERE im.loginId = '" + loginId +"'").list();
		ListIterator<Image> iter = images.listIterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		return images;
	}
}
