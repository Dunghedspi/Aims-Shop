package itss.nhom7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = { "itss.nhom7" })
@EntityScan(basePackages = { "itss.nhom7.entities" })
public class Aims1Application {

	public static void main(String[] args) {
		SpringApplication.run(Aims1Application.class, args);
	}

}