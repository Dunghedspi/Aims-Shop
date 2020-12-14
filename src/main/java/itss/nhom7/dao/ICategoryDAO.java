package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.Category;

@Transactional
@Repository
public interface ICategoryDAO extends JpaRepository<Category, Integer>{
	Category findCateoryByCode(String code);
}
