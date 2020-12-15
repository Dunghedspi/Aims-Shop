package itss.nhom7.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(value="/api/product")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//Them san pham theo loai
	@PostMapping(value="/addProduct/{category}")
	public ResponseEntity<String> addProduct(@PathVariable("category") String category,@RequestBody Product product){
		HttpStatus httpStatus = null;
		try {
			productService.addProduct(product, category);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}
	//Lay cac san pham ban chay
	@GetMapping(value="/getProductTrending")
	public ResponseEntity<List<MediaModel>> getListMediaTrending(){
		return new ResponseEntity<List<MediaModel>>(productService.getListProductTrending(),HttpStatus.OK);
	}
	//Tim kiem cac san pham theo ten
	@GetMapping(value="/getProductByName/{name}")
	public ResponseEntity<Object> getProductByName(@PathVariable("name") String name){
		HttpStatus httpStatus = null;
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		try {
			mediaModels = productService.getListProductByName(name);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(mediaModels,httpStatus);
	}
	//Lay cac san pham theo loai
	@GetMapping(value="/getProduct/{category}")
	public ResponseEntity<List<MediaModel>> getListMediaCategory(@PathVariable("category") String category){
		return new ResponseEntity<List<MediaModel>>(productService.getListProductByCategory(category),HttpStatus.OK);
	}
	//Sua thong tin san pham
	@PutMapping(value="/editProduct/{category}")
	public ResponseEntity<String> editProduct(@PathVariable("category") String category,@RequestBody Product product){
		HttpStatus httpStatus = null;
		try {
			productService.editProduct(product, category);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
		
		
	}
	//xoa san pham
	@DeleteMapping(value="/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") int id){
		
		HttpStatus httpStatus = null;
		try {
			productService.deleteProduct(id);
		}catch(Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		
		return new ResponseEntity<String>(httpStatus);
		
	}
}
