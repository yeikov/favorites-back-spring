package com.favorites.back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.viewer.ViewerRepository;

import org.springframework.boot.test.context.TestConfiguration;
import static org.hamcrest.Matchers.hasItem;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

//@TestConfiguration(proxyBeanMethods = false)
//@Testcontainers
//@SpringBootTest

@DataJpaTest
@AutoConfigureMockMvc
// @WithMockViewer
class BackApplicationTests {

	private String path = BackApplication.backEndUrl;

	// @Container
	// @ServiceConnection
	// static MySQLContainer<?> mySQLContainer = new
	// MySQLContainer<>(DockerImageName.parse("mysql:latest"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ViewerRepository viewers;

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindRecent() {
		Viewer viewer = new Viewer("Orlando", "Orlando@Orlando.exp");

		entityManager.persist(viewer);

		Optional<Viewer> findViewer = viewers.findByeMail(viewer.geteMail());

		// assertThat(findByLastName).extracting(Viewer::getName).containsOnly(viewer.getName());

		// assertThat(findViewer).extrac (viewer.getName())

	}

	@Test
	void shouldReturnAViewerWhenDataIsSaved_3() throws Exception {
		this.mockMvc.perform(post(this.path + "/viewers/", new Viewer("Juana", "Chicago")))
				.andExpect(status().isOk());
		/*
		 * this.mockMvc.perform(get(this.path + "/viewers/1"))
		 * .andExpect(status().isOk())
		 * .andExpect(jsonPath("$.id").value(1))
		 * .andExpect(jsonPath("$.name").value("Juana"));
		 */
	}

	@Test
	void shouldReturnAViewer() throws Exception {

		// assertThat(1).isEqualTo(1);
		this.mockMvc.perform(get(this.path + "/viewers/1"))
				.andExpect(status().isOk());
		/*
		 * .andExpect(jsonPath("$.id").value(1))
		 * .andExpect(jsonPath("$.name").value("Juana"));
		 */
	}

	/**
	 * @Test
	 *       void shouldCreateANewCashCard() throws Exception {
	 *       String location = this.mvc.perform(post("/viewers")
	 *       .with(csrf())
	 *       ...
	 */

}
