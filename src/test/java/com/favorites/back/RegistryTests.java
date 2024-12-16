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

import com.favorites.back.entities.assessment.AssessmentDto;
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.viewer.Viewer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;

import java.net.URI;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class RegistryTests {

	private String path = BackApplication.backEndUrl;

	@Autowired
	TestRestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		// schema & data sql's not workin ...

		Registry newRegistryA = new Registry("The Hobbit", "book", "J. R. R. Tolkien", "1937");
		Registry newRegistryB = new Registry("Akira", "comic", "Katsuhiro Ōtomo", "1982");
		Registry newRegistryC = new Registry("The legend of mother sarah", "comic", "Katsuhiro Ōtomo", "1990");

		restTemplate.postForEntity(path + "/registries", newRegistryA, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryB, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryC, Void.class);
	}

	@Test
	void shouldNotReturnARegistryWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/registries/1000",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}

	@Test
	@DirtiesContext
	void shouldReturnARegistryLocationWhenDataIsSaved() {
		Registry newRegistry = new Registry("Memories", "comic", "Katsuhiro Ōtomo", "1995");

		ResponseEntity<String> response = restTemplate.postForEntity(path + "/registries",
				newRegistry, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation()).isNotNull();

	}

	@Test
	@DirtiesContext
	void shouldCreateANewRegistry() {
		Registry newRegistry = new Registry("Memories", "comic", "Katsuhiro Ōtomo", "1995");
		ResponseEntity<String> postResponse = restTemplate.postForEntity(path + "/registries",
				newRegistry, String.class);
		assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		DocumentContext documentCreateContext = JsonPath.parse(postResponse.getBody());

		Number postId = documentCreateContext.read("$.id");
		assertThat(postId).isNotNull();

		String postTitle = documentCreateContext.read("$.title");
		assertThat(postTitle).isEqualTo("Memories");

		URI locationOfNewRegistry = postResponse.getHeaders().getLocation();

		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewRegistry, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number getId = documentContext.read("$.id");
		String getName = documentContext.read("$.title");

		assertThat(getId).isNotNull();
		assertThat(getId).isEqualTo(postId);
		assertThat(getName).isEqualTo("Memories");
	}

	@Test
	void shouldReturnAllRegistriesWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/registries", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int registryCount = documentContext.read("$.length()");
		assertThat(registryCount).isBetween(1, 10);
	}

	@Test
	void shouldReturnAPageOfRegistries() {

		ResponseEntity<String> response = restTemplate.getForEntity(path + "/registries?page=0&size=1&sort=id,asc",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}

	@Test
	@DirtiesContext
	void shouldReturnTheTopTenMostFavoriteRecordsInDescendingOrderByMedia() {

		Viewer newViewerA = new Viewer("Kay", "kay@tokio.exp", "Tokio", LocalDate.parse("2000-11-26"));
		restTemplate.postForEntity(path + "/viewers", newViewerA, Void.class);

		AssessmentDto newAssessmentDtoB = new AssessmentDto();
		newAssessmentDtoB.setViewerId(1L);
		newAssessmentDtoB.setRegistryId(2L);
		newAssessmentDtoB.setFavorite(0);
		newAssessmentDtoB.setRecommend(2);
		newAssessmentDtoB.setNotes("reg 2");

		AssessmentDto newAssessmentDtoC = new AssessmentDto();
		newAssessmentDtoC.setViewerId(1L);
		newAssessmentDtoC.setRegistryId(3L);
		newAssessmentDtoC.setFavorite(9);
		newAssessmentDtoC.setRecommend(1);
		newAssessmentDtoC.setNotes("reg 3");

		ResponseEntity<String> createResponseB = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoB, String.class);
		assertThat(createResponseB.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ResponseEntity<String> createResponseC = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDtoC, String.class);
		assertThat(createResponseC.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ResponseEntity<String> response = restTemplate.getForEntity(path + "/registries/topFavorite/comic",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray list = documentContext.read("$[*]");
		assertThat(list.size()).isEqualTo(2);

		JSONArray scores = documentContext.read("$..favoriteSum");
		assertThat(scores).containsExactly(9.0, 0.0);

		ResponseEntity<String> responseRecommend = restTemplate.getForEntity(path + "/registries/topRecommend/comic",
				String.class);
		assertThat(responseRecommend.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContextRecommend = JsonPath.parse(responseRecommend.getBody());
		JSONArray recommendList = documentContextRecommend.read("$[*]");
		assertThat(recommendList.size()).isEqualTo(2);

		JSONArray recomendScores = documentContextRecommend.read("$..recommendSum");
		assertThat(recomendScores).containsExactly(2.0, 1.0);
	}

}
