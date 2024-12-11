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
import com.favorites.back.entities.assessment.AssessmentDto;
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
	void shouldReturnNotAllowedWhenAssessmentsListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);

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
	void shouldCreateANewAssessmentAndReturnItAndItsLocation() {

		Viewer getViewer = restTemplate.getForEntity(path + "/viewers/1", Viewer.class).getBody();

		ResponseEntity<String> responseRegistry = restTemplate.getForEntity(path + "/registries/1", String.class);
		DocumentContext documentRegistryContext = JsonPath.parse(responseRegistry.getBody());
		Registry getRegistry = new Registry();
		Integer index = documentRegistryContext.read("$.id");
		String date = documentRegistryContext.read("$.productionDate");

		getRegistry.setId((long) index);
		getRegistry.setTitle(documentRegistryContext.read("$.title"));
		getRegistry.setAuthor(documentRegistryContext.read("$.author"));
		getRegistry.setMedia(documentRegistryContext.read("$.media"));
		getRegistry.setProductionDate(LocalDate.parse(date));

		AssessmentDto newAssessmentDto = new AssessmentDto();
		newAssessmentDto.setViewerId(getViewer.getId());
		newAssessmentDto.setRegistryId(getRegistry.getId());
		newAssessmentDto.setFavorite(0);
		newAssessmentDto.setRecommend(5);
		newAssessmentDto.setNotes("great");

		ResponseEntity<String> createResponse = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDto, String.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewAssessment = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewAssessment, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		int favorite = documentContext.read("$.favorite");

		assertThat(id).isNotNull();
		assertThat(favorite).isEqualTo(0);
	}

	@Test
	
	@DirtiesContext
	void shouldReturnAllAssessmentsOfAViewer() {

		AssessmentDto newAssessmentDtoA = new AssessmentDto();
		newAssessmentDtoA.setViewerId(1L);
		newAssessmentDtoA.setRegistryId(1L);
		newAssessmentDtoA.setFavorite(5);
		newAssessmentDtoA.setRecommend(4);
		newAssessmentDtoA.setNotes("reg 1");

		AssessmentDto newAssessmentDtoB = new AssessmentDto();
		newAssessmentDtoB.setViewerId(1L);
		newAssessmentDtoB.setRegistryId(2L);
		newAssessmentDtoB.setFavorite(2);
		newAssessmentDtoB.setRecommend(2);
		newAssessmentDtoB.setNotes("reg 2");

		AssessmentDto newAssessmentDtoC = new AssessmentDto();
		newAssessmentDtoC.setViewerId(1L);
		newAssessmentDtoC.setRegistryId(3L);
		newAssessmentDtoC.setFavorite(5);
		newAssessmentDtoC.setRecommend(4);
		newAssessmentDtoC.setNotes("reg 3");

		ResponseEntity<String> createResponseA = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoA, String.class);
		assertThat(createResponseA.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		

		ResponseEntity<String> createResponseB = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoB, String.class);
		assertThat(createResponseB.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		

		ResponseEntity<String> createResponseC = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoC, String.class);
		assertThat(createResponseC.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments/viewer/1?page=0&size=10&sort=registeredAt,asc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int assessmentCount = documentContext.read("$.length()");
		assertThat(assessmentCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..notes");
		assertThat(ids).containsExactlyInAnyOrder("reg 1", "reg 2", "reg 3");

	}

	
	@Test
	
	@DirtiesContext
	void shouldReturnAllAssessmentsOfARegistry() {

		AssessmentDto newAssessmentDtoA = new AssessmentDto();
		newAssessmentDtoA.setViewerId(1L);
		newAssessmentDtoA.setRegistryId(1L);
		newAssessmentDtoA.setFavorite(5);
		newAssessmentDtoA.setRecommend(4);
		newAssessmentDtoA.setNotes("viewer 1");

		AssessmentDto newAssessmentDtoB = new AssessmentDto();
		newAssessmentDtoB.setViewerId(2L);
		newAssessmentDtoB.setRegistryId(1L);
		newAssessmentDtoB.setFavorite(2);
		newAssessmentDtoB.setRecommend(2);
		newAssessmentDtoB.setNotes("viewer 2");

		AssessmentDto newAssessmentDtoC = new AssessmentDto();
		newAssessmentDtoC.setViewerId(3L);
		newAssessmentDtoC.setRegistryId(1L);
		newAssessmentDtoC.setFavorite(5);
		newAssessmentDtoC.setRecommend(4);
		newAssessmentDtoC.setNotes("viewer 3");

		ResponseEntity<String> createResponseA = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoA, String.class);
		assertThat(createResponseA.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		

		ResponseEntity<String> createResponseB = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoB, String.class);
		assertThat(createResponseB.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		

		ResponseEntity<String> createResponseC = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoC, String.class);
		assertThat(createResponseC.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/assessments/registry/1?page=0&size=10&sort=registeredAt,asc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int assessmentCount = documentContext.read("$.content.length()");
		assertThat(assessmentCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..notes");
		assertThat(ids).containsExactlyInAnyOrder("viewer 1", "viewer 2", "viewer 3");

	}


}
