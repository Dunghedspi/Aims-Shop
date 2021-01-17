package itss.nhom7.controller;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import itss.nhom7.entities.User;
import itss.nhom7.helper.Utils;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.CartService;
import itss.nhom7.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Timestamp;


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

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	public ResponseEntity<String> createUser(UserModel userModel) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			userModel.setRole("ROLE_USER");
			if (userService.addUser(userModel)) {
				httpStatus = HttpStatus.CREATED;
			}
		} catch (SQLException e) {
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
				userModel = userService.findByEmailAfterLogin(user.getEmail());
				String result = jwtService.generateTokenLogin(userModel.getEmail(), userModel.getRole());
				Cookie jwt = utils.createCookie("Authorization", result, true, (long) 3600);
				Cookie cookie = utils.getCookie(request, "userToken");
				if (null != cookie) {
					cartService.updateUserId(cookie.getValue(), userModel.getId());
					Cookie userToken = utils.createCookie("userToken", null, false, (long) 0);
					response.addCookie(userToken);
				}
				response.addCookie(jwt);
				httpStatus = HttpStatus.OK;
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
			Cookie userToken = utils.createCookie("userToken", null, false, (long) 0);
			response.addCookie(userToken);
			Cookie jwt = utils.createCookie("Authorization", null, true, (long) 0);
			response.addCookie(jwt);
			new SecurityContextLogoutHandler().logout(request, response, auth);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<String>(httpStatus);
	}

	@PutMapping(value = "/applyNewPassword", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> applyNewPassword(User user) {
		if (userService.findByEmail(user.getEmail()) != null) {
			userService.applyNewPassword(user);
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}
}
