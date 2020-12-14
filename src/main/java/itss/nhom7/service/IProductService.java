package itss.nhom7.service;

import java.util.List;

import itss.nhom7.entities.Product;
import itss.nhom7.model.MediaModel;

public interface IProductService {
	
	List<MediaModel> getListProductByCategory(String code);		//lay danh sach san pham theo loai
	void addProduct(Product product, String category);			//them san pham vao kho (admin)
	MediaModel getMediaModelById(int id);						//lay san pham theo id
	void editProduct(Product product, String category);			//thay doi thong tin san pham(admin)
	void deleteProduct(int id);									//xoa san pham(admin)
	List<MediaModel> getListProductByTokenUser(String tokenUser);//lay san pham theo tokenuser
	List<MediaModel> getListProductTrending();					//lay cac san pham dang ban chay
	List<MediaModel> getListProductByName(String nameProduct);	//tim kiem san pham bang ten
}
