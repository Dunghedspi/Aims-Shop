package itss.nhom7.service.impl;

import itss.nhom7.dao.IOrderDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Order;
import itss.nhom7.entities.Product;
import itss.nhom7.model.OrderDetailModel;
import itss.nhom7.model.OrderModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

@Service
public class OrderService implements IOrderService{
	
	@Autowired
	private IOrderDAO orderDao;
	@Autowired
	private IProductDAO productDao;
	

	//Them tokenUser khi dang nhap vao trang chu
	@Override
	public void addTokenUser(String tokenUser) {
		Order order = new Order();
		order.setTokenUser(tokenUser);
		Calendar timeout = Calendar.getInstance();
		order.setUserId(1);
		order.setCreatedAt(timeout);
		orderDao.save(order);
	}

	//Lay product theo tokenUser
	@Override
	public ArrayList<MediaModel> getListProcuctfindByTokenUser(String tokenUser) {
		
		Order order = orderDao.findByTokenUser(tokenUser);
		ArrayList<MediaModel> mediaModels = new ArrayList<MediaModel>();
		if(order!=null) {
			OrderDetailService orderDetailService = new OrderDetailService();
			for(OrderDetailModel orderDetailModel : orderDetailService.findByOrderId(order.getId())) {
				MediaModel mediaModel = new MediaModel();
				Product product = productDao.getOne(orderDetailModel.getProductId());
				mediaModel.setId(product.getId());
				mediaModel.setName(product.getName());
				mediaModel.setPrice(product.getPrice());
				mediaModel.setValue(product.getValue());
				mediaModel.setQuantity(orderDetailModel.getQuantity());
				
				mediaModels.add(mediaModel);
			}
		}
		
		return mediaModels;
	}

	@Override
	public void updateExpired(String tokenUser) {
		orderDao.deleteById(orderDao.findByTokenUser(tokenUser).getId());
		addTokenUser(tokenUser);
	}

	@Override
	public void updateUserId(String tokenUser, int userId) throws SQLException {
		deleteOrderById(userId);
		Order order = orderDao.findByTokenUser(tokenUser);
		order.setUserId(userId);
		orderDao.save(order);
	}

	@Override
	public OrderModel findByTokenUser(String tokenUser) {
		Order order = orderDao.findByTokenUser(tokenUser);
		OrderModel orderModel = new OrderModel();
		if(order!=null) {
			orderModel.setId(order.getId());
			orderModel.setTokenUser(order.getTokenUser());
			orderModel.setUserId(order.getUserId());
			orderModel.setCreatedAt(order.getCreatedAt());
		}
		return orderModel;
	}

	@Override
	public OrderModel findByUserId(int id) {
		Order order = orderDao.findByUserId(id);
		OrderModel orderModel = new OrderModel();
		if (order != null) {
			orderModel.setId(order.getId());
			orderModel.setTokenUser(order.getTokenUser());
			orderModel.setUserId(order.getUserId());
			orderModel.setCreatedAt(order.getCreatedAt());
		}
		return orderModel;
	}

	public void deleteOrderById(int userId) throws SQLException {
		Order order = orderDao.findByUserId(userId);
		if (null != order) {
			deleteOrder(order.getId());
		}
	}

	@Override
	public void deleteOrder(int id) throws SQLException {
		orderDao.deleteById(id);
	}

}
