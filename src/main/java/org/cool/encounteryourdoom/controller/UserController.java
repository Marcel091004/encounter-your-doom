package org.cool.encounteryourdoom.controller;

import org.cool.encounteryourdoom.service.UserService;
import org.openapitools.api.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/datev/v1")
public class UserController implements UserApi {

	private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Override
	public ResponseEntity<UUID> generateUserId() {

	    UUID response = userService.getUniqueUserID();
        userService.addUser(response);

		return ResponseEntity.ok(response);
	}


}
