package itss.nhom7.model;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartModel {

	private int id;

	private String tokenUser;

	private int userId;
	
	private Calendar createdAt;

}
