package itss.nhom7.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {
	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/registerAdmin", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> createUser(UserModel userModel) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			userModel.setRole("ROLE_ADMIN");
			boolean isCreated = userService.addUser(userModel);
			if(isCreated) {
				httpStatus=HttpStatus.CREATED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(httpStatus);
	}

}
