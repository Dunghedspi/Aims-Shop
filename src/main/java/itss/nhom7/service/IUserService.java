package itss.nhom7.service;

import itss.nhom7.entities.User;

public interface IUserService {

	boolean checkLogin(User user);
	//void addUser(User user);
	User findByUsername(String username);
	void updateUser(User user);
}
