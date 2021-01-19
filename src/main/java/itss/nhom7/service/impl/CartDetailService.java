package itss.nhom7.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.ICartDetailDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.CartDetail;
import itss.nhom7.entities.Product;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.service.ICartDetailService;

@Service
public class CartDetailService implements ICartDetailService{

	@Autowired
	private ICartDetailDAO cartDetailDao;
	@Autowired
	private IProductDAO productDao;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ArrayList<CartDetailModel> findByCartId(int id) {
		ArrayList<CartDetailModel> cartDetailModels = new ArrayList<CartDetailModel>();
		for(CartDetail cartDetail :cartDetailDao.findByCartId(id) ) {
			CartDetailModel cartDetailModel = new CartDetailModel();
			modelMapper.map(cartDetail, CartDetailModel.class);
			cartDetailModels.add(cartDetailModel);
		}
		
		return cartDetailModels;
	}
	@Override
	public boolean addProductToCart(CartDetailModel cartDetailModel) {
		if(checkQuantity(cartDetailModel.getProductId(),cartDetailModel.getQuantity())) {
			return false;
		}
		CartDetail cartDetail = modelMapper.map(cartDetailModel,CartDetail.class);
		cartDetailDao.save(cartDetail);
		return true;
	}
	@Override
	public void deleteCartDetail(int idCart) {
		cartDetailDao.deleteByCartId(idCart);
		
	}
	@Override
	public boolean editCartDetail(CartDetailModel cartDetailModel) {
		if(checkQuantity(cartDetailModel.getProductId(),cartDetailModel.getQuantity())) {
			return false;
		}
		CartDetail cartDetail = cartDetailDao.getOne(cartDetailModel.getCartId());
		cartDetail.setCartId(cartDetailModel.getCartId());
		cartDetail.setProductId(cartDetailModel.getProductId());
		cartDetail.setQuantity(cartDetailModel.getQuantity());
		cartDetailDao.saveAndFlush(cartDetail);
		return true;
	}
	@Override
	public void deleteProduct(CartDetailModel cartDetailModel) {
		cartDetailDao.deleteByCartIdAndProductId(cartDetailModel.getCartId(), cartDetailModel.getProductId());
	}
	private boolean checkQuantity(int productId, int quantity) {
		
		Product product = productDao.getOne(productId);
		if(product.getQuantity()<quantity) {
			return false;
		}
		return true;
	}
	
	//tinh tien tung mat hang
	public int getCartDetailPrice(CartDetailModel cartDetailModel) {
		Product product = productDao.getOne(cartDetailModel.getProductId());
		return cartDetailModel.getQuantity() * product.getPrice();
	}
	
}
