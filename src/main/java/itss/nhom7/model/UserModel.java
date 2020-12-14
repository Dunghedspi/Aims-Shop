package itss.nhom7.model;

import java.util.Calendar;

import itss.nhom7.form.CustomerForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserModel {
	 
    private String fullName;
    private String avataUrl;
    private String address;
    private String email;
    private String password;
    private String phone;
    private String role;
    private Calendar createdAt;
   
 
    private boolean active;
 
 
    public UserModel(CustomerForm customerForm) {
        this.fullName = customerForm.getFullName();
        this.address = customerForm.getAddress();
        this.email = customerForm.getEmail();
        this.phone = customerForm.getPhone();
        this.active = customerForm.isActive();
    }
}