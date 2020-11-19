package itss.nhom7.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IUserDAO;
import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.IUserService;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private IUserDAO userDao;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean checkLogin(User user) {

		User userfind = userDao.findByUserName(user.getUserName());

		if (userfind == null) {
			return false;

		} else {

			if (user.getPassword().equals(userfind.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}

//	@Override
//	public void addUser(User user) {
//
//		userDao.addUser(user);
//
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDao.findByUserName(username);

		if (user == null) {

			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		String role = user.getRole();

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		GrantedAuthority authority = new SimpleGrantedAuthority(role);

		grantList.add(authority);

		boolean enable = user.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantList);

		return userDetails;
	}

	@Override
	public User findByUsername(String username) {

		return userDao.findByUserName(username);
	}

	@Override
	public void applyNewPassword(User user) {

		User userUpdate = userDao.findByEmail(user.getEmail());

		String passRandom = RandomStringUtils.randomAlphanumeric(8);

		if (user.getUserName().equals(userUpdate.getUserName())) {
			userUpdate.setPassword(passRandom);
			userDao.save(userUpdate);

			SimpleMailMessage message = new SimpleMailMessage();

			message.setTo(userUpdate.getEmail());
			message.setSubject("Change Password");
			message.setText("Hello, We are Aims!\n Your new password is : " + passRandom);

			// Send Message!
			this.emailSender.send(message);
		}

	}

	@Override
	public void editUser(UserModel userModel) {

		User user = userDao.findByEmail(userModel.getEmail());

		user.setUserName(userModel.getUserName());
		user.setFullName(userModel.getFullName());
		user.setAvataUrl(userModel.getAvataUrl());
		user.setAddress(userModel.getAddress());
		user.setPhone(userModel.getPhone());

		userDao.save(user);

	}

	@Override
	public UserModel getUser(int id) {

		return modelMapper.map(userDao.getOne(id), UserModel.class);

	}

}
