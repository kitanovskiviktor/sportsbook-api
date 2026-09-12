package com.sportsbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class SportsbookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsbookApiApplication.class, args);
	}

}
