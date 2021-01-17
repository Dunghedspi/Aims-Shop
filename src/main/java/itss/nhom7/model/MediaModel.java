package itss.nhom7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaModel {
	private int id;
	private String name;
	private int price;
	private int value;
	private int quantity;
	private String inputDate;
	private String productImage;
	private Double size;
	private Double weight;
	private String barCode;
}