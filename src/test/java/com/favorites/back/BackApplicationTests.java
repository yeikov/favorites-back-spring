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

import com.favorites.back.entities.user.User;
import com.favorites.back.entities.user.UserRepository;

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
// @WithMockUser
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
	private UserRepository users;

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindRecent() {
		User user = new User("Orlando", "Orlando@Orlando.exp");

		entityManager.persist(user);

		Optional<User> findUser = users.findByeMail(user.geteMail());

		// assertThat(findByLastName).extracting(User::getName).containsOnly(user.getName());

		// assertThat(findUser).extrac (user.getName())

	}

	@Test
	void shouldReturnAUserWhenDataIsSaved_3() throws Exception {
		this.mockMvc.perform(post(this.path + "/users/", new User("Juana", "Chicago")))
				.andExpect(status().isOk());
		/*
		 * this.mockMvc.perform(get(this.path + "/users/1"))
		 * .andExpect(status().isOk())
		 * .andExpect(jsonPath("$.id").value(1))
		 * .andExpect(jsonPath("$.name").value("Juana"));
		 */
	}

	@Test
	void shouldReturnAUser() throws Exception {

		// assertThat(1).isEqualTo(1);
		this.mockMvc.perform(get(this.path + "/users/1"))
				.andExpect(status().isOk());
		/*
		 * .andExpect(jsonPath("$.id").value(1))
		 * .andExpect(jsonPath("$.name").value("Juana"));
		 */
	}

	/**
	 * @Test
	 *       void shouldCreateANewCashCard() throws Exception {
	 *       String location = this.mvc.perform(post("/users")
	 *       .with(csrf())
	 *       ...
	 */

}
