package itss.nhom7.service;

import java.sql.SQLException;
import java.util.List;

import itss.nhom7.entities.Product;
import itss.nhom7.model.MediaModel;

public interface IProductService {
	
	List<MediaModel> getListProductByCategory(String code) throws SQLException;		//lay danh sach san pham theo loai
	void addProduct(Product product, String category) throws SQLException;			//them san pham vao kho (admin)
	MediaModel getMediaModelById(int id) throws SQLException;						//lay san pham theo id
	void editProduct(Product product, String category) throws SQLException;			//thay doi thong tin san pham(admin)
	void deleteProduct(int id) throws SQLException;									//xoa san pham(admin)
	List<MediaModel> getListProductByTokenUser(String tokenUser) throws SQLException;//lay san pham theo tokenuser
	List<MediaModel> getListProductTrending() throws SQLException;					//lay cac san pham dang ban chay
	List<MediaModel> getListProductByName(String nameProduct) throws SQLException;	//tim kiem san pham bang ten
}
