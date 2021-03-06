package itss.nhom7.service;

import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;

public interface IUserService {

	boolean checkLogin(User user);													//kiem tra email va password khi login
  
  //đổi addUser trả về HttpStatus
	HttpStatus addUser(UserModel userModel) throws SQLException;					//Them nguoi dung (khi dang ki)
	User findByEmail(String email) throws SQLException;								//Tim kiem nguoi dung bang email(khi admin thay mat khau cho nguoi dung)
	void applyNewPassword(User user) throws SQLException;							//Cap nhat mat khau cho nguoi dung va admin gui mail thong bao
	void editUser(UserModel user) throws SQLException, ParseException;				//Thay doi thong tin nguoi dung(nguoi dung)
	UserModel getUser(int id) throws SQLException;									//Lay thong tin nguoi dung (nguoi dung)
	void blockOrUnBlockUser(int id, boolean activity) throws SQLException;
	void updateExpired(String tokenUser) throws SQLException;							//update han dung cho tokenUser
	HttpServletResponse createCookie(String tokenUser,HttpServletResponse response);	//Thiet lap cookie cho nguoi dung
	//void updateUserId(Cookie[] cookies,int userId);
	UserModel getUserByEmail(String email) throws SQLException;							// lay user theo email
}

