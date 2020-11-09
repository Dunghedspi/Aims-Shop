package itss.nhom7.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
	
	private String code;
    private String name;
    private double price;
 
    private boolean newProduct = false;
 
    // Upload file.
    private MultipartFile fileData;

}
