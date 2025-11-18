package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.cool.encounteryourdoom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Nested
	class GetUniqueUserID {
		@Test
		void returnsUniqueUUIDWhenFirstIsUnique() {
			when(userRepository.findAllByUserId(any(UUID.class))).thenReturn(Collections.emptyList());

			UUID result = userService.getUniqueUserID();
			assertNotNull(result);
			verify(userRepository, atLeastOnce()).findAllByUserId(result);
		}

		@Test
		void generatesNewUUIDIfFirstIsNotUnique() {
			UUID first = UUID.randomUUID();
			UUID second = UUID.randomUUID();
			when(userRepository.findAllByUserId(first)).thenReturn(List.of(new PrivateEncounterEntity()));
			when(userRepository.findAllByUserId(second)).thenReturn(Collections.emptyList());
			when(userRepository.findAllByUserId(any(UUID.class)))
					.thenReturn(List.of(new PrivateEncounterEntity()))
					.thenReturn(Collections.emptyList());

			UUID result = userService.getUniqueUserID();
			assertNotNull(result);
			verify(userRepository, atLeast(2)).findAllByUserId(any(UUID.class));
		}
	}

	@Nested
	class AddUser {
		@Test
		void addsUserSuccessfully() {
			UUID userId = UUID.randomUUID();

			userService.addUser(userId);

			verify(userRepository, times(1)).save(argThat(entity ->
					entity.getUserId().equals(userId) && entity.getId() != null
			));
		}
	}
}
