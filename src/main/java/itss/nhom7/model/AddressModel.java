package itss.nhom7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {
	private int id;
	private String country;
	private String district;
	private String village;
	private String street;
	private boolean active;

}