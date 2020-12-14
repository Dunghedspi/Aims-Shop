package itss.nhom7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.entities.Product;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.impl.ProductService;

@RestController
@RequestMapping(value="/aims")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//Them san pham theo loai
	@PostMapping(value="/addProduct/{category}")
	public ResponseEntity<Object> addProduct(@PathVariable("category") String category,@RequestBody Product product){
		productService.addProduct(product, category);
		return new ResponseEntity<Object>("Add Product Successfully!",HttpStatus.OK);
	}
	//Lay cac san pham ban chay
	@GetMapping(value="/getProductTrending")
	public ResponseEntity<List<MediaModel>> getListMediaTrending(){
		return new ResponseEntity<List<MediaModel>>(productService.getListProductTrending(),HttpStatus.OK);
	}
	//Tim kiem cac san pham theo ten
	@GetMapping(value="/getProductByName/{name}")
	public ResponseEntity<List<MediaModel>> getProductByName(@PathVariable("name") String name){
		return new ResponseEntity<List<MediaModel>>(productService.getListProductByName(name),HttpStatus.OK);
	}
	//Lay cac san pham theo loai
	@GetMapping(value="/getProduct/{category}")
	public ResponseEntity<List<MediaModel>> getListMediaCategory(@PathVariable("category") String category){
		return new ResponseEntity<List<MediaModel>>(productService.getListProductByCategory(category),HttpStatus.OK);
	}
	//Sua thong tin san pham
	@PutMapping(value="/editProduct/{category}")
	public ResponseEntity<Object> editProduct(@PathVariable("category") String category,@RequestBody Product product){
		
		productService.editProduct(product, category);
		return new ResponseEntity<Object>("Edit Product Successfully!",HttpStatus.OK);
		
	}
	//xoa san pham
	@DeleteMapping(value="/deleteProduct/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id){
		
		productService.deleteProduct(id);
		
		return new ResponseEntity<Object>("Delete Product Successfully!",HttpStatus.OK);
	}
}
