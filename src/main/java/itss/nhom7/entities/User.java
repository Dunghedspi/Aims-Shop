package itss.nhom7.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique=true,name="email")
	private String email;
	
	@Column(name="full_name")
	private String fullName;
	//Đổi avatar_url thành image_url cho đồng bộ
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="password")
	private String password;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="role")
	private String role;
	
	@Column(name="active")
	private boolean active;
	
	@OneToOne
	@JoinColumn(name="address_id")
	private Address address;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="update_at")
	private Date updateAt;
	
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	
	@OneToMany(mappedBy = "user")
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	private List<Card> cards = new ArrayList<>();
	
	public List<GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(this.getRole()));

		return authorities;
	}
}
