package com.frankybboy.qajoke;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.frankybboy.qajoke.controller.JokeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QaJokeApplicationTests {

	@Autowired
	JokeController jokeController;

	@Test
	void contextLoads() {
		assertNotNull(jokeController);
	}

}
