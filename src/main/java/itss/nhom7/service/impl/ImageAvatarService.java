package itss.nhom7.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IUserDAO;
import itss.nhom7.entities.User;
import itss.nhom7.service.IImageAvatarService;
@Service
public class ImageAvatarService implements IImageAvatarService{
	
	@Autowired
	private IUserDAO userDao;

	@Override
	public void saveFile(String imageUrl, String email){
		User user = userDao.findByEmail(email);
		user.setImageUrl(imageUrl);
		user.setUpdateAt(new Date());
		userDao.saveAndFlush(user);
	}
}
