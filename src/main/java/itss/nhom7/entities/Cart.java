package itss.nhom7.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="tokenUser")
	private String tokenUser;
	
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="created_at")
	private Calendar createdAt;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    
    @JoinTable(name = "cart_detail", //Tạo ra một join Table tên là "cart_detail"
            joinColumns = @JoinColumn(name = "cart_id"),  // TRong đó, khóa ngoại chính là cart_id trỏ tới class hiện tại (Cart)
            inverseJoinColumns = @JoinColumn(name = "product_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Product)
    )
	
	private List<Product> products = new ArrayList<>();
}
