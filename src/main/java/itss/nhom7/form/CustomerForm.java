package itss.nhom7.form;

import itss.nhom7.model.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerForm {
	 
    private String name;
    private String address;
    private String email;
    private String phone;
 
    private boolean valid;
 
   
 
    public CustomerForm(CustomerInfo customerInfo) {
        if (customerInfo != null) {
            this.name = customerInfo.getName();
            this.address = customerInfo.getAddress();
            this.email = customerInfo.getEmail();
            this.phone = customerInfo.getPhone();
        }
    }
}