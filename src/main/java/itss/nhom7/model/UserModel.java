package itss.nhom7.model;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserModel {
	 
	private int id;
    private String fullName;
    private String avataUrl;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String sex;
    private Calendar createdAt;
    private String dateOfBirth;
    private String country;
    private String province;
	private String district;
	private String village;
	private String street;
    private boolean active;
 
 
//    public UserModel(CustomerForm customerForm) {
//        this.fullName = customerForm.getFullName();
//        this.address = customerForm.getAddress();
//        this.email = customerForm.getEmail();
//        this.phone = customerForm.getPhone();
//        this.active = customerForm.isActive();
//    }
}