package itss.nhom7.model;

import lombok.Data;

@Data
public class ProductOrderQuantityCount {

	private int id;
	private Long total;
	public ProductOrderQuantityCount(int id, Long total) {
		this.id = id;
		this.total = total;
	}
	
	
	
}
