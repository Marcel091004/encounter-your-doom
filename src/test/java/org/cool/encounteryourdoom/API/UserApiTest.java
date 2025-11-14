package org.cool.encounteryourdoom.API;

import org.cool.encounteryourdoom.controller.UserController;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		void shouldReturn200OkWithNewUserID() throws Exception {

            UUID userId = UUID.fromString("cc40b7c9-bf6e-412a-944d-4593f75e7501");

            when(UserController.generateUserId()).thenReturn(ResponseEntity.ok(userId));

			 mockMvc.perform(post("/datev/v1/user/generation"))
				.andExpect(status().isOk())
                     .andExpect(content().string("\"cc40b7c9-bf6e-412a-944d-4593f75e7501\""));


		}

		@Test
		void shouldReturn405WhenUsingGetInsteadOfPost() throws Exception {
			mockMvc.perform(get("/datev/v1/user/generation"))
				.andExpect(status().isMethodNotAllowed());
		}
	}
}
