package itss.nhom7.service.impl;

import itss.nhom7.service.IImageAvatarService;
//@Service
public class ImageAvatarService implements IImageAvatarService{
	
	//private final Path fileStorageLocation;
	
//	@Autowired
//	private IImageAvatarDAO imageDao;
//	@Autowired
//	private IUserDAO userDao;
	
//	@Autowired
//	public ImageAvatarService(FileStorageProperties fileStorageProperties) {
//		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//										.toAbsolutePath().normalize();
//		try {
//			Files.createDirectories(this.fileStorageLocation);
//		}catch(Exception e) {
//			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
//		}
//	}
//
//	@Override
//	public void addImageAvatar(MultipartFile file, int userId) {
//		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		try {
//			if (fileName.contains("..")) {
//				throw new FileStorageException("Sorry! Filenamecontains invalid path sequence" + fileName);
//			}
//			Path targetLocation = this.fileStorageLocation.resolve(fileName);
//			Files.copy(file.getInputStream(), targetLocation,StandardCopyOption.REPLACE_EXISTING);
//			
//			ImageAvatar image = new ImageAvatar();
////			image.setFileName(fileName);
////			image.setFileType(file.getContentType());
//			//image.setUser(userDao.getOne(userId));
//
//			imageDao.save(image);
//		} catch (IOException e) {
//			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
//		}
//		
//	}
//
//	@Override
//	public Resource findImageAvatarByUserID(int userId) {
//		try {
//			ImageAvatar image = imageDao.findByUserId(userId);
//			Path filePath = this.fileStorageLocation.resolve(image.getFileName()).normalize();
//			Resource resource = new UrlResource(filePath.toUri());
//			if(resource.exists()) {
//				return resource;
//			}else {
//				throw new MyFileNotFoundException("File not found avatar !");
//			}
//			
//		}catch(MalformedURLException e) {
//			throw new MyFileNotFoundException("File not found avatar !");
//		}
//	}

}
