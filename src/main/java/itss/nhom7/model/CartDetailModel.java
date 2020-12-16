package itss.nhom7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailModel {

	private int id;

	private int quantity;

	private int productId;

	private int cartId;

}
