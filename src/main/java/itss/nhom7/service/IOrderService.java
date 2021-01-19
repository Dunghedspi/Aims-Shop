package itss.nhom7.service;

import java.sql.SQLException;
import java.util.ArrayList;

import itss.nhom7.model.OrderModel;
import itss.nhom7.model.MediaModel;

public interface IOrderService {
	
	void addTokenUser(String tokenUser) throws SQLException;									//them tokenUser vao db 
	ArrayList<MediaModel> getListProcuctfindByTokenUser(String tokenUser) throws SQLException;	//lay danh sach san pham theo tokenUser
	OrderModel findByTokenUser(String tokenUser) throws SQLException;							//tim don hang theo tokenUser 
	void updateExpired(String tokenUser) throws SQLException;									//Cap nhat han dung cua tokenUser
	void updateUserId(String tokenUser,int id) throws SQLException;								//Them userId theo tokenUser vao db
	OrderModel findByUserId(int id) throws SQLException;										//Lay thong tin don hang theo userId
	void deleteOrder(int id) throws  SQLException;
}
