package itss.nhom7.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import itss.nhom7.model.MediaModel;
import itss.nhom7.model.ProductModel;

public interface IProductService {
	
	List<MediaModel> getListProductByCategory(String code) throws SQLException;						//lay danh sach san pham theo loai
	boolean addProduct(ProductModel product) throws SQLException, ParseException;					//them san pham vao kho (admin)
	ProductModel getProductModelById(int id) throws SQLException, ParseException;					//lay san pham theo id
	boolean editProduct(ProductModel product) throws SQLException, ParseException;					//thay doi thong tin san pham(admin)
	void deleteProduct(int id) throws SQLException;													//xoa san pham(admin)
	List<MediaModel> getListProductByTokenUser(String tokenUser) throws SQLException;				//lay san pham theo tokenuser
	List<MediaModel> getListProductTrending() throws SQLException;									//lay cac san pham dang ban chay
	List<MediaModel> getListProductHasSale() throws SQLException;									//lay cac san pham dang co sale
	List<MediaModel> getListProductByName(String nameProduct) throws SQLException;					//tim kiem san pham bang ten
	List<MediaModel> getListProductByNameOrAuthorOrArtist(String searchText) throws SQLException;	//tim kiem san pham bang ten, author, artist
	List<MediaModel> getListProductRandom() throws SQLException;									//tim kiem san pham ngau nhien
	List<MediaModel> getListProductOrderByPrice() throws SQLException;								//lay san pham sap xep theo gia
}
