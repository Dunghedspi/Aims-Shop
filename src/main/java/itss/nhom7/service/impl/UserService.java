package itss.nhom7.service.impl;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IAddressDAO;
import itss.nhom7.dao.IUserDAO;
import itss.nhom7.entities.Address;
import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.IUserService;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IAddressDAO addressDao;
	@Autowired
	private CartService cartService;
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public boolean checkLogin(User user) {
		User userfind = userDao.findByEmail(user.getEmail());
		boolean isCheck = false;
		if ((null != userfind) && userfind.getPassword().equals(user.getPassword())) {
			isCheck = true;
		}
		return isCheck;
	}

	@Override
	public HttpStatus addUser(UserModel userModel) throws SQLException {
		HttpStatus status = null;
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
				user.setRole(String.valueOf(1));
				user.setCreatedAt(Calendar.getInstance());
				
//				Address address = new Address();
//				address.setCountry(userModel.getCountry());
//				address.setProvince(userModel.getProvince());
//				address.setDistrict(userModel.getDistrict());
//				address.setVillage(userModel.getVillage());
//				address.setStreet(userModel.getStreet());
//				addressDao.save(address);
				
				//user.setAddress(addressDao.findByDistrictAndVillageAndStreet(userModel.getDistrict(), userModel.getVillage(), userModel.getStreet()));
				userDao.save(user);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.CREATED;
			}
		}catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
			return status;
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
		System.out.println("Role : " +role);
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
	public void editUser(UserModel userModel) throws ParseException {

		User user = userDao.findByEmail(userModel.getEmail());
		user.setFullName(userModel.getFullName());
		user.setAvataUrl(userModel.getAvataUrl());
		user.setSex(userModel.getSex());
		Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse(userModel.getDateOfBirth());
		user.setDateOfBirth(dateOfBirth);
		user.setPhone(userModel.getPhone());
		
		if(user.getAddress() == null) {
			Address address = new Address();
			address.setCountry(userModel.getCountry());
			address.setProvince(userModel.getProvince());
			address.setDistrict(userModel.getDistrict());
			address.setVillage(userModel.getVillage());
			address.setStreet(userModel.getStreet());
			addressDao.save(address);
			
			user.setAddress(addressDao.findByDistrictAndVillageAndStreet(userModel.getDistrict(),
													userModel.getVillage(), userModel.getStreet()));
		}else {
			Address address = user.getAddress();
			address.setCountry(userModel.getCountry());
			address.setProvince(userModel.getProvince());
			address.setDistrict(userModel.getDistrict());
			address.setVillage(userModel.getVillage());
			address.setStreet(userModel.getStreet());
			addressDao.saveAndFlush(address);
			user.setAddress(address);
		}
		

		userDao.saveAndFlush(user);

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
	public HttpServletResponse createCookie(String tokenUser, HttpServletResponse response) {
		Cookie cookie = new Cookie("tokenUser", tokenUser);
		cookie.setPath("/");
		response.addCookie(cookie);
		cookie.setMaxAge(60 * 60 * 60);
		cartService.addTokenUser(tokenUser);
		return response;
	}

//	private void updateUserId(String tokenUser, int userId) {
//		try {
//			cartService.updateUserId(tokenUser, userId);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}

	@Override
	public void blockOrUnBlockUser(int id,boolean activity) {
		User user = userDao.getOne(id);
		if (user != null) {
			user.setActive(activity);
			userDao.saveAndFlush(user);
			
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(user.getEmail());
			if(activity) {
				message.setSubject("Unblocked account");
				message.setText("Hello, We are Aims!\n Admin unblocked your accoutn" );
			}else {
				message.setSubject("Blocked account");
				message.setText("Hello, We are Aims!\n Admin blocked your accoutn" );
			}
			
			// Send Message!
			this.emailSender.send(message);
		}
	}

	@Override
	public UserModel findByEmailAfterLogin(String email) throws SQLException {
		User userTmp = userDao.findByEmail(email);
		UserModel userReturn = new UserModel();
		userReturn.setEmail(userTmp.getEmail());
		userReturn.setFullName(userTmp.getFullName());
		userReturn.setDateOfBirth(userTmp.getDateOfBirth().toString());
		userReturn.setAvataUrl(userTmp.getAvataUrl());
		userReturn.setRole(userTmp.getRole());
		userReturn.setPhone(userTmp.getPhone());
		userReturn.setId(userTmp.getId());
		return userReturn;
	}

}
