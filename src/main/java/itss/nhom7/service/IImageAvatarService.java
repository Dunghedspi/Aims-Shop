package itss.nhom7.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImageAvatarService {
	void addImageAvatar(MultipartFile file, int userId);
	Resource findImageAvatarByUserID(int userId);
}
