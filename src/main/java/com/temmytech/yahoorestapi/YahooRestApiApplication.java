package com.temmytech.yahoorestapi;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YahooRestApiApplication {

	public static Logger logger = LoggerFactory.getLogger(YahooRestApiApplication.class);

	@PostConstruct
	public void init()
	{
		logger.info("Application started");
	}

	public static void main(String[] args) {
		logger.info("application execute");
		SpringApplication.run(YahooRestApiApplication.class, args);


	}

}
