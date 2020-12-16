package itss.nhom7.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

	@RequestMapping(value = "/createUserToken")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		String myDate = "1970/01/01 00:00:01";
		LocalDateTime localDateTime = LocalDateTime.parse(myDate,
		    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss") );
		/*
		  With this new Date/Time API, when using a date, you need to
		  specify the Zone where the date/time will be used. For your case,
		  seems that you want/need to use the default zone of your system.
		  Check which zone you need to use for specific behaviour e.g.
		  CET or America/Lima
		*/
		long millis = localDateTime
		    .atZone(ZoneId.systemDefault())
		    .toInstant().toEpochMilli();
		int flag = 0;
		if (cookies == null) {
			response = userService.createCookie(Long.toString(millis), response);
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
				response = userService.createCookie(Long.toString(millis), response);
			}
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

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public ResponseEntity<Object> login(User user, HttpServletResponse response, HttpServletRequest request) {
		String result = "";
		HttpStatus httpStatus = null;
		UserModel userModel = new UserModel();
		Cookie[] cookies = request.getCookies();
		try {
			if (userService.checkLogin(user, cookies)) {
				result = jwtService.generateTokenLogin(user.getEmail());
				httpStatus = HttpStatus.OK;
				Cookie jwt = new Cookie("Authorization", result);
				jwt.setHttpOnly(true);
				jwt.setPath("/");
				response.addCookie(jwt);
				userModel = userService.findByEmailAfterLogin(user.getEmail());
			} else {
				httpStatus = HttpStatus.BAD_REQUEST;
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
