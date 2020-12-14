package itss.nhom7.service;

import java.util.ArrayList;

import itss.nhom7.model.CartDetailModel;

public interface ICartDetailService {
	ArrayList<CartDetailModel> findByCartId(int id);			//lay thong tin chi tiet gio hang theo id gio hang
	boolean addProductToCart(CartDetailModel cartDetailModel);	//them san pham vao gio hang
	void editCartDetail(CartDetailModel cartDetailModel);     	//thay doi ve so luong san pham trong gio hang
	void deleteProduct(CartDetailModel cartDetailModel);		//xoa san pham trong gio hang
	void deleteCartDetail(int idCart);							//Xoa du lieu trong bang cart_detail sau khi khach hang da order(tao hoa don)
}
