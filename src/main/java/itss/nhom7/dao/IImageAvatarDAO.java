package itss.nhom7.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import itss.nhom7.entities.ImageAvatar;

@Repository
@Transactional
public interface IImageAvatarDAO extends JpaRepository<ImageAvatar, Integer>{
	ImageAvatar findByUserId(int userId);
}
