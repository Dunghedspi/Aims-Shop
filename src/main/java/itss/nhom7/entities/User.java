package itss.nhom7.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	@Column(name="avata_url")
	private String avataUrl;
	
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
	private Calendar createdAt;

	@Column(name = "date_of_birth")
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
