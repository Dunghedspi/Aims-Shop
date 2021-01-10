package itss.nhom7.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import itss.nhom7.dao.ICartDAO;
import itss.nhom7.dao.ICategoryDAO;
import itss.nhom7.dao.IHistoryCrudProduct;
import itss.nhom7.dao.IOrderDetailDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Cart;
import itss.nhom7.entities.Product;
import itss.nhom7.exception.FileStorageException;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.model.ProductModel;
import itss.nhom7.model.ProductOrderQuantityCount;
import itss.nhom7.service.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	private IProductDAO productDao;
	@Autowired
	private IOrderDetailDAO orderDetailDao;
	@Autowired
	private ICartDAO cartDao;
	@Autowired
	private ICategoryDAO categoryDao;
	@Autowired
	private IHistoryCrudProduct historyProductDao;

	@Override
	public boolean addProduct(ProductModel product) throws ParseException {

		Product productAdd = new Product();
		if (product.getCodeCategory().equals("bookPhy")) {
			productAdd = mapBookPhy(product);
		} else if (product.getCodeCategory().equals("cdPhy")) {
			productAdd = mapCDPhy(product);
		} else if (product.getCodeCategory().equals("dvdPhy")) {
			productAdd = mapDVDPhy(product);
		} else if (product.getCodeCategory().equals("lpPhy")) {
			productAdd = mapLPPhy(product);
		} else {
			System.out.println("Add failed");
			return false;
		}
		productAdd.setCategory(categoryDao.findCateoryByCode(product.getCodeCategory()));
		productAdd.setDelete(false);
		productAdd.setImageUrl(createUrlImageProduct(product.getImageFile()));
		productDao.save(productAdd);

		return true;

	}

	private String createUrlImageProduct(MultipartFile imageFile) {
		String fileName = null;
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String token = String.valueOf(timestamp.getTime());
			fileName = token.concat(Objects.requireNonNull(imageFile.getOriginalFilename()));
			Path path = Paths.get("uploads/product/");
			InputStream inputStream = imageFile.getInputStream();
			Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + imageFile + ". Please try again!", e);
		}
		return fileName;
	}

	@Override
	public boolean editProduct(ProductModel product) throws ParseException {
		
		if(checkTimeUpdate(historyProductDao.getByProductIdAndContent(product.getId(), "insert").getCreatedDate())) {
			Product productEdit = new Product();
			System.out.println("CodeCategory : "+ product.getCodeCategory());
			
			System.out.println(product.getCodeCategory().equals("bookPh"));

			if (product.getCodeCategory().equals("bookPhy")) {
				productEdit = mapBookPhy(product);
			} else if (product.getCodeCategory().equals("cdPhy")) {
				productEdit = mapCDPhy(product);
			} else if (product.getCodeCategory().equals("dvdPhy")) {
				productEdit = mapDVDPhy(product);
			} else if (product.getCodeCategory().equals("lpPhy")) {
				productEdit = mapLPPhy(product);
			} else {
				System.out.println("Edit failed");
				return false;
			}
			productEdit.setId(productDao.getOne(product.getId()).getId());
			productEdit.setCategory(productDao.getOne(product.getId()).getCategory());
			productEdit.setImageUrl(product.getImageUrl());
			productDao.saveAndFlush(productEdit);
			
			return true;
		}
		
		return false;
	}

	private boolean checkTimeUpdate(Calendar createdDate) {
		Calendar nowDate = Calendar.getInstance();
		if(nowDate.get(Calendar.YEAR) == createdDate.get(Calendar.YEAR)) {
			if(nowDate.get(Calendar.MONTH) == createdDate.get(Calendar.MONTH)) {
				if(nowDate.get(Calendar.DATE) == createdDate.get(Calendar.DATE)) {
					if((nowDate.get(Calendar.HOUR) - createdDate.get(Calendar.HOUR)) <= 2)
						return true;
				}
			}
		}
		return false;
	}

	@Override
	public void deleteProduct(int id) {
		if (productDao.existsById(id)) {
			Product product = productDao.getOne(id);
			product.setDelete(true);
			productDao.saveAndFlush(product);
		}
	}

	@Override
	public List<MediaModel> getListProductTrending() {
		List<ProductOrderQuantityCount> productOrderQuantityCounts = orderDetailDao.countTotalProductOrderQuantity();
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (ProductOrderQuantityCount productOrderQuantityCount : productOrderQuantityCounts) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(productOrderQuantityCount.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	@Override
	public List<MediaModel> getListProductByTokenUser(String tokenUser) {
		Cart cart = cartDao.findByTokenUser(tokenUser);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		if (cart != null) {
			CartDetailService cartDetailService = new CartDetailService();
			for (CartDetailModel cartDetailModel : cartDetailService.findByCartId(cart.getId())) {
				MediaModel mediaModel = new MediaModel();
				Product product = productDao.getOne(cartDetailModel.getProductId());
				mediaModel.setId(product.getId());
				mediaModel.setName(product.getName());
				mediaModel.setPrice(product.getPrice());
				mediaModel.setValue(product.getValue());
				mediaModel.setQuantity(cartDetailModel.getQuantity());

				mediaModels.add(mediaModel);
			}
		}

		return mediaModels;
	}

	@Override
	public List<MediaModel> getListProductByName(String nameProduct) {
		List<Product> products = productDao.getListProductByNameContaining(nameProduct);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	// Convert model to entity
	// ====== Start ======
	private Product mapLPPhy(ProductModel lp) throws ParseException {
		Product product = new Product();
		product.setName(lp.getName());
		product.setPrice(lp.getPrice());
		product.setValue(lp.getValue());
		product.setQuantity(lp.getQuantity());
		product.setArtists(lp.getArtists());
		product.setTracklist(lp.getArtists());
		product.setType(lp.getType());
		product.setInputDate(new SimpleDateFormat("yyyy-MM-dd").parse(lp.getInputDate()));
		return product;

	}

	private Product mapDVDPhy(ProductModel dvd) throws ParseException {
		Product product = new Product();
		product.setName(dvd.getName());
		product.setPrice(dvd.getPrice());
		product.setValue(dvd.getValue());
		product.setQuantity(dvd.getQuantity());
		product.setRuntime(dvd.getRuntime());
		product.setSubtitles(dvd.getSubtitles());
		product.setPublicationDate(new SimpleDateFormat("yyyy-MM-dd").parse(dvd.getPublicationDate()));
		product.setType(dvd.getType());
		product.setLanguage(dvd.getLanguage());
		product.setAuthor(dvd.getAuthor());

		return product;

	}

	private Product mapCDPhy(ProductModel cd) throws ParseException {
		Product product = new Product();
		product.setName(cd.getName());
		product.setPrice(cd.getPrice());
		product.setValue(cd.getValue());
		product.setQuantity(cd.getQuantity());
		product.setArtists(cd.getArtists());
		product.setTracklist(cd.getTracklist());
		product.setType(cd.getType());
		product.setInputDate(new SimpleDateFormat("yyyy-MM-dd").parse(cd.getInputDate()));

		return product;

	}

	private Product mapBookPhy(ProductModel book) throws ParseException {
		Product product = new Product();
		product.setName(book.getName());
		product.setPrice(book.getPrice());
		product.setValue(book.getValue());
		product.setQuantity(book.getQuantity());
		product.setAuthor(book.getAuthor());

		product.setCoverType(book.getCoverType());
		product.setPublisher(book.getPublisher());
		product.setPublicationDate(new SimpleDateFormat("yyyy-MM-dd").parse(book.getPublicationDate()));
		product.setPages(book.getPages());
		product.setLanguage(book.getLanguage());
		product.setType(book.getType());

		return product;
	}

	// ====== End ======

	// Convert entity to model
	// ====== Start ======
	private ProductModel mapLPPhyEntityToModel(Product lp) throws ParseException {
		ProductModel product = new ProductModel();
		product.setName(lp.getName());
		product.setPrice(lp.getPrice());
		product.setValue(lp.getValue());
		product.setQuantity(lp.getQuantity());
		product.setArtists(lp.getArtists());
		product.setTracklist(lp.getArtists());
		product.setType(lp.getType());
		product.setInputDate(lp.getInputDate().toString());
		return product;

	}

	private ProductModel mapDVDPhyEntityToModel(Product dvd) throws ParseException {
		ProductModel product = new ProductModel();
		product.setName(dvd.getName());
		product.setPrice(dvd.getPrice());
		product.setValue(dvd.getValue());
		product.setQuantity(dvd.getQuantity());
		product.setRuntime(dvd.getRuntime());
		product.setSubtitles(dvd.getSubtitles());
		product.setPublicationDate(dvd.getPublicationDate().toString());
		product.setType(dvd.getType());
		product.setLanguage(dvd.getLanguage());
		product.setAuthor(dvd.getAuthor());

		return product;

	}

	private ProductModel mapCDPhyEntityToModel(Product cd) throws ParseException {
		ProductModel product = new ProductModel();
		product.setName(cd.getName());
		product.setPrice(cd.getPrice());
		product.setValue(cd.getValue());
		product.setQuantity(cd.getQuantity());
		product.setArtists(cd.getArtists());
		product.setTracklist(cd.getArtists());
		product.setType(cd.getType());
		product.setInputDate(cd.getInputDate().toString());

		return product;

	}

	private ProductModel mapBookPhyEntityToModel(Product book) throws ParseException {
		ProductModel product = new ProductModel();
		product.setName(book.getName());
		product.setPrice(book.getPrice());
		product.setValue(book.getValue());
		product.setQuantity(book.getQuantity());
		product.setAuthor(book.getAuthor());

		product.setCoverType(book.getCoverType());
		product.setPublisher(book.getPublisher());
		product.setPublicationDate(book.getPublicationDate().toString());
		product.setPages(book.getPages());
		product.setLanguage(book.getLanguage());
		product.setType(book.getType());

		return product;
	}

	private MediaModel getMediaModelById(int id) {
		Product product = productDao.getOne(id);
		MediaModel mediaModel = new MediaModel();
		mediaModel.setId(product.getId());
		mediaModel.setName(product.getName());
		mediaModel.setPrice(product.getPrice());
		mediaModel.setQuantity(product.getQuantity());
		mediaModel.setImageUrl(ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/product/productImage/" + product.getImageUrl()).toUriString());
		return mediaModel;
	}

	@Override
	public List<MediaModel> getListProductByCategory(String code) {
		List<Product> products = productDao.getListProductCode(code);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	@Override
	public ProductModel getProductModelById(int id) throws SQLException, ParseException {
		if (productDao.existsById(id)) {
			ProductModel productModel = new ProductModel();
			Product product = productDao.getOne(id);
			if (product.getCategory().getCode().equals("bookPhy")) {
				productModel = mapBookPhyEntityToModel(product);
			} else if (product.getCategory().getCode().equals("cdPhy")) {
				productModel = mapCDPhyEntityToModel(product);
			} else if (product.getCategory().getCode().equals("dvdPhy")) {
				productModel = mapDVDPhyEntityToModel(product);
			} else if (product.getCategory().getCode().equals("lpPhy")) {
				productModel = mapLPPhyEntityToModel(product);
			} else {
				System.out.println("Add failed");
			}
			productModel.setCodeCategory(categoryDao.findCateoryByCode(product.getCategory().getCode()).getCode());
			productModel.setDelete(false);
			productModel.setImageUrl(product.getImageUrl());
			return productModel;
		}

		return null;
	}

	@Override
	public List<MediaModel> getListProductRandom() throws SQLException {
		List<Product> products = productDao.getListProductRandom20();
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	@Override
	public List<MediaModel> getListProductOrderByPrice() throws SQLException {
		List<Product> products = productDao.getListProductOrderPrice();
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	@Override
	public List<MediaModel> getListProductByNameOrAuthorOrArtist(String searchText) throws SQLException {
		List<Product> products = productDao.getListProductByNameContainingOrAuthorContainingOrArtistsContaining(searchText, searchText, searchText);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

	@Override
	public List<MediaModel> getListProductHasSale() throws SQLException {
		List<Product> products = productDao.getListProductHasSale20();
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for (Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}

}
