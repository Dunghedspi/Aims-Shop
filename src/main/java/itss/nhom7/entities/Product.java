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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="image_font")
	private String imageFont;
	
	@Column(name="image_back")
	private String imageBack;
	
	@Column(name="image_up")
	private String imageUp;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="price")
	private String price;
	
	@Column(name="size")
	private Double size;
	
	@Column(name="bought")
	private int bought;
	
	@Column(name="status")
	private int status;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Category category;
	
	@OneToMany(mappedBy = "product")
	private List<OrderDetail> orderDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductPriceAudit> productPriceAudits = new ArrayList<>();

}
