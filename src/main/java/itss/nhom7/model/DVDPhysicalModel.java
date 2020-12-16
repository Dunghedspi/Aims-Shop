package itss.nhom7.model;

import java.util.Calendar;

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
	private Calendar publicatioDate;
	private String language;
	private String author;
}
