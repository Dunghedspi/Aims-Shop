package itss.nhom7.controller;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import itss.nhom7.entities.User;
import itss.nhom7.helper.Utils;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.CartService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CartService cartService;
	@Autowired
	private Utils utils;

	@RequestMapping(value = "/createUserToken")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Cookie userToken = utils.getCookie(request, "userToken");
		if (null == userToken) {
			String token = String.valueOf(timestamp.getTime());
			userToken = utils.createCookie("userToken", token, false, (long) 3600);
			cartService.addTokenUser(token);
			response.addCookie(userToken);
		}
		return new ResponseEntity<Object>("Access successfully!", HttpStatus.OK);
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public ResponseEntity<String> createUser(UserModel userModel) {
		HttpStatus httpStatus = null;
		try {
			userModel.setRole("ROLE_USER");
			httpStatus = userService.addUser(userModel);
		} catch (SQLException e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<String>(httpStatus);
	}

	@PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> login(User user, HttpServletResponse response, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		UserModel userModel = null;
		try {
			if (userService.checkLogin(user)) {
				String result = jwtService.generateTokenLogin(user.getEmail());
				Cookie jwt = utils.createCookie("Authorization", result, true, (long) 3600);
				
				Cookie[] cookies = request.getCookies();
				if(cookies ==  null) {
					response.addCookie(jwt);
				}else {
					if(utils.checkCookies(cookies)) {
						for(Cookie cookie : cookies) {
							if(cookie.getName().contentEquals("Authorization")) {
								cookie.setValue(jwt.getValue());
								cookie.setMaxAge(jwt.getMaxAge());
								break;
							}
						}
					}else {
						response.addCookie(jwt);
					}
				}
				Cookie cookie = utils.getCookie(request, "userToken");
				userModel = userService.findByEmailAfterLogin(user.getEmail());
				if (null != cookie) {
					cartService.updateUserId(cookie.getValue(), userModel.getId());
				}
				//response.addCookie(jwt);
				httpStatus = HttpStatus.OK;
				System.out.println(result);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(userModel,httpStatus);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		if (auth != null) {
			utils.deleteCookie(request, "Authentication");
			utils.deleteCookie(request, "userToken");
//			Cookie jwt = utils.deleteCookie(request, "Authentication");
//			Cookie userToken = utils.deleteCookie(request, "userToken");
//			response.addCookie(jwt);
//			response.addCookie(userToken);
			new SecurityContextLogoutHandler().logout(request, response, auth);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<String>(httpStatus);
	}
	
	@PutMapping(value = "/applyNewPassword",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> applyNewPassword(User user) {
		if(userService.findByEmail(user.getEmail())!=null) {
			userService.applyNewPassword(user);
			return new ResponseEntity<String>(HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}

}
