package itss.nhom7.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	
	@Column(name="username")
	private String userName;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="avata_url")
	private String avataUrl;
	
	@Column(name="password")
	private String password;
	
	@Column(name="address")
	private String address;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="role")
	private String role;
	
	@Column(name="active")
	private boolean active;
	
	
	@Column(name="remember_token")
	private String rememberToken;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private List<Cart> carts = new ArrayList<>();
	
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
