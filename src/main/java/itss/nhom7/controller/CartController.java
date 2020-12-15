package itss.nhom7.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.impl.CartDetailService;
import itss.nhom7.service.impl.CartService;

@RestController
@RequestMapping(value = "/api/cart")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CartDetailService cartDetailService;
	
	
	@GetMapping(value="/getCart")
	public ResponseEntity<Object> getCart(HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("tokenUser")) {
				tokenUser = cookie.getValue();
				break;
			}
		}
		HttpStatus httpStatus = null;
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		try {
			mediaModels = cartService.getListProcuctfindByTokenUser(tokenUser);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		
		return new ResponseEntity<Object>(mediaModels, httpStatus);
	}
	
	@PostMapping(value="/addProductToCart",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addProductToCart(CartDetailModel cartDetailModel) throws IOException {
		String result = null;
		HttpStatus httpStatus = null;
		try {
			if(cartDetailService.addProductToCart(cartDetailModel)) {
				result = "add Product To Cart Successfully!";
			}else {
				result = "so luong san pham trong gio lon hon trong kho";
			}
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		
		return new ResponseEntity<Object>(result, httpStatus);
	}
	
	@PutMapping(value="/editCart",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> editCart(CartDetailModel cartDetailModel) {

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}
	
	//xoa product trong gia hang
	@DeleteMapping(value="/deleteProductInCartDetail",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> deleteProductInCartDetail(CartDetailModel cartDetailModel) {
		HttpStatus httpStatus = null;
		try {
			cartDetailService.deleteProduct(cartDetailModel);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<String>(httpStatus);
	}
	
	@DeleteMapping(value="/deleteCartDetail",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> deleteCart(int idCart) {
		HttpStatus httpStatus = null;
		try {
			cartDetailService.deleteCartDetail(idCart);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<String>(httpStatus);

	}

}
