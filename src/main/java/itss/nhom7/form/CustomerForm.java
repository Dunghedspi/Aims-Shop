package itss.nhom7.form;

import itss.nhom7.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerForm {
	 
    private String userName;
    private String fullName;
    private String address;
    private String email;
    private String phone;
    private boolean active;
    
    public CustomerForm(UserModel customerInfo) {
        if (customerInfo != null) {
            this.fullName = customerInfo.getFullName();
           // this.address = customerInfo.getAddress();
            this.email = customerInfo.getEmail();
            this.phone = customerInfo.getPhone();
        }
    }
}