package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Product;

@Transactional
@Repository
public interface IProductDAO extends JpaRepository<Product, Integer> {

}
