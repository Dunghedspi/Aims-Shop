package itss.nhom7.model;

import java.time.LocalDate;
import java.util.Calendar;

import itss.nhom7.form.CustomerForm;
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
    private String address;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String sex;
    private Calendar createdAt;
    private LocalDate dateOfBirth;
   
 
    private boolean active;
 
 
    public UserModel(CustomerForm customerForm) {
        this.fullName = customerForm.getFullName();
        this.address = customerForm.getAddress();
        this.email = customerForm.getEmail();
        this.phone = customerForm.getPhone();
        this.active = customerForm.isActive();
    }
}