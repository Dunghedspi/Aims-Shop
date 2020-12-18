package itss.nhom7.controller;

import java.sql.SQLException;
import java.text.ParseException;

import itss.nhom7.helper.Utils;
import itss.nhom7.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value="/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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

	@PutMapping(value = "/editUser")
	public ResponseEntity<Object> editUser(@RequestBody UserModel userModel) throws ParseException {
		if(userService.getUser(userModel.getId()) == null) {
			return new ResponseEntity<Object>("Edit failed!", HttpStatus.NO_CONTENT);
		}
		userService.editUser(userModel);
		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/blockUser/{idUser}")
	public ResponseEntity<String> editUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = null;
		try {
			userService.blockUser(idUser);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}

}
