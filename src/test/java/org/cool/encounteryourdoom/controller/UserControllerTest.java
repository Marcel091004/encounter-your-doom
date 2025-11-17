package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {



    @MockitoBean
    UserService userService;

    @Nested
    class User {

        @Test
        void shouldReturn200kWithNewUserId() {

            UUID userId = UUID.randomUUID();

            when(userService.getUniqueUserID()).thenReturn(userId);
            doNothing().when(userService).addUser(any(UUID.class));

            UserController userController = new UserController(userService);

            ResponseEntity<UUID> response = userController.generateUserId();
            ResponseEntity<UUID> responseWeExpect = ResponseEntity.ok(userId);

            assertEquals(response, responseWeExpect);

        }
    }
}
