package itss.nhom7.model;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPhysicalModel extends MediaModel{
	
	private String author;
	private String coverType;
	private String publisher;
	private Date publicationDate;
	private int pages;
	private String language;
	private String type;

}
