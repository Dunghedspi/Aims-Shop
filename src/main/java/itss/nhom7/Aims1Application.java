package itss.nhom7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import itss.nhom7.properties.FileStorageProperties;

@SpringBootApplication(scanBasePackages = { "itss.nhom7" })
@EntityScan(basePackages = { "itss.nhom7.entities" })
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class Aims1Application {

	public static void main(String[] args) {
		SpringApplication.run(Aims1Application.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
}