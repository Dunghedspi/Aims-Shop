package itss.nhom7.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.ProductService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value = "/api/admin")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminController {
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	
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
	public ResponseEntity<Object> blockUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = null;
		try {
			userService.blockOrUnBlockUser(idUser,true);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	
	@PutMapping(value = "/unBlockUser/{idUser}")
	public ResponseEntity<Object> unBlockUser(@PathVariable("idUser") int idUser) {
		HttpStatus httpStatus = null;
		try {
			userService.blockOrUnBlockUser(idUser,false);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(httpStatus);
	}
	
	//xoa san pham
		@PutMapping(value="/deleteProduct/{id}")
		public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id,HttpServletRequest request){
			HttpStatus httpStatus = null;
			try {
				
				productService.deleteProduct(id);
				httpStatus = HttpStatus.OK;
			}catch(Exception e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				System.out.println(e);
			}
			
			return new ResponseEntity<Object>(httpStatus);
			
		}

}
