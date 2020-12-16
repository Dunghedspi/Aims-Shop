package itss.nhom7.dao;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.CartDetail;

@Transactional
@Repository
public interface ICartDetailDAO extends JpaRepository<CartDetail, Integer> {
	
	ArrayList<CartDetail> findByCartId(int id);
	void deleteByCartIdAndProductId(int cartId,int productId);
	void deleteByCartId(int cartId);

}
