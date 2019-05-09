package pl.webapp.arbitratus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
/*
	Jędrzej Piasecki
	Projekt na zaliczenie projektu na przedmiot "Technologie internetowe i mobilne". Do stworzenia API użyto: Spring Boot, REST, JPA, Postgres, Security

	~Arbitratus z łacińskiego oznacza "Liczyć"
*/


@SpringBootApplication
@EntityScan(basePackageClasses = {
		ArbitratusApplication.class,
		Jsr310JpaConverters.class
})
public class ArbitratusApplication {
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	public static void main(String[] args) {
		SpringApplication.run(ArbitratusApplication.class, args);
	}

}
