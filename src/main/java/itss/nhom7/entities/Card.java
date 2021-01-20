package itss.nhom7.entities;

import java.io.Serializable;

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
@Table(name="card")
public class Card implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name_bank")
	private String nameBank;
	
	@Column(name="number_card")
	private int numberCard;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="tokenUser")
	private String tokenUser;
	
	@Column(name="account_balance") // so du tai khoan
	private String accountBalance;

}
