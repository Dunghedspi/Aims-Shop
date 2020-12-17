package itss.nhom7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "itss.nhom7" })
@EntityScan(basePackages = { "itss.nhom7.entities" })
public class Aims1Application {

	public static void main(String[] args) {
		SpringApplication.run(Aims1Application.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	
}