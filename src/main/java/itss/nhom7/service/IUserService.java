package itss.nhom7.service;

import itss.nhom7.entities.User;
import itss.nhom7.model.UserModel;

public interface IUserService {

	boolean checkLogin(User user);
	//void addUser(User user);
	User findByUsername(String username);
	User findByEmail(String email);
	void applyNewPassword(User user);
	void editUser(UserModel user);
	UserModel getUser(int id);
}
