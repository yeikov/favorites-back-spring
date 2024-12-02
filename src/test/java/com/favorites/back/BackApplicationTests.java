package com.favorites.back;

import net.minidev.json.JSONArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.favorites.back.entities.assessment.Assessment;
import com.favorites.back.entities.assessment.AssessmentDto;
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class BackApplicationTests {

	private String path = BackApplication.backEndUrl;

	@Autowired
	TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		// schema & data sql's not workin ...

		Viewer newViewerA = new Viewer("Kay", "kay@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		Viewer newViewerB = new Viewer("Tetsuo", "tetsuo@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		Viewer newViewerC = new Viewer("Kaneda", "kaneda@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));

		restTemplate.postForEntity(path + "/viewers", newViewerA, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerB, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerC, Void.class);

		

		Registry newRegistryA = new Registry("The Hobbit", "book", "J. R. R. Tolkien","1937" );
		Registry newRegistryB = new Registry("Akira", "comic", "Katsuhiro Ōtomo","1982" );
		Registry newRegistryC = new Registry("The legend of mother sarah", "comic", "Katsuhiro Ōtomo","1990" );
		

		restTemplate.postForEntity(path + "/registries", newRegistryA, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryB, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryC, Void.class);
	
	}


	

}
