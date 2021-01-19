package itss.nhom7.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Card;

@Transactional
@Repository
public interface ICardDAO extends JpaRepository<Card, Integer> {
	List<Card> findListCardByUserId(int userId);
	Card findByUserId(int userId);
	ArrayList<Card> findByTokenUser(String tokenUser);
}
