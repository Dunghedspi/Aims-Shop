package itss.nhom7.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CartDetailDAO {
	@PersistenceContext
	EntityManager entityManager;

//	void deleteByCartId(int idCart) {
//		Query query = entityManager.createQuery("delete from CartDetail cartDetail where cartDetail.cartId = ?1");
//		query.setParameter(1, idCart);
//		query.executeUpdate();
//	}
}
