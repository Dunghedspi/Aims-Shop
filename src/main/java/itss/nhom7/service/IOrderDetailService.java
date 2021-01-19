package itss.nhom7.service;

import java.util.ArrayList;

import itss.nhom7.model.OrderDetailModel;

public interface IOrderDetailService {
	ArrayList<OrderDetailModel> findByOrderId(int id);			//lay thong tin chi tiet don hang theo id don hang
}
