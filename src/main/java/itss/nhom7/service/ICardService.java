package itss.nhom7.service;

import java.sql.SQLException;
import java.util.List;

import itss.nhom7.model.CardModel;

public interface ICardService {
	List<CardModel> getListCardByUserId(int userId) throws SQLException;	//lay danh sach the ngan hang cua user
	CardModel getCartModelById(int id) throws SQLException;					//lay thong tin the ngan hang(khong can thiet)
	void addCard(CardModel cardModel) throws SQLException;					//them the ngan hang (nguoi dung)
	void deleteCard(int idCard) throws SQLException;						//Xoa the ngan hang(nguoi dung)
}
