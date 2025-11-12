package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.UserRepository;
import org.cool.encounteryourdoom.model.privateEncounter;
import org.openapitools.api.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/datev/v1")
public class UserController implements UserApi {

	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public ResponseEntity<Void> generateUserId() {

		boolean isUnique = false;
		UUID response;
		UUID MongoID;

		do {
			MongoID = UUID.randomUUID();
			response = UUID.randomUUID();
			if (this.userRepository.findAllByUserId(response).isEmpty() && this.userRepository.findById(MongoID).isEmpty()) {
				isUnique = true;
			}
		} while (!isUnique);

		privateEncounter emptyUser = new privateEncounter();

		emptyUser.setUserId(response);
		emptyUser.setId(MongoID);
		this.userRepository.save(emptyUser);

		URI location = URI.create(String.format("/datev/v1/users/%s", response));
		return ResponseEntity.created(location).build();
	}


}
