package com.srt.FreelanceMarketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FreelanceMarketplaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreelanceMarketplaceApplication.class, args);
	}

}
