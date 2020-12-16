package itss.nhom7.service.impl;

import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.ICartDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Cart;
import itss.nhom7.entities.Product;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.CartModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.ICartService;

@Service
public class CartService implements ICartService{
	
	@Autowired
	private ICartDAO cartDao;
	@Autowired
	private IProductDAO productDao;
	

	//Them tokenUser khi dang nhap vao trang chu
	@Override
	public void addTokenUser(String tokenUser) {
		Cart cart = new Cart();
		cart.setTokenUser(tokenUser);
		Calendar timeout = Calendar.getInstance();
		timeout.set(Calendar.DATE, Calendar.DAY_OF_WEEK+2);
		cart.setCreatedAt(timeout);
		cartDao.save(cart);
	}

	//Lay product theo tokenUser
	@Override
	public ArrayList<MediaModel> getListProcuctfindByTokenUser(String tokenUser) {
		
		Cart cart = cartDao.findByTokenUser(tokenUser);
		ArrayList<MediaModel> mediaModels = new ArrayList<MediaModel>();
		if(cart!=null) {
			CartDetailService cartDetailService = new CartDetailService();
			for(CartDetailModel cartDetailModel : cartDetailService.findByCartId(cart.getId())) {
				MediaModel mediaModel = new MediaModel();
				Product product = productDao.getOne(cartDetailModel.getProductId());
				mediaModel.setId(product.getId());
				mediaModel.setName(product.getName());
				mediaModel.setPrice(product.getPrice());
				mediaModel.setValue(product.getValue());
				mediaModel.setQuantity(cartDetailModel.getQuantity());
				
				mediaModels.add(mediaModel);
			}
		}
		
		return mediaModels;
	}

	@Override
	public void updateExpired(String tokenUser) {
		cartDao.deleteById(cartDao.findByTokenUser(tokenUser).getId());
		addTokenUser(tokenUser);
	}

	@Override
	public void updateUserId(String tokenUser,int userId) {
		Cart cart = cartDao.findByTokenUser(tokenUser);
		cart.setUserId(userId);
		cartDao.save(cart);
	}

	@Override
	public CartModel findByTokenUser(String tokenUser) {
		Cart cart = cartDao.findByTokenUser(tokenUser);
		CartModel cartModel = new CartModel();
		if(cart!=null) {
			cartModel.setId(cart.getId());
			cartModel.setTokenUser(cart.getTokenUser());
			cartModel.setUserId(cart.getUserId());
			cartModel.setCreatedAt(cart.getCreatedAt());
		}
		return cartModel;
	}

	@Override
	public CartModel findByUserId(int id) {
		
		Cart cart = cartDao.findByUserId(id);
		CartModel cartModel = new CartModel();
		if(cart!=null) {
			cartModel.setId(cart.getId());
			cartModel.setTokenUser(cart.getTokenUser());
			cartModel.setUserId(cart.getUserId());
			cartModel.setCreatedAt(cart.getCreatedAt());
		}
		return cartModel;
	}

}
