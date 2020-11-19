package itss.nhom7.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DVDPhysicalModel extends MediaModel{

	private String type;
	private int runtime;
	private String subtitles;
	private Date publicatioDate;
}
