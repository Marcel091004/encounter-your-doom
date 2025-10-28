package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.UserApi;
import org.openapitools.model.GenerateUserId200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements UserApi {

    @Override
    public ResponseEntity<GenerateUserId200Response> generateUserId() {
        //TODO this is not yet implemented
        GenerateUserId200Response response = new GenerateUserId200Response();
        response.setUserId(UUID.randomUUID());
        return ResponseEntity.ok(response);
    }


}
