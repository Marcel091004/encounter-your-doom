package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.MongoDBTestContainer;
import org.cool.encounteryourdoom.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ImportTestcontainers(MongoDBTestContainer.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGenerateUserId() throws Exception {
       MvcResult result = mockMvc.perform(post("/user/generation"))
                .andExpect(status().isOk()).andReturn();

       String body = result.getResponse().getContentAsString();
       body = body.replace("\"", ""); // "" machen den UUID String kaputt
       UUID userId = UUID.fromString(body);

       Assertions.assertNotNull(userId);
       Assertions.assertTrue(userRepository.findById(userId).isPresent());

    }
}
