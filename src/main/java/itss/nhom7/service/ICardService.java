package itss.nhom7.service;

import java.util.List;

import itss.nhom7.model.CardModel;

public interface ICardService {
	List<CardModel> getListCardByUserId(int userId);	//lay danh sach the ngan hang cua user
	CardModel getCartModelById(int id);					//lay thong tin the ngan hang(khong can thiet)
	void addCard(CardModel cardModel);					//them the ngan hang (nguoi dung)
	void deleteCard(int idCard);						//Xoa the ngan hang(nguoi dung)
}
