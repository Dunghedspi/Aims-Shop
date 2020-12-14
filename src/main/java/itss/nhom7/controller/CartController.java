package itss.nhom7.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CartDetailService cartDetailService;
	
	
	@GetMapping(value="/getCart")
	public ResponseEntity<ArrayList<MediaModel>> getCart(HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("tokenUser")) {
				tokenUser = cookie.getValue();
				break;
			}
		}
		ArrayList<MediaModel> mediaModels = cartService.getListProcuctfindByTokenUser(tokenUser);
		
		return new ResponseEntity<ArrayList<MediaModel>>(mediaModels, HttpStatus.OK);
	}
	
	@PostMapping(value="/addProductToCart",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> addProductToCart(CartDetailModel cartDetailModel) {
		String result = null;
		if(cartDetailService.addProductToCart(cartDetailModel)) {
			result = "add Product To Cart Successfully!";
		}else {
			result = "so luong san pham trong gio lon hon trong kho";
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}
	
	@PutMapping(value="/editCart",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> editCart(CartDetailModel cartDetailModel) {

		return new ResponseEntity<Object>("Edit Cart Successfully!", HttpStatus.OK);

	}
	
	//xoa product trong gia hang
	@DeleteMapping(value="/deleteProductInCartDetail",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> deleteProductInCartDetail(CartDetailModel cartDetailModel) {
		cartDetailService.deleteProduct(cartDetailModel);
		return new ResponseEntity<Object>("Delete Product in CartDetail Successfully!", HttpStatus.OK);

	}
	
	@DeleteMapping(value="/deleteCartDetail",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> deleteCart(int idCart) {
		cartDetailService.deleteCartDetail(idCart);
		return new ResponseEntity<Object>("Delete CartDetail Successfully!", HttpStatus.OK);

	}

}
