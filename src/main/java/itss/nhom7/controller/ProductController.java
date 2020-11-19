package itss.nhom7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.entities.Product;
import itss.nhom7.service.impl.ProductService;

@RestController
@RequestMapping(value="/aims")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PutMapping(value="/editProduct/{category}")
	public ResponseEntity<Object> editProduct(@PathVariable("category") String category,@RequestBody Product product){
		
		productService.editProduct(product, category);
		return new ResponseEntity<Object>("Edit Product Successfully!",HttpStatus.OK);
		
	}
	
	@DeleteMapping(value="/deleteProduct/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id){
		
		productService.deleteProduct(id);
		
		return new ResponseEntity<Object>("Delete Product Successfully!",HttpStatus.OK);
	}
}
