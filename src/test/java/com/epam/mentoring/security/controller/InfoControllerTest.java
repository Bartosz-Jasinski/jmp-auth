package com.epam.mentoring.security.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InfoControllerTest {
	@Value(value = "${local.server.port}")
	private int port;

	@Autowired
	private InfoController controller;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void infoShouldReceiveRandomFunfact() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/info",
				String.class)).containsAnyOf("A shrimp's heart is in its head.",
				"Rubber bands last longer when refrigerated.",
				"&quot;Dreamt&quot; is the only English word that ends in the letters &quot;mt&quot;.",
				"Almonds are a member of the peach family.",
				"Most people fall asleep in seven minutes.");
	}
}
