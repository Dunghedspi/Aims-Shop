package itss.nhom7.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.entities.User;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value="/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;
	
	@RequestMapping(value="/home")
	public ResponseEntity<Object> home(HttpServletResponse response, HttpServletRequest request){
		
		Cookie[] cookies = request.getCookies();
		String tokenUser =null;
		if(cookies == null) {
			tokenUser = RandomStringUtils.randomAlphanumeric(8);
			Cookie cookie = new Cookie("tokenUser",tokenUser);
			cookie.setMaxAge(24*60*60); // han la 2 ngay
			cookie.setHttpOnly(true);
			cookie.setPath("/");
			response.addCookie(cookie);
		}else {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("tokenUser")) {
					tokenUser = cookie.getValue();
				}
			}
		}
		System.out.println(tokenUser);
		
		return new ResponseEntity<Object>("Access successfully!",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	  public ResponseEntity<String> login(User user, HttpServletResponse response, HttpServletRequest request) {
	    String result = "";
	    HttpStatus httpStatus = null;
	    try {
	      if (userService.checkLogin(user)) {
	        result = jwtService.generateTokenLogin(user.getEmail());
	        httpStatus = HttpStatus.OK;
	        Cookie jwt = new Cookie("Authorization",result);
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

	@RequestMapping(value="/logout", method=RequestMethod.GET)  
    public ResponseEntity<Object> logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        
        HttpStatus httpStatus = null;
        String result = "error";
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
           httpStatus = HttpStatus.OK;
           result="ok";
        }
		assert httpStatus != null;
		return new ResponseEntity<Object>(result,httpStatus);
     }  

	//@PutMapping(value = "/applyNewPassword",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@PutMapping(value = "/applyNewPassword")
	public ResponseEntity<Object> applyNewPassword(@RequestBody User user) {

		userService.applyNewPassword(user);

		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}

	@GetMapping(value = "editUser/{id}")
	public ResponseEntity<UserModel> viewUser(@PathVariable("id") int id) {

		UserModel userModel = userService.getUser(id);
		return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);

	}

	@PutMapping(value = "/editUser")
	public ResponseEntity<Object> editUser(@RequestBody UserModel userModel) {

		userService.editUser(userModel);

		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}

}
