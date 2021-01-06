package itss.nhom7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Product;
import itss.nhom7.service.IImageProductService;

@Service
public class ImageProductService implements IImageProductService {

	@Autowired
	private IProductDAO productDao;
	@Override
	public void saveFile(String imageUrl, int productId) {
		Product product = productDao.getOne(productId);
		product.setImageUrl(imageUrl);
		productDao.saveAndFlush(product);
		
	}
}
