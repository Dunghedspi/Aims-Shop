package itss.nhom7.controller;

import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
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

import itss.nhom7.entities.User;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.CartService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/home")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/home")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		int flag = 0;
		if (cookies == null) {
			response = userService.createCookie(RandomStringUtils.randomAlphanumeric(8), response);
		} else {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("tokenUser")) {
					tokenUser = cookie.getValue();
					if (cartService.findByTokenUser(tokenUser).getTokenUser() != null) {
						Calendar expired = cartService.findByTokenUser(tokenUser).getCreatedAt();
						if (expired.before(Calendar.getInstance())) {
							try {
								userService.updateExpired(tokenUser);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} else {
						response = userService.createCookie(tokenUser, response);
					}
					flag++;
					break;
				}
			}
			if (flag == 0) {
				response = userService.createCookie(RandomStringUtils.randomAlphanumeric(8), response);
			}
		}

		return new ResponseEntity<Object>("Access successfully!", HttpStatus.OK);
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> createUser(UserModel userModel) {
		HttpStatus httpStatus = null;
		try {
			httpStatus = userService.addUser(userModel);
		} catch (SQLException e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		return new ResponseEntity<String>(httpStatus);
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> login(User user, HttpServletResponse response, HttpServletRequest request) {
		String result = "";
		HttpStatus httpStatus = null;
		Cookie[] cookies = request.getCookies();
		try {
			if (userService.checkLogin(user, cookies)) {
				result = jwtService.generateTokenLogin(user.getEmail());
				httpStatus = HttpStatus.OK;
				Cookie jwt = new Cookie("Authorization", result);
				jwt.setHttpOnly(true);
				jwt.setPath("/");
				response.addCookie(jwt);
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<String>(httpStatus);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		HttpStatus httpStatus = null;
		//String result = "error";
		if (auth != null) {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("Authorization")) {
					cookie.setMaxAge(0);
					cookie.setValue(null);
				}
			}
			new SecurityContextLogoutHandler().logout(request, response, auth);
			httpStatus = HttpStatus.OK;
			//result = "ok";
		}
		assert httpStatus != null;
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
