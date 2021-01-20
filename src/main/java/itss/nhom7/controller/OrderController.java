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

import itss.nhom7.dao.impl.OrderDetailDAO;
import itss.nhom7.entities.CartDetail;
import itss.nhom7.entities.OrderDetail;
import itss.nhom7.model.CardModel;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.impl.CardService;
import itss.nhom7.service.impl.CartDetailService;
import itss.nhom7.service.impl.CartService;
import itss.nhom7.service.impl.OrderDetailService;
import itss.nhom7.service.impl.OrderService;
import itss.nhom7.utils.Utils;

@RestController
@RequestMapping(value = "/api/order")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private CartDetailService cartDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderDetailDAO orderDetailDAO;
	@Autowired
	private CardService cardService;
	
	// Hien thi don hang va tinh gia tien
	@GetMapping(value="/checkout")
	public ResponseEntity<Object> checkout(HttpServletResponse response, HttpServletRequest request, int total_price) {
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
			//Lưu ket quan hoa don tam thoi vao session
			mediaModels = cartService.getListProcuctfindByTokenUser(tokenUser);
			for (MediaModel mediaModel : mediaModels) {
	            total_price += mediaModel.getPrice()  *	mediaModel.getQuantity();
	        }
			Utils.storeCheckoutSession(request, mediaModels);
			Utils.storeTotalPriceSession(request, total_price);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(mediaModels, httpStatus);
	}
	
	// Thanh toan va them ban ghi vao Order va xoa trong Cart
	@PostMapping(value="/payment",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<Object> payment(HttpServletResponse response, HttpServletRequest request) throws IOException {
		Cookie[] cookies = request.getCookies();
		String tokenUser = null;
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("tokenUser")) {
				tokenUser = cookie.getValue();
				break;
			}
		}
		HttpStatus httpStatus = null;
		try {
			CartDetail cartDetail = Utils.getCheckoutSession(request);
			int total_price = Utils.getTotalPriceSession(request);
			//Kiem tra so du
			List<CardModel> cardModels = new ArrayList<CardModel>();
		    cardModels = cardService.getListCardfindByTokenUser(tokenUser);
		    int balance = cardModels.get(0).getBalance();   //Doan nay la lay so du cua the vi k xu ly chon duoc nen chon cai dau tien
			//neu con du tien de thanh toan
			if(card.getAcountBalance() > total_price)
			{
				orderDetailDAO.save(cartDetail);
				cartDetailService.deleteCartDetail(cartDetail.getID());
				result = "Thanh toan thanh cong";
			}
			else
			{ 
				result = "Tai khoan khong du so du";
			}
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(result, httpStatus);
	}

	//Hien thi lich su order
	@GetMapping(value="/getOrder")
	public ResponseEntity<Object> getOrder(HttpServletResponse response, HttpServletRequest request) {
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
			mediaModels = orderService.getListProcuctfindByTokenUser(tokenUser);
			httpStatus = HttpStatus.OK;
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(mediaModels, httpStatus);
	}

}
