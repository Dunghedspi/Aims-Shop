package itss.nhom7.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cart")
public class CartController {
	
	
	@GetMapping(value="getCart")
	public ResponseEntity<Object> getCart(HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("tokenUser")) {
				tokenUser = cookie.getValue();
			}
		}

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}
	
	@PostMapping(value="addCart")
	public ResponseEntity<Object> addCart() {

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}
	
	@PutMapping(value="editCart")
	public ResponseEntity<Object> editCart() {

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}

	public ResponseEntity<Object> deleteCart() {

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}

}
