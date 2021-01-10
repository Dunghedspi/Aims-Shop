package itss.nhom7.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="history_crud_product")
public class HistoryCrudProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="product_id")
	private int productId;
	
	@Column(name="content")
	private String content;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_date")
	Calendar createdDate;
	
}
