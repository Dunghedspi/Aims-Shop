package itss.nhom7.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IOrderDetailDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.OrderDetail;
import itss.nhom7.entities.Product;
import itss.nhom7.model.OrderDetailModel;
import itss.nhom7.service.IOrderDetailService;

@Service
public class OrderDetailService implements IOrderDetailService{

	@Autowired
	private IOrderDetailDAO orderDetailDao;
	@Autowired
	private IProductDAO productDao;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ArrayList<OrderDetailModel> findByOrderId(int id) {
		ArrayList<OrderDetailModel> orderDetailModels = new ArrayList<OrderDetailModel>();
		for(OrderDetail orderDetail :orderDetailDao.findByOrderId(id) ) {
			OrderDetailModel orderDetailModel = new OrderDetailModel();
			modelMapper.map(orderDetail, OrderDetailModel.class);
			orderDetailModels.add(orderDetailModel);
		}
		
		return orderDetailModels;
	}
}
