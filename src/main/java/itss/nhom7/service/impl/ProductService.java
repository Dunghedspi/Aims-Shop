package itss.nhom7.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.Product;
import itss.nhom7.model.BookPhysicalModel;
import itss.nhom7.model.CDPhysicalModel;
import itss.nhom7.model.DVDPhysicalModel;
import itss.nhom7.model.LPPhysicalModel;
import itss.nhom7.service.IProductService;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IProductDAO productDao;

	@Override
	public void editProduct(Product product, String category) {
		
		if(category.equals("bookPhy")) {
			editBookPhy(modelMapper.map(product, BookPhysicalModel.class));
		}else if(category.equals("cdPhy")) {
			editCDPhy(modelMapper.map(product, CDPhysicalModel.class));
		}else if(category.equals("dvdPhy")) {
			editDVDPhy(modelMapper.map(product, DVDPhysicalModel.class));
		}else if(category.equals("lpPhy")) {
			editLPPhy(modelMapper.map(product, LPPhysicalModel.class));
		}
		
	}


	private void editDVDPhy(DVDPhysicalModel dvd) {
		// TODO Auto-generated method stub
		
	}


	private void editLPPhy(LPPhysicalModel lp) {
		// TODO Auto-generated method stub
		
	}


	private void editCDPhy(CDPhysicalModel cd) {
		// TODO Auto-generated method stub
		
	}

	private void editBookPhy(BookPhysicalModel book) {
		
		Product product = productDao.getOne(book.getId());
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
		
		productDao.save(product);
		
	}


	@Override
	public void deleteProduct(int id) {
		
		productDao.deleteById(id);
		
		
	}

}
