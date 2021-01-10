package itss.nhom7.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itss.nhom7.model.MediaModel;
import itss.nhom7.model.ProductModel;
import itss.nhom7.service.impl.ProductService;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Them san pham theo loai
	@PostMapping(value = "/addProduct", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> addProduct(ProductModel product) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		try {
			if (productService.addProduct(product)) {
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);
	}

	// Lay ran dom 20 sp khi khoi dong project
	@GetMapping(value = "/getProductRandom")
	public ResponseEntity<List<MediaModel>> getListMediaRandom() {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		List<MediaModel> listMediaModel = new ArrayList<MediaModel>();
		try {
			listMediaModel = productService.getListProductRandom();
			return new ResponseEntity<List<MediaModel>>(listMediaModel, HttpStatus.OK);
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<MediaModel>>(listMediaModel, httpStatus);
	}

	// Lay sp sap xep theo gia ca
	@GetMapping(value = "/getProductOrderByPrice")
	public ResponseEntity<List<MediaModel>> getListMediaOrderByPrice() {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		List<MediaModel> listMediaModel = new ArrayList<MediaModel>();
		try {
			listMediaModel = productService.getListProductOrderByPrice();
			return new ResponseEntity<List<MediaModel>>(listMediaModel, HttpStatus.OK);
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<MediaModel>>(listMediaModel, httpStatus);
	}

	// Lay cac san pham ban chay
	@GetMapping(value = "/getProductTrending")
	public ResponseEntity<List<MediaModel>> getListMediaTrending() {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		List<MediaModel> listMediaModel = new ArrayList<MediaModel>();
		try {
			listMediaModel = productService.getListProductTrending();
			return new ResponseEntity<List<MediaModel>>(listMediaModel, HttpStatus.OK);
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<MediaModel>>(listMediaModel, httpStatus);
	}

	// Tim kiem cac san pham theo ten
	@GetMapping(value = "/getProductByName/{name}")
	public ResponseEntity<Object> getProductByName(@PathVariable("name") String name) {
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		try {
			mediaModels = productService.getListProductByName(name);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(mediaModels, httpStatus);
	}

	// Tim kiem cac san pham theo ten, author, artist
	@GetMapping(value = "/getProductSearch/{searchText}")
	public ResponseEntity<Object> getListProductByNameOrAuthorOrArtist(@PathVariable("searchText") String searchText) {
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		try {
			mediaModels = productService.getListProductByNameOrAuthorOrArtist(searchText);
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<Object>(mediaModels, httpStatus);
	}

	// Lay cac san pham theo loai
	@GetMapping(value = "/getProductByCategory/{category}")
	public ResponseEntity<List<MediaModel>> getListMediaCategory(@PathVariable("category") String category) {
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		List<MediaModel> listMediaModel = new ArrayList<MediaModel>();
		try {
			listMediaModel = productService.getListProductByCategory(category);
			return new ResponseEntity<List<MediaModel>>(listMediaModel, HttpStatus.OK);
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<List<MediaModel>>(listMediaModel, httpStatus);
	}

	// Lay cac san pham theo id
	@GetMapping(value = "/getProductById/{id}")
	public ResponseEntity<ProductModel> getProductById(@PathVariable("id") int id) {
		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
		ProductModel productModel = new ProductModel();
		try {
			productModel = productService.getProductModelById(id);
			return new ResponseEntity<ProductModel>(productModel, HttpStatus.OK);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<ProductModel>(productModel, httpStatus);
	}
	
	//Lay anh cua product
	@GetMapping("/productImage/{photo}")
	public ResponseEntity<Object> getImageAvatar1(@PathVariable("photo") String photo) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			Path filename = Paths.get("uploads/product/", photo);
			byte[] buffer = Files.readAllBytes(filename);
			ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
			return ResponseEntity.ok().contentLength(buffer.length).contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE)).body(byteArrayResource);
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	// Sua thong tin san pham
	@PutMapping(value = "/editProduct", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<String> editProduct(ProductModel product) {

		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		try {
			if (productService.editProduct(product)) {
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			System.out.println(e);
		}
		return new ResponseEntity<String>(httpStatus);

	}

}
