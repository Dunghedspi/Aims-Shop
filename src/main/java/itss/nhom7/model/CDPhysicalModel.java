package itss.nhom7.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CDPhysicalModel extends MediaModel {

	private String artists;
	private String tracklist;
	private String type;
	private Date inputDate;
}
