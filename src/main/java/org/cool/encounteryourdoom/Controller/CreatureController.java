package org.cool.encounteryourdoom.Controller;

import org.openapitools.api.CreatureApi;
import org.openapitools.model.GetCreature200Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class CreatureController implements CreatureApi {

    @Override
    public ResponseEntity<GetCreature200Response> getCreature(String region) {
        GetCreature200Response response = new GetCreature200Response();

        if (region != null && !region.isEmpty()) {
            response.setMessage("Creature from region: " + region);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok(response);
    }
}
