package itss.nhom7.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// name, price, value la thuoc tinh chung cho tat ca product
	// quantity la thuoc tinhs chung cho cac san pham physical
	@Column(name="name")
	private String name;
	
	@Column(name="value")
	private int value;
	
	@Column(name="price")
	private int price;
	
	@Column(name="quantity")
	private int quantity;
	
	// cac thuoc tinh  cho book
	
	@Column(name="author")
	private String author;
	
	@Column(name="cover_type")
	private String coverType;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="publication_date")
	private Date publicationDate;
	
	@Column(name="pages")
	private int pages;
	
	@Column(name="language")
	private String language;
	
	@Column(name="type")
	private String type;
	
	@Column(name="barcode")
	private String barcode;
	
	//cac thuoc tinh chung cho cd,lp
	
	@Column(name="artists")
	private String artists;
	
	@Column(name="tracklist")
	private String tracklist;
	
	// cac thuoc tinh cho dvd

	@Column(name="runtime")
	private int runtime;

	@Column(name="subtitles")
	private String subtitles;

	@Column(name="description")
	private String description;
	
	@Column(name="input_date")
	private Date inputDate;
	
	@Column(name="weight")
	private Double weight;
	
	@Column(name="size")
	private Double size;
	
	@Column(name="is_delete")
	private boolean isDelete;
	
	@Column(name="modified_by")
	private String modifiedBy;
	
	@Column(name="modified_date")
	private Date modifiedDate;
	
	@Column(name="product_url")
	private String productUrl;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Category category;
	
	@OneToOne
	@JoinColumn(name="sale_id")
	private Sale sale;
	
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductPriceAudit> productPriceAudits = new ArrayList<>();

	// mappedBy trỏ tới tên biến products ở trong Cart.
    @ManyToMany(mappedBy = "products")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @Exclude
    private List<Cart> carts = new ArrayList<>();
    
    @ManyToMany(mappedBy = "products")
    // LAZY để tránh việc truy xuất dữ liệu không cần thiết. Lúc nào cần thì mới query
    @EqualsAndHashCode.Exclude
    @Exclude
    private List<Order> orders = new ArrayList<>();
}
