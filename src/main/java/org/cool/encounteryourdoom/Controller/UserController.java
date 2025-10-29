package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.UserApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    @Override
    public ResponseEntity<UUID> generateUserId() {
        //TODO this is not yet implemented
        UUID response = UUID.randomUUID();
        return ResponseEntity.ok(response);
    }


}
