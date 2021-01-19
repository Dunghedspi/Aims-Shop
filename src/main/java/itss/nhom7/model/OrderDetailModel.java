package itss.nhom7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailModel {

	private int id;

	private int quantity;

	private int productId;

	private int orderId;
	
	private int price;

}
