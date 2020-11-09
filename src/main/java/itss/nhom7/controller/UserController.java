package itss.nhom7.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.entities.User;
import itss.nhom7.jwt.JwtService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value="/aims")
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<String> login( @RequestBody User user) {
		
		
	    String result = "";
	    HttpStatus httpStatus = null;
	    try {
	    	System.out.println(2);
	      if (userService.checkLogin(user)) {
	        result = jwtService.generateTokenLogin( user.getUserName());
	        httpStatus = HttpStatus.OK;
	      } else {
	        result = "Wrong userId and password";
	        httpStatus = HttpStatus.BAD_REQUEST;
	      }
	    } catch (Exception ex) {
	      result = "Server Error";
	      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	    }
	    return new ResponseEntity<String>(result, httpStatus);
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
         return new ResponseEntity<String>(result,httpStatus);
     }  
	
	
	@PutMapping(value = "/editUser")
	public ResponseEntity<Object> editUser(@RequestBody User user) {
		
		userService.updateUser(user);
		
		return new ResponseEntity<Object>("Edit successfully!",HttpStatus.OK);
	}


}
