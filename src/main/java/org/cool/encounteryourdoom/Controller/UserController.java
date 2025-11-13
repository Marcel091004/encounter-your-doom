package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.UserRepository;
import org.cool.encounteryourdoom.Service.UserService;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.openapitools.api.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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
