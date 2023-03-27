package com.temmytech.yahoorestapi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class YahooRestApiApplicationTests {

	public static Logger logger = LoggerFactory.getLogger(YahooRestApiApplicationTests.class);
	@Test
	 void contextLoads() {

		loggers.info("Test Case Executing");
		assertEquals(true,true);
		logger.info("Running second statement");
	}

}
