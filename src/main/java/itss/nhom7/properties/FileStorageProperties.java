package itss.nhom7.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix="file")
public class FileStorageProperties {
	private String uploadDir;
}
