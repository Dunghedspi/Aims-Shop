package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Order;

@Transactional
@Repository
public interface IOrderDAO extends JpaRepository<Order, Integer>{

	Order findByUserId(int userId);
}
