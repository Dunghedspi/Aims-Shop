package itss.nhom7.controller;

import itss.nhom7.entities.Product;
import itss.nhom7.helper.Utils;
import itss.nhom7.model.MediaModel;
import itss.nhom7.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private Utils utils;

	//Them san pham theo loai
	@PostMapping(value = "/addProduct", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<String> addProduct(Product product, HttpServletRequest req) {
		Cookie jwt = utils.getCookie(req, "Authorization");
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		if (null != jwt) {
			try {
				productService.addProduct(product, product.getCodeCategory());
				httpStatus = HttpStatus.CREATED;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return new ResponseEntity<String>(httpStatus);
	}

	//Lay cac san pham ban chay
	@GetMapping(value = "/getProductTrending")
	public ResponseEntity<List<MediaModel>> getListMediaTrending() {
		return new ResponseEntity<List<MediaModel>>(productService.getListProductTrending(), HttpStatus.OK);
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
	
}
