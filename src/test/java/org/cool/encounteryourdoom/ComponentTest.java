package org.cool.encounteryourdoom;

import org.cool.encounteryourdoom.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
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
public class ComponentTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class User {

        @Test
        void callUserGenerationShouldReturnValidUserId() throws Exception {
            MvcResult result = mockMvc.perform(post("/datev/v1/user/generation"))
                    .andExpect(status().isCreated()).andReturn();

            String locationHeader = result.getResponse().getHeader("Location");

            Assertions.assertNotNull(locationHeader);
            locationHeader = locationHeader.replace("\"", "");
            locationHeader = locationHeader.replace("/datev/v1/users/", "");

            UUID userId = UUID.fromString(locationHeader);

            Assertions.assertNotNull(userId);
            Assertions.assertFalse(userRepository.findAllByUserId(userId).isEmpty());

        }
    }
}
