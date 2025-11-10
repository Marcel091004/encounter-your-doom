package org.cool.encounteryourdoom.Controller;

import org.cool.encounteryourdoom.Repository.UserRepository;
import org.cool.encounteryourdoom.model.privateEncounter;
import org.openapitools.api.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UUID> generateUserId() {

        boolean isUnique = false;
        UUID response;

        do {
             response = UUID.randomUUID();
            if(this.userRepository.findAllByUserId(response).isEmpty()) {
                isUnique = true;
            }
        }while (!isUnique);

        privateEncounter emptyUser = new privateEncounter();
        emptyUser.setUserId(response);
        this.userRepository.save(emptyUser);

    return ResponseEntity.ok(response);
    }


}
