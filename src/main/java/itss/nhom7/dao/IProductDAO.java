package itss.nhom7.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Product;

@Transactional
@Repository
public interface IProductDAO extends JpaRepository<Product, Integer> {
	List<Product> getListProductByNameContaining(String nameProduct);
	
	@Query("select p from Product p where p.category.code = ?1")
	List<Product> getListProductCode(String code);
	@Query("select p from Product p order by p.price desc")
	List<Product> getListProductOrderPrice();
	@Query(nativeQuery = true,value="select p from Product p order by rand() limit 1")
	List<Product> getListProductRandom20();
	
}
