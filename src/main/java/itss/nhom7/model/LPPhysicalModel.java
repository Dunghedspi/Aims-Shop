package itss.nhom7.model;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LPPhysicalModel extends MediaModel{

	private String artists;
	private String tracklist;
	private String type;
//	private Calendar inputDate;
}
