package itss.nhom7.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.OrderDetail;
import itss.nhom7.model.ProductOrderQuantityCount;

@Transactional
@Repository
public interface IOrderDetailDAO extends JpaRepository<OrderDetail, Integer> {

	@Query("select new itss.nhom7.model.ProductOrderQuantityCount(o.product.id,sum(o.quantity))"
			+"from OrderDetail as o group by o.product.id")
	List<ProductOrderQuantityCount> countTotalProductOrderQuantity();
}
