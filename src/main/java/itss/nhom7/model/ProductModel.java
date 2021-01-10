package itss.nhom7.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

	private int id;
	private String name;
	private int value;
	private int price;
	private int quantity;
	private String author;
	private String coverType;
	private String publisher;
	private String publicationDate;
	private int pages;
	private String language;
	private String type;
	private String barcode;
	private String artists;
	private String tracklist;
	private int runtime;
	private String subtitles;
	private String description;
	private String inputDate;
	private Double weight;
	private Double size;
	private boolean isDelete;
	private String modifiedBy;
	private String modifiedDate;
	private String codeCategory;
	private int saleDiscount;
	private String imageUrl;
	private MultipartFile imageFile;

}
