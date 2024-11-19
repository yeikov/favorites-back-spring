package com.favorites.back;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WithMockUser
class BackApplicationTests {

	private String path = BackApplication.backEndUrl;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnAUserWhenDataIsSaved() throws Exception {
		this.mockMvc.perform(get("http://localhost:9001/favorites/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Juana"));
	}
 
	@Test
	void shouldReturnAUserWhenDataIsSaved_1_() throws Exception {
		this.mockMvc.perform(get("localhost:9001/favorites/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Juana"));
	} 
	@Test
	void shouldReturnAUserWhenDataIsSaved_2() throws Exception {
		this.mockMvc.perform(get("favorites/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Juana"));
	}
	@Test
	void shouldReturnAUserWhenDataIsSaved_3() throws Exception {
		this.mockMvc.perform(get(this.path + "/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Juana"));
	}

	/**
	 * @Test
	 * void shouldCreateANewCashCard() throws Exception {
  		String location = this.mvc.perform(post("/users")
      	.with(csrf())
		...
	 */

}
