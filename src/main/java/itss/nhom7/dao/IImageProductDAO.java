package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.ImageProduct;

@Repository
@Transactional
public interface IImageProductDAO extends JpaRepository<ImageProduct, Integer>{
	ImageProduct findByProductId(int productId);
}
