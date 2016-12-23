package in.saram.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class UnifiedAddressApiApplication {
	static Logger logger = LoggerFactory.getLogger(UnifiedAddressApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UnifiedAddressApiApplication.class, args);
		 
		logger.info("[UnifiedAddressApiApplication START]");
		
	}
}
