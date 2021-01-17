package itss.nhom7.controller;

import java.sql.SQLException;
import java.text.ParseException;

import itss.nhom7.helper.Utils;
import itss.nhom7.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value="/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private Utils utils;
	@Autowired
	private JwtService jwtService;
	@GetMapping(value = "/getUser")
	public ResponseEntity<UserModel> getUser(HttpServletRequest req, HttpServletResponse res) throws SQLException {
		Cookie jwt = utils.getCookie(req, "Authorization");
		UserModel userModel = null;
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if (null != jwt) {
			String email = jwtService.getEmailToken(jwt.getValue());
			userModel = userService.findByEmailAfterLogin(email);
			if(null != userModel) httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<UserModel>(userModel, httpStatus);

	}

	@PostMapping(value = "/editUser", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> editUser(UserModel userModel, HttpServletRequest req) throws ParseException {
		Cookie jwt = utils.getCookie(req,"Authorization");
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if(null != jwt) {
			userService.editUser(userModel);
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<Object>("Edit successfully!", httpStatus);
	}
	@PostMapping(value = "/editImageUser", produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> editImageUser(UserModel userModel, HttpServletRequest req) throws Exception {
		Cookie jwt = utils.getCookie(req,"Authorization");
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if(null != jwt) {
			userService.editUserImage(userModel, jwtService.getEmailToken(jwt.getValue()));
			httpStatus = HttpStatus.OK;
		}
		return new ResponseEntity<Object>("Edit successfully!", httpStatus);
	}

}
