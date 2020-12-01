package itss.nhom7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.ICartDAO;
import itss.nhom7.entities.Cart;
import itss.nhom7.service.ICartService;

@Service
public class CartService implements ICartService{
	
	@Autowired
	private ICartDAO cartDao;

	@Override
	public void addTokenUser(String tokenUser) {
		
		Cart cart = new Cart();
		cart.setTokenUser(tokenUser);
		
		cartDao.save(cart);
		
	}

}
