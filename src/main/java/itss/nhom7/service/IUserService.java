package itss.nhom7.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;

public interface IUserService {

	boolean checkLogin(User user,Cookie[] cookies);								//kiem tra email va password khi login
	void addUser(UserModel userModel);											//Them nguoi dung (khi dang ki)
	User findByEmail(String email);												//Tim kiem nguoi dung bang email(khi admin thay mat khau cho nguoi dung)
	void applyNewPassword(User user);											//Cap nhat mat khau cho nguoi dung va admin gui mail thong bao
	void editUser(UserModel user);												//Thay doi thong tin nguoi dung(nguoi dung)
	UserModel getUser(int id);													//Lay thong tin nguoi dung (nguoi dung)
	
	void updateExpired(String tokenUser);										//update han dung cho tokenUser
	HttpServletResponse createCookie(String tokenUser,HttpServletResponse response);//Thiet lap cookie cho nguoi dung
	//void updateUserId(Cookie[] cookies,int userId);
}
