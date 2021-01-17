package itss.nhom7.service;

import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;

import java.sql.SQLException;
import java.text.ParseException;

public interface IUserService {

	boolean checkLogin(User user);								//kiem tra email va password khi login
	boolean addUser(UserModel userModel) throws SQLException;				//Them nguoi dung (khi dang ki)
	User findByEmail(String email) throws SQLException;	//Tim kiem nguoi dung bang email(khi admin thay mat khau cho nguoi dung)
	UserModel findByEmailAfterLogin(String email) throws SQLException;	
	void applyNewPassword(User user) throws SQLException;						//Cap nhat mat khau cho nguoi dung va admin gui mail thong bao
	void editUser(UserModel user) throws SQLException, ParseException;							//Thay doi thong tin nguoi dung(nguoi dung)
	UserModel getUser(int id) throws SQLException;								//Lay thong tin nguoi dung (nguoi dung)
	void blockUser(int id) throws SQLException;
	void updateExpired(String tokenUser) throws SQLException;					//update han dung cho tokenUser
	boolean checkLoginAdmin(User user);
	boolean editUserImage(UserModel userModel, String email) throws Exception;
}
