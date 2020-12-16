package itss.nhom7.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.ICartDAO;
import itss.nhom7.dao.ICategoryDAO;
import itss.nhom7.dao.IOrderDetailDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Cart;
import itss.nhom7.entities.Product;
import itss.nhom7.model.BookPhysicalModel;
import itss.nhom7.model.CDPhysicalModel;
import itss.nhom7.model.CartDetailModel;
import itss.nhom7.model.DVDPhysicalModel;
import itss.nhom7.model.LPPhysicalModel;
import itss.nhom7.model.MediaModel;
import itss.nhom7.model.ProductOrderQuantityCount;
import itss.nhom7.service.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IProductDAO productDao;
	@Autowired
	private IOrderDetailDAO orderDetailDao;
	@Autowired
	private ICartDAO cartDao;
	@Autowired
	private ICategoryDAO categoryDao;
	
	@Override
	public void addProduct(Product product, String code) {
		Product productAdd = new Product();
		if(code.equals("bookPhy")) {
			productAdd=mapBookPhy(modelMapper.map(product, BookPhysicalModel.class));
		}else if(code.equals("cdPhy")) {
			productAdd=mapCDPhy(modelMapper.map(product, CDPhysicalModel.class));
		}else if(code.equals("dvdPhy")) {
			productAdd=mapDVDPhy(modelMapper.map(product, DVDPhysicalModel.class));
		}else if(code.equals("lpPhy")) {
			productAdd=mapLPPhy(modelMapper.map(product, LPPhysicalModel.class));
		}else {
			System.out.println("Edit failed");
		}
		productAdd.setCategory(categoryDao.findCateoryByCode(code));
		productAdd.setDelete(false);
		productDao.save(productAdd);
		
	}

	@Override
	public void editProduct(Product product, String category) {
		Product productEdit = new Product();
		
		if(category.equals("bookPhy")) {
			productEdit = mapBookPhy(modelMapper.map(product, BookPhysicalModel.class));
		}else if(category.equals("cdPhy")) {
			productEdit = mapCDPhy(modelMapper.map(product, CDPhysicalModel.class));
		}else if(category.equals("dvdPhy")) {
			productEdit = mapDVDPhy(modelMapper.map(product, DVDPhysicalModel.class));
		}else if(category.equals("lpPhy")) {
			productEdit = mapLPPhy(modelMapper.map(product, LPPhysicalModel.class));
		}else {
			System.out.println("Edit failed");
		}
		productEdit.setId(productDao.getOne(product.getId()).getId());
		productEdit.setCategory(productDao.getOne(product.getId()).getCategory());
		productDao.saveAndFlush(productEdit);
	}


	@Override
	public void deleteProduct(int id) {
		productDao.deleteById(id);
	}



	@Override
	public List<MediaModel> getListProductTrending() {
		List<ProductOrderQuantityCount> productOrderQuantityCounts = orderDetailDao.countTotalProductOrderQuantity();
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for(ProductOrderQuantityCount productOrderQuantityCount : productOrderQuantityCounts) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(productOrderQuantityCount.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}


	@Override
	public MediaModel getMediaModelById(int id) {
		Product product = productDao.getOne(id);
		MediaModel mediaModel = new MediaModel();
		mediaModel.setId(product.getId());
		mediaModel.setName(product.getName());
		mediaModel.setPrice(product.getPrice());
		mediaModel.setQuantity(product.getQuantity());
		return mediaModel;
	}


	@Override
	public List<MediaModel> getListProductByTokenUser(String tokenUser) {
		Cart cart = cartDao.findByTokenUser(tokenUser);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		if(cart!=null) {
			CartDetailService cartDetailService = new CartDetailService();
			for(CartDetailModel cartDetailModel : cartDetailService.findByCartId(cart.getId())) {
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
		for(Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}
	private Product mapLPPhy(LPPhysicalModel lp) {
		Product product = new Product();
		product.setName(lp.getName());
		product.setPrice(lp.getPrice());
		product.setValue(lp.getValue());
		product.setQuantity(lp.getQuantity());
		product.setArtists(lp.getArtists());
		product.setTracklist(lp.getArtists());
		product.setType(lp.getType());
		product.setInputDate(lp.getInputDate());
		return product;
		
	}

	private Product mapDVDPhy(DVDPhysicalModel dvd) {
		Product product = new Product();
		product.setName(dvd.getName());
		product.setPrice(dvd.getPrice());
		product.setValue(dvd.getValue());
		product.setQuantity(dvd.getQuantity());
		product.setRuntime(dvd.getRuntime());
		product.setSubtitles(dvd.getSubtitles());
		product.setPublicationDate(dvd.getPublicatioDate());
		product.setType(dvd.getType());
		product.setLanguage(dvd.getLanguage());
		product.setAuthor(dvd.getAuthor());
		
		return product;
		
	}

	private Product mapCDPhy(CDPhysicalModel cd) {
		Product product = new Product();
		product.setName(cd.getName());
		product.setPrice(cd.getPrice());
		product.setValue(cd.getValue());
		product.setQuantity(cd.getQuantity());
		product.setArtists(cd.getArtists());
		product.setTracklist(cd.getArtists());
		product.setType(cd.getType());
		product.setInputDate(cd.getInputDate());
		
		return product;
		
	}

	private Product mapBookPhy(BookPhysicalModel book) {
		Product product = new Product();
		product.setName(book.getName());
		product.setPrice(book.getPrice());
		product.setValue(book.getValue());
		product.setQuantity(book.getQuantity());
		product.setAuthor(book.getAuthor());
		
		product.setCoverType(book.getCoverType());
		product.setPublisher(book.getPublisher());
		product.setPublicationDate(book.getPublicationDate());
		product.setPages(book.getPages());
		product.setLanguage(book.getLanguage());
		product.setType(book.getType());
		
		return product;
	}

	@Override
	public List<MediaModel> getListProductByCategory(String code) {
		List<Product> products = productDao.getListProductCode(code);
		List<MediaModel> mediaModels = new ArrayList<MediaModel>();
		for(Product product : products) {
			MediaModel mediaModel = new MediaModel();
			mediaModel = getMediaModelById(product.getId());
			mediaModels.add(mediaModel);
		}
		return mediaModels;
	}	

}
