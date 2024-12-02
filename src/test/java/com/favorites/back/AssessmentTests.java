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

import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.assessment.Assessment;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AssessmentTest {

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

	@Test
	void shouldNotReturnAAssessmentWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments/1000",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}
/* 
	@Test
	@DirtiesContext
	void shouldReturnAAssessmentLocationWhenDataIsSaved() {

		//Viewer newViewerA = restTemplate.getForEntity(path + "/viewers/1", Viewer.class).getBody();
		//Registry newRegistyA = restTemplate.getForEntity(path + "/registries/1", Registry.class).getBody();

		ResponseEntity<Viewer> responseViewer = restTemplate.getForEntity(path + "/viewers/1", Viewer.class);
		Viewer documentViewerContext = responseViewer.getBody();

		ResponseEntity<Registry> responseRegistry = restTemplate.getForEntity(path + "/registries/1", Registry.class);
		Registry documentRegistryContext = responseRegistry.getBody();
		
		//ResponseEntity<Registry> responseRegistry = restTemplate.getForEntity(path + "/registries/1", Registry.class);
		//DocumentContext documentRegistryContext = JsonPath.parse(responseRegistry.getBody());
		//Registry _registry = new Registry();
//
		//_registry.setId(documentRegistryContext.read("$.id"));
		//_registry.setTitle(documentRegistryContext.read("$.title"));
		//_registry.setAuthor(documentRegistryContext.read("$.author"));
		//_registry.setMedia(documentRegistryContext.read("$.media"));
		//_registry.setProductionDate(documentRegistryContext.read("$.productionDate"));


		Assessment newAssessment = new Assessment(documentViewerContext, documentRegistryContext, 5, 6, "Some notes");

		ResponseEntity<String> response = restTemplate.postForEntity(path + "/assessments", 
				newAssessment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation()).isNotNull();
		
	} */
 

	@Test
	@DirtiesContext
	void shouldCreateANewAssessment() {

		Viewer newViewerA = restTemplate.getForEntity(path + "/viewers/1", Viewer.class).getBody();
		Registry newRegistyA = restTemplate.getForEntity(path + "/registrie/1", Registry.class).getBody();

		Assessment newAssessment = new Assessment(newViewerA, newRegistyA, 5, 6, "Some notes");

		ResponseEntity<Void> createResponse = restTemplate.postForEntity(path + "/assessments",
				newAssessment, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewAssessment = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewAssessment, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");

		assertThat(id).isNotNull();
		assertThat(name).isEqualTo("Yamagata");
	}
	
/* 
	@Test
	void shouldReturnAllAssessmentsWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int assessmentCount = documentContext.read("$.length()");
		assertThat(assessmentCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..name");
		assertThat(ids).containsExactlyInAnyOrder("Kay", "Tetsuo", "Kaneda");

		JSONArray amounts = documentContext.read("$..city");
		assertThat(amounts).containsExactlyInAnyOrder("Tokio", "Tokio", "Tokio");
	}
 */
/* 
	@Test
	void shouldReturnAPageOfAssessments() {

		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments?page=0&size=1&sort=id,asc",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}
 */
}
