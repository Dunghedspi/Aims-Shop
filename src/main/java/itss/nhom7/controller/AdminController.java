package itss.nhom7.controller;

import itss.nhom7.entities.User;
import itss.nhom7.helper.Utils;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.ProductService;
import itss.nhom7.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestController
@RequestMapping(value = "/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private Utils utils;

	@PostMapping(value = "/registerAdmin", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> createUser(UserModel userModel) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			userModel.setRole("ROLE_ADMIN");
			boolean isCreated = userService.addUser(userModel);
			if (isCreated) {
				httpStatus = HttpStatus.CREATED;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	@GetMapping(value = "/getUser/{id}")
	public ResponseEntity<Object> viewUser(@PathVariable("id") int id) {

		UserModel userModel = userService.getUser(id);
		if(userModel == null) {
			return new ResponseEntity<Object>(userModel, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Object>(userModel, HttpStatus.OK);

	}
	
	@PutMapping(value = "/blockUser/{idUser}")
	public ResponseEntity<Object> editUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = null;
		try {
			userService.blockUser(idUser);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	
	//xoa san pham
		@DeleteMapping(value="/deleteProduct/{id}")
		public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id,HttpServletRequest request){
			HttpStatus httpStatus = null;
			try {

				productService.deleteProduct(id);
				httpStatus = HttpStatus.OK;
			} catch (Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.println(e);
			}

			return new ResponseEntity<Object>(httpStatus);

		}

	@PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Object> login(User user, HttpServletResponse response, HttpServletRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		UserModel userModel = null;
		try {
			if (userService.checkLoginAdmin(user)) {
				userModel = userService.findByEmailAfterLogin(user.getEmail());
				String result = jwtService.generateTokenLogin(userModel.getEmail(), userModel.getRole());
				Cookie jwt = utils.createCookie("Authorization", result, true, (long) 36000);
				response.addCookie(jwt);
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(userModel, httpStatus);
	}

}
