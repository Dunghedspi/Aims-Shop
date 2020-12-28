package itss.nhom7.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/api/image")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FileImageController {
	
	//private static final Logger logger = LoggerFactory.getLogger(FileImageController.class);
	
//	@Autowired
//	private ImageAvatarService imageAvatarService;
//	@Autowired
//	private ImageProductService imageProductService;
//	
//	@PostMapping("/uploadImageAvatar/{userId}")
//	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,@PathVariable("userId") int userId) {
//		HttpStatus httpStatus = null;
//		try {
//			imageAvatarService.addImageAvatar(file,userId);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			System.out.println(e);
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		
//		return new ResponseEntity<Object>(httpStatus);
//	}
//	
//	@PostMapping("/uploadImageProduct/{productId}")
//	public ResponseEntity<Object> uploadMultiFiles(@RequestParam("file") MultipartFile file,@PathVariable("productId") int productId){
//		HttpStatus httpStatus = HttpStatus.NO_CONTENT;
//		try {
//			imageProductService.addImageProduct(file,productId);
//			httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			System.out.println(e);
//			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		
//		return new ResponseEntity<Object>(httpStatus);
//	}
//	
//	@GetMapping("/getImageAvatar/{userId}")
//	public ResponseEntity<Resource> getImageAvatar(@PathVariable("userId") int userId,HttpServletRequest request){	
//		//HttpStatus httpStatus = null;
//		Resource resource = null;
//		
//		try {
//			resource = imageAvatarService.findImageAvatarByUserID(userId);
//			//httpStatus = HttpStatus.OK;
//		}catch(Exception e) {
//			System.out.println(e);
//			//httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		}catch(IOException e) {
//			logger.info("Could not determine file type.");
//		}
//		if(contentType == null) {
//			contentType = "application/octet-stream";
//		}
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+resource.getFilename()+"\"")
//				.body(resource);
//	}
//	
//
//	@GetMapping("/getImageProduct/{productId}")
//	public ResponseEntity<Resource> getImageFood(@PathVariable("productId") int productId,HttpServletRequest request){	
//		Resource resource = null;
//		try {
//			resource = imageProductService.findImageProductByProductId(productId);
//		}catch(Exception e) {
//			System.out.println(e);
//		}
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		}catch(IOException e) {
//			logger.info("Could not determine file type.");
//		}
//		if(contentType == null) {
//			contentType = "application/octet-stream";
//		}
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+resource.getFilename()+"\"")
//				.body(resource);	
//	}

}
