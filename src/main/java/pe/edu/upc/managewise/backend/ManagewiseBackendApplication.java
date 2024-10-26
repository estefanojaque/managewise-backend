package pe.edu.upc.managewise.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ManagewiseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagewiseBackendApplication.class, args);
	}

}
