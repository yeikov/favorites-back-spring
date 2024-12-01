package com.favorites.back;

import net.minidev.json.JSONArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.favorites.back.entities.viewer.Viewer;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.time.LocalDate;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
// @DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(username = "Master", authorities = {"SCOPE_viewer:read", "SCOPE_viewer:write"})
class BackApplicationViewerTests {

	@Autowired
	MockMvc mvc;

	
	@Test
	@DirtiesContext
	//@WithMockUser(username="Yamagata", authorities = {"SCOPE_viewer:read", "SCOPE_viewer:write"})
	
	void shouldReturnAViewerAndItsLocationWhenDataIsSaved()  throws Exception {
				
		/* Viewer newViewer = new Viewer("Yamagata", "yamagata@neo-tokio.exp", "Neo-Tokio",
				LocalDate.parse("1990-11-26")); */
		String location = this.mvc.perform(post(BackApplication.backEndUrl + "/viewers")
                .with(csrf())
                        .contentType("application/json")
                        .content(
							//newViewer.toString()
						 	 
							"""
							{
								"name" : "Yamagata",
								"city": "Neo-Tokio",
								"eMail": ""yamagata@neo-tokio.exp",
								"birth": "1990-11-26"
							}
							"""
 							
						))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn().getResponse().getHeader("Location");

        this.mvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Yamagata"));

	}


	/* @Autowired
	TestRestTemplate restTemplate; */

	/* @BeforeEach
	void setUp() {
		// schema & data sql's not workin ...

		Viewer newViewerA = new Viewer("Kay", "kay@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));
		Viewer newViewerB = new Viewer("Kaneda", "kaneda@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));
		Viewer newViewerC = new Viewer("Tetsuo", "tetsuo@neo-tokio.exp", "Neo-Tokio", LocalDate.parse("1990-11-26"));

		restTemplate.postForEntity(path + "/viewers", newViewerA, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerB, Void.class);
		restTemplate.postForEntity(path + "/viewers", newViewerC, Void.class);
	}
 */
	/* @Test
    void shouldReturnAViewerWhenDataIsSaved() throws Exception {
        this.mvc.perform(get(path + "/viewers/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.name").value("Tetsuo"));
    }
 */
	/* @WithMockUser(username = "Kay", authorities = {"SCOPE_viewers:read"})
    @Test
    void shouldReturnForbiddenWhenCardBelongsToSomeoneElse() throws Exception {
        this.mvc.perform(get(path + "/viewers/3"))
                .andExpect(status().isForbidden());
    } */

	/* @Test
	void shouldNotReturnAViewerWithAnUnknownId() {
		ResponseEntity<String> response = restTemplate
		.withBasicAuth("Master", "mas123")
		.getForEntity(path + "/viewers/1000",
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	} */


/* 	@Test
	@DirtiesContext
	void shouldCreateANewViewer() {
		Viewer newViewer = new Viewer("Yamagata", "yamagata@neo-tokio.exp", "Neo-Tokio",
				LocalDate.parse("1990-11-26"));
		ResponseEntity<Void> createResponse = restTemplate
		//.withBasicAuth("Master", "mas123")
		.postForEntity(path + "/viewers",
				newViewer, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewViewer = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewViewer, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		String name = documentContext.read("$.name");

		assertThat(id).isNotNull();
		assertThat(name).isEqualTo("Yamagata");
	} */

/* 	@Test
	void shouldReturnAllViewersWhenListIsRequested() {
		ResponseEntity<String> response = restTemplate.getForEntity(path + "/viewers", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int viewerCount = documentContext.read("$.length()");
		assertThat(viewerCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..name");
		assertThat(ids).containsExactlyInAnyOrder("Kay", "Tetsuo", "Kaneda");

		JSONArray amounts = documentContext.read("$..city");
		assertThat(amounts).containsExactlyInAnyOrder("Neo-Tokio", "Neo-Tokio", "Neo-Tokio");
	} */

/* 	@Test
	void shouldReturnAPageOfViewers() {

		ResponseEntity<String> response = restTemplate.getForEntity(path + "/viewers?page=0&size=1&sort=id,asc",
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	} */

	// security xtras
/* 	@Test
	void shouldNotReturnAViewerWhenUsingBadCredentials() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth("BAD-USER", "abc123")
				.getForEntity("/viewers/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

		response = restTemplate
				.withBasicAuth("kay", "BAD-PASSWORD")
				.getForEntity("/viewers/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	} */

/* 
	@Test
	void shouldRejectUsersWhoAreNotCardOwners() {
		ResponseEntity<String> response = restTemplate
		.withBasicAuth("Ryu No Assessments", "qrs456")
		.getForEntity("/viewers/1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	} */
}
