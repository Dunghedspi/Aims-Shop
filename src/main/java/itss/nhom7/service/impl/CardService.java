package itss.nhom7.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.ICardDAO;
import itss.nhom7.dao.IUserDAO;
import itss.nhom7.entities.Card;
import itss.nhom7.entities.Cart;
import itss.nhom7.entities.Product;
import itss.nhom7.model.CardModel;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.ICardService;

@Service
public class CardService implements ICardService{
	
	@Autowired
	private ICardDAO cardDao;
	@Autowired
	private IUserDAO userDao;

	@Override
	public List<CardModel> getListCardByUserId(int userId) {
		List<Card> cards = cardDao.findListCardByUserId(userId);
		List<CardModel> cardModels = new ArrayList<CardModel>();
		if(cards != null) {
			for(Card card : cards) {
				CardModel cardModel = new CardModel();
				cardModel.setId(card.getId());
				cardModel.setNameBank(card.getNameBank());
				cardModel.setNumberCard(card.getNumberCard());
				cardModel.setUserId(card.getUser().getId());
				cardModels.add(cardModel);
			}
		}
		
		return cardModels;
	}

	@Override
	public CardModel getCartModelById(int id) {
		Card card = cardDao.getOne(id);
		CardModel cardModel = new CardModel();
		if(card != null) {
			cardModel.setId(card.getId());
			cardModel.setNameBank(card.getNameBank());
			cardModel.setNumberCard(card.getNumberCard());
			cardModel.setUserId(card.getUser().getId());
		}
		return cardModel;
	}

	@Override
	public void addCard(CardModel cardModel) {
		Card card = new Card();
		card.setNameBank(cardModel.getNameBank());
		card.setNumberCard(cardModel.getNumberCard());
		card.setUser(userDao.getOne(cardModel.getUserId()));
		cardDao.saveAndFlush(card);
	}

	@Override
	public void deleteCard(int idCard) {
		if(cardDao.getOne(idCard) !=null) {
			cardDao.deleteById(idCard);
		}
	}
	
	public ArrayList<CardModel> getListCardfindByTokenUser(String tokenUser) {
		ArrayList<Card> cards = cardDao.findByTokenUser(tokenUser);
		ArrayList<CardModel> cardModels = new ArrayList<CardModel>();
		if(cards != null) {
			for(Card card : cards) {
				CardModel cardModel = new CardModel();
				cardModel.setId(card.getId());
				cardModel.setNameBank(card.getNameBank());
				cardModel.setNumberCard(card.getNumberCard());
				cardModel.setUserId(card.getUser().getId());
				cardModel.setBalance(card.getAccountBalance());
				cardModels.add(cardModel);
			}
		}		
		return cardModels;
	}

}
