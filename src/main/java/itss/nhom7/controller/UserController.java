package itss.nhom7.controller;

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
import org.springframework.web.bind.annotation.*;

import itss.nhom7.entities.User;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value="/aims")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	
//	@PostMapping(value="/register")
//	public void addUser(@RequestBody User user) {
//		user.setCreatedDate(new Date());
//		
//		userService.addUser(user);
//		System.out.println("123");
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	  public ResponseEntity<String> login(User user, HttpServletResponse response, HttpServletRequest request) {
	    String result = "";
	    HttpStatus httpStatus = null;
	    try {
	      if (userService.checkLogin(user)) {
	        result = jwtService.generateTokenLogin(user.getId());
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
    public ResponseEntity<String> logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        
        HttpStatus httpStatus = null;
        String result = "error";
        if (auth != null){      
           new SecurityContextLogoutHandler().logout(request, response, auth);  
           httpStatus = HttpStatus.OK;
           result="ok";
        }
		assert httpStatus != null;
		return new ResponseEntity<String>(httpStatus);
     }  
	
	
	@PutMapping(value = "/editUser")
	public ResponseEntity<Object> editUser(@RequestBody User user) {
		
		userService.updateUser(user);
		
		return new ResponseEntity<Object>("Edit successfully!",HttpStatus.OK);
	}


}
