package itss.nhom7.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
import itss.nhom7.model.CartModel;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.IUserService;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean checkLogin(User user,Cookie[] cookies) {

		User userfind = userDao.findByEmail(user.getEmail());

		if (userfind == null) {
			return false;

		} else {

			if (user.getPassword().equals(userfind.getPassword())) {
				updateUserId(cookies, userfind.getId());
				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public UserModel addUser(UserModel userModel) throws SQLException {
		
		try {
			User userCheck = userDao.findByEmail(userModel.getEmail());
			if(userCheck==null) {
				User user = new User();
				user.setActive(true);
				user.setFullName(userModel.getFullName());
				user.setAvataUrl(userModel.getAvataUrl());
				user.setEmail(userModel.getEmail());
				user.setPassword(userModel.getPassword());
				user.setPhone(userModel.getPhone());
				user.setRole(userModel.getRole());
				user.setCreatedAt(Calendar.getInstance());

				userDao.saveAndFlush(user);
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		
		User userTmp = userDao.findByEmail(userModel.getEmail());
		UserModel userReturn = new UserModel();
		userReturn.setEmail(userTmp.getEmail());
		userReturn.setFullName(userTmp.getFullName());
		userReturn.setDateOfBirth(userTmp.getDateOfBirth());
		userReturn.setAvataUrl(userTmp.getAvataUrl());
		userReturn.setRole(userTmp.getRole());
		userReturn.setPhone(userTmp.getPhone());
			return userReturn;
		
		

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userDao.findByEmail(email);

		if (user == null) {

			throw new UsernameNotFoundException("User " + email + " was not found in the database");
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
				user.getEmail(), user.getPassword(), enable, accountNonExpired, credentialsNonExpired,
				accountNonLocked, grantList);

		return userDetails;
	}


	@Override
	public void applyNewPassword(User user) {

		User userUpdate = userDao.findByEmail(user.getEmail());

		String passRandom = RandomStringUtils.randomAlphanumeric(8);

		if (user.getEmail().equals(userUpdate.getEmail())) {
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
		user.setFullName(userModel.getFullName());
		user.setAvataUrl(userModel.getAvataUrl());
		user.setSex(userModel.getSex());
		user.setDateOfBirth(userModel.getDateOfBirth());
		user.setPhone(userModel.getPhone());

		userDao.save(user);

	}

	@Override
	public UserModel getUser(int id) {

		return modelMapper.map(userDao.getOne(id), UserModel.class);

	}

	@Override
	public User findByEmail(String email) {
		
		return userDao.findByEmail(email);
	}

	@Override
	public void updateExpired(String tokenUser) throws SQLException {
		cartService.updateExpired(tokenUser);
		
		
	}

	@Override
	public HttpServletResponse createCookie(String tokenUser,HttpServletResponse response) {
		Cookie cookie = new Cookie("tokenUser",tokenUser);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		cartService.addTokenUser(tokenUser);
		
		return response;
	}


	private void updateUserId(Cookie[] cookies, int userId) {
		CartModel cartModel = cartService.findByUserId(userId);
		if(cartModel.getUserId()==0) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("tokenUser")) {
					String tokenUser = cookie.getValue();
					cartService.updateUserId(tokenUser, userId);
					break;
				}
			}
		}
	}

	@Override
	public void blockUser(int id) {
		User user = userDao.getOne(id);
		if(user != null) {
			user.setActive(false);
			userDao.saveAndFlush(user);
		}
		
	}

}
