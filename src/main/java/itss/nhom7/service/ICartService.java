package itss.nhom7.service;

import java.sql.SQLException;
import java.util.ArrayList;

import itss.nhom7.model.CartModel;
import itss.nhom7.model.MediaModel;

public interface ICartService {
	
	void addTokenUser(String tokenUser) throws SQLException;									//them tokenUser vao db 
	ArrayList<MediaModel> getListProcuctfindByTokenUser(String tokenUser) throws SQLException;	//lay danh sach san pham theo tokenUser
	CartModel findByTokenUser(String tokenUser) throws SQLException;							//tim gio hang theo tokenUser 
	void updateExpired(String tokenUser) throws SQLException;									//Cap nhat han dung cua tokenUser
	void updateUserId(String tokenUser,int id) throws SQLException;								//Them userId theo tokenUser vao db
	CartModel findByUserId(int id) throws SQLException;//Lay thong tin gio hang theo userId
	void deleteCart(int id) throws  SQLException;
}
