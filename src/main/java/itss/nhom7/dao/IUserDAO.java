package itss.nhom7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import itss.nhom7.entities.User;

@Transactional
@Repository
public interface IUserDAO extends JpaRepository<User, Integer>  {
	
	User findByEmail(String email);
	

}
