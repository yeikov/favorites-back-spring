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
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.registry.RegistryRepository;
import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.viewer.ViewerRepository;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class BackApplicationAssessmentsTests {

	private String path = BackApplication.backEndUrl;

	@Autowired
	ViewerRepository viewerRepository;
	
	@Autowired
	RegistryRepository registryRepository;

	@Autowired
	TestRestTemplate restTemplate;

	/* 
	@BeforeEach
	void setUp() {
		// schema & data sql's not workin ...

		Viewer newViewerA = new Viewer("Kay", "kay@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));
		Viewer newViewerB = new Viewer("Kaneda", "kaneda@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));
		Viewer newViewerC = new Viewer("Tetsuo", "tetsuo@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));

		restTemplate.postForEntity(path + "/viewers", newViewerA, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerB, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerC, Void.class);

		Registry newRegistryA = new Registry("Akira", Media.COMIC.toString(), "Katsuhiro Otomo", "1994"); 

		restTemplate.postForEntity(path + "/registries", newRegistryA, Void.class);

	}

	@Test
	void shouldNotReturnAAssessmentWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments/1000",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	@DirtiesContext
	void shouldReturnAAssessmentAndItsLocationWhenDataIsSaved() {
		Viewer newViewerA = restTemplate.getForEntity(path + "/viewers/1", Viewer.class).getBody();
		Registry newRegistryA = restTemplate.getForEntity(path + "/registries/1", Registry.class).getBody();
		Assessment newAssessment = new Assessment(newViewerA, newRegistryA, 6, 8, "");

		ResponseEntity<String> response = restTemplate
				//.withBasicAuth("Kay", "abc123")
				.postForEntity(path + "/assessments",
						newAssessment, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation()).isNotNull();

	}

	@Test
	@DirtiesContext
	void shouldCreateANewAssessment() {

		Viewer newViewer = new Viewer();
		Optional<Viewer> resViewer = viewerRepository.findById(1L);
		if(resViewer.isPresent()){
			newViewer = resViewer.get();
		}

		Registry newRegistry = new Registry();
		Optional<Registry> resRegistry = registryRepository.findById(1L);
		if(resRegistry.isPresent()){
			newRegistry = resRegistry.get();
		}

		Assessment newAssessment = new Assessment(newViewer, newRegistry, 6, 8, "");

		// review 
		//Viewer newViewerA = restTemplate.getForEntity(path + "/viewers/1", Viewer.class).getBody();
		//Registry newRegistryA = restTemplate.getForEntity("/registries/1", Registry.class).getBody();

		ResponseEntity<Void> createResponse = restTemplate
		//.withBasicAuth("Kay", "kay123")
		.postForEntity(path + "/assessments",
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
		assertThat(amounts).containsExactlyInAnyOrder("Neo-Tokio", "Neo-Tokio", "Neo-Tokio");
	}

	@Test
	void shouldReturnAPageOfAssessments() {

		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments?page=0&size=1&sort=id,asc",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}

	// security xtras
	@Test
	void shouldNotReturnAAssessmentWhenUsingBadCredentials() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("BAD-USER", "abc123")
				.getForEntity("/assessments/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		response = restTemplate
				.withBasicAuth("kay", "BAD-PASSWORD")
				.getForEntity("/assessments/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}


	@Test
	void shouldRejectUsersWhoAreNotCardOwners() {
		ResponseEntity<String> response = restTemplate
		.withBasicAuth("Ryu No Assessments", "qrs456")
		.getForEntity("/assessments/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	} 
	*/
}
