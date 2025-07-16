package fr.phenix333.semantop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

import fr.phenix333.logger.MyLogger;

@CrossOrigin
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	private static final MyLogger L = MyLogger.create(ApiApplication.class);

	public static void main(String[] args) {
		L.function("Starting Semantop API Application");

		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		L.debug("Start main code");

		//
	}

}
