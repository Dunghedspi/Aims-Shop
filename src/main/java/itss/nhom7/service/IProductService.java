package itss.nhom7.service;

import itss.nhom7.entities.Product;

public interface IProductService {

	public void editProduct(Product product, String category);
	public void deleteProduct(int id);
}
