package itss.nhom7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageAvaterModel {
	private int id;
	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private int userId;
	

}
