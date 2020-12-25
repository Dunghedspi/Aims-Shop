package itss.nhom7.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IImageProductService {
	void addImageProduct(MultipartFile file, int productId);
	Resource findImageProductByProductId(int productId);
}
