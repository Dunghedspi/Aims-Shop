package itss.nhom7.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import itss.nhom7.dao.IImageProductDAO;
import itss.nhom7.dao.IProductDAO;
import itss.nhom7.entities.ImageProduct;
import itss.nhom7.exception.FileStorageException;
import itss.nhom7.exception.MyFileNotFoundException;
import itss.nhom7.properties.FileStorageProperties;
import itss.nhom7.service.IImageProductService;

@Service
public class ImageProductService implements IImageProductService {
private final Path fileStorageLocation;
	
	@Autowired
	private IImageProductDAO imageDao;
	@Autowired
	private IProductDAO productDao;
	
	@Autowired
	public ImageProductService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
										.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		}catch(Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
		}
	}

	@Override
	public void addImageProduct(MultipartFile file, int productId) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
			}
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
			
			ImageProduct image = new ImageProduct();
			image.setFileName(fileName);
			image.setFileType(file.getContentType());
			image.setProduct(productDao.getOne(productId));

			imageDao.save(image);
		} catch (IOException e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
		
	}

	@Override
	public Resource findImageProductByProductId(int productId) {
		try {
			ImageProduct image = imageDao.findByProductId(productId);
			Path filePath = this.fileStorageLocation.resolve(image.getFileName()).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			}else {
				throw new MyFileNotFoundException("File not found avatar !");
			}
			
		}catch(MalformedURLException e) {
			throw new MyFileNotFoundException("File not found avatar !");
		}
	}
}
