package itss.nhom7.service;

import java.util.ArrayList;

import itss.nhom7.model.CartModel;
import itss.nhom7.model.MediaModel;

public interface ICartService {
	
	void addTokenUser(String tokenUser);									//them tokenUser vao db 
	ArrayList<MediaModel> getListProcuctfindByTokenUser(String tokenUser);	//lay danh sach san pham theo tokenUser
	CartModel findByTokenUser(String tokenUser);							//tim gio hang theo tokenUser 
	void updateExpired(String tokenUser);									//Cap nhat han dung cua tokenUser
	void updateUserId(String tokenUser,int id);								//Them userId theo tokenUser vao db
	CartModel findByUserId(int id);											//Lay thong tin gio hang theo userId
}
