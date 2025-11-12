package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.Controller.UserController;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApiTest {

	@MockitoBean
	UserController UserController;

	@Autowired
	private MockMvc mockMvc;

	@Nested
	class userGeneration {
		@Test
		void shouldReturn201AndLocationHeaderWhenUserIsGenerated() throws Exception {
			mockMvc.perform(post("/datev/v1/user/generation"))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"));
		}

		@Test
		void shouldReturn405WhenUsingGetInsteadOfPost() throws Exception {
			mockMvc.perform(get("/datev/v1/user/generation"))
				.andExpect(status().isMethodNotAllowed());
		}
	}
}
