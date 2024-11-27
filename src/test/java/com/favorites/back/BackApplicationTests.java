package com.favorites.back;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.favorites.back.entities.viewer.Viewer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class BackApplicationTests {

	private String path = BackApplication.backEndUrl;

	@Autowired
	TestRestTemplate restTemplate;

	/*
	 * 
	 * @Test
	 * public void testFindRecent() {
	 * //Viewer viewer = new Viewer("Orlando", "Orlando@Orlando.exp");
	 * 
	 * ResponseEntity<String> response = restTemplate.getForEntity(path +
	 * "/viewer/1", String.class);
	 * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	 * 
	 * DocumentContext documentContext = JsonPath.parse(response.getBody());
	 * Number id = documentContext.read("$.id");
	 * assertThat(id).isEqualTo(1);
	 * 
	 * Double amount = documentContext.read("$.name");
	 * assertThat(amount).isEqualTo("ka_y");
	 * 
	 * }
	 */
/* 
	@Test
	void shouldReturnAViewerWhenDataIsSaved() {
		ResponseEntity<String> response = restTemplate.postForEntity("/viewers",
				new Viewer("Kay", "kay@tokio.exp", "Tokio", LocalDate.parse("2000-11-26")), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		Number id = documentContext.read("$.id");
		assertThat(id).isEqualTo(1);

		Double amount = documentContext.read("$.name");
		assertThat(amount).isEqualTo("Kay");
	}

	@Test
	void shouldNotReturnAViewerWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/viewers/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	@DirtiesContext
	void shouldCreateANewViewer() {
		Viewer newViewer = new Viewer("Kay", "kay@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		ResponseEntity<Void> createResponse = restTemplate.postForEntity("/viewers", newViewer, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewViewer = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewViewer, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");

		assertThat(id).isNotNull();
		assertThat(name).isEqualTo("Kay");
	} */

	@Test
	void shouldReturnAllViewersWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/viewers", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int viewerCount = documentContext.read("$.length()");
		assertThat(viewerCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..name");
		assertThat(ids).containsExactlyInAnyOrder("Kay", "Tetsuo", "Kaneda");

		JSONArray amounts = documentContext.read("$..city");
		assertThat(amounts).containsExactlyInAnyOrder("Tokio", "Tokio", "Tokio");
	} 
 
	@Test
	void shouldReturnAPageOfViewers() {

		//schema & data sql's not workin ...
		/* */
		  
		Viewer newViewerA = new Viewer("Kay", "kay@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		Viewer newViewerB = new Viewer("Tetsuo", "tetsuo@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		Viewer newViewerC = new Viewer("Kaneda", "kaneda@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		
		restTemplate.postForEntity( path + "/viewers", newViewerA, Void.class);
		restTemplate.postForEntity( path + "/viewers", newViewerB, Void.class);
		restTemplate.postForEntity( path + "/viewers", newViewerC, Void.class);
		
		
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/viewers?page=0&size=1&sort=id,asc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}
 
}
