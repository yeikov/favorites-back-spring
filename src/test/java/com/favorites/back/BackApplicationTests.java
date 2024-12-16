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

		

		//add registries of the same media
		Registry newRegistryA = new Registry("Memories", "comic", "Katsuhiro Ōtomo", "1995");
		Registry newRegistryB = new Registry("Akira", "comic", "Katsuhiro Ōtomo", "1982");
		Registry newRegistryC = new Registry("The legend of mother sarah", "comic", "Katsuhiro Ōtomo", "1990");
		Registry newRegistryD = new Registry("L\'Incal", "comic", "Moebius, Jodorowsky", "1981");
		
		

		restTemplate.postForEntity(path + "/registries", newRegistryA, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryB, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryC, Void.class);
		restTemplate.postForEntity(path + "/registries", newRegistryD, Void.class);
	
	}

	
	@Test
	@DirtiesContext
	void topFavoritesReturnsTheMostValuedTenFavoritesInOrder() {
		long[] viewersIndexs = { 1L, 2L, 3L };
		long[] registriesIndexs = { 1L, 2L, 3L, 4L};


		//add assessments (3 x 4) 
		AssessmentDto newAssessmentDto = new AssessmentDto();


		for (long viewer_i : viewersIndexs) {
			for (long registry_i : registriesIndexs) {
				newAssessmentDto.setViewerId(viewer_i);
				newAssessmentDto.setRegistryId(registry_i);
				newAssessmentDto.setFavorite((int) viewer_i + 5);
				newAssessmentDto.setRecommend(9 - (int) viewer_i);
				newAssessmentDto.setNotes("viewer" + viewer_i);
				
				ResponseEntity<String> createResponseA = restTemplate.postForEntity(path + "/assessments",
				newAssessmentDto, String.class);
				assertThat(createResponseA.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			}
		}

		//list of assessments of a media with different viewers
		ResponseEntity<String> findMediaResponse = restTemplate.getForEntity(path + "/assessments/media/comic", String.class);

		assertThat(findMediaResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(findMediaResponse.getBody());
		Number findMediaResult = documentContext.read("$.length()");
		assertThat(findMediaResult).isEqualTo(8);
		
		JSONArray ids = documentContext.read("$..favorite");
		assertThat(ids).containsOnly(8, 7);

	}

}
