package itss.nhom7.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product_price_audit")
public class ProductPriceAudit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="email")
	private String email;
	
	@Column(name="price_before")
	private int priceBefore;
	
	@Column(name="created_date")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
}
