package itss.nhom7.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import itss.nhom7.model.UserModel;
import itss.nhom7.service.impl.ImageAvatarService;
import itss.nhom7.service.impl.UserService;

@RestController
@RequestMapping(value="/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ImageAvatarService imageAvatarService;

	// Lay thong tin user theo id
	@GetMapping(value = "/getUser/{id}")
	public ResponseEntity<UserModel> viewUser(@PathVariable("id") int id) {

		UserModel userModel = userService.getUser(id);
		if(userModel == null) {
			return new ResponseEntity<UserModel>(userModel, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);

	}
	
	//Lay thong tin theo email (user dang login)
	@GetMapping(value = "/getUserInfo")
	public ResponseEntity<Object> getUserInfo() {
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserModel userModel = null;
		try {
			if (null != auth.getName()) {
				userModel = userService.getUserByEmail(auth.getName());
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

		return new ResponseEntity<Object>(userModel, httpStatus);
	}
	
	//upload anh avatar cho user
	@PostMapping(value = "/uploadImageAvatar")
	public ResponseEntity<String> uploadImageAvatar(@RequestParam("file") MultipartFile file) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String urlImage = "";
		try {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String token = String.valueOf(timestamp.getTime());
			String fileName = token.concat(Objects.requireNonNull(file.getOriginalFilename()));
			Path path = Paths.get("uploads/avatar/");
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			imageAvatarService.saveFile(fileName, auth.getName());
			urlImage = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/api/user/avatar/" + fileName).toUriString();
			httpStatus = HttpStatus.OK;
		} catch (Exception e) {
			e.getStackTrace();
		}

		return new ResponseEntity<String>(urlImage, httpStatus);
	}

	//hien thi anh avatar
	@GetMapping("/avatar/{photo}")
	public ResponseEntity<Object> getImageAvatar1(@PathVariable("photo") String photo) throws SQLException {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		try {
			Path filename = Paths.get("uploads/avatar/", photo);
			byte[] buffer = Files.readAllBytes(filename);
			ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
			return ResponseEntity.ok().contentLength(buffer.length).contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE)).body(byteArrayResource);
		} catch (Exception e) {
			System.out.println(e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Object>(httpStatus);
	}

	// chinh sua thong tin user
	@PutMapping(value = "/editUser")
	public ResponseEntity<Object> editUser(@RequestBody UserModel userModel) throws ParseException {
		if(userService.getUser(userModel.getId()) == null) {
			return new ResponseEntity<Object>("Edit failed!", HttpStatus.NO_CONTENT);
		}
		userService.editUser(userModel);
		return new ResponseEntity<Object>("Edit successfully!", HttpStatus.OK);
	}

}
