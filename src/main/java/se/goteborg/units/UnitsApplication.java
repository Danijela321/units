package se.goteborg.units;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ConditionalOnProperty(name="updating.enabled", matchIfMissing=true)
public class UnitsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnitsApplication.class, args);
	}

}
