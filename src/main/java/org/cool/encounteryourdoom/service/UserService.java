package org.cool.encounteryourdoom.service;

import org.cool.encounteryourdoom.repository.UserRepository;
import org.cool.encounteryourdoom.model.PrivateEncounterEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UUID getUniqueUserID() {
        boolean isUnique = false;
        UUID response;

        do {
            response = UUID.randomUUID();
            if (this.userRepository.findAllByUserId(response).isEmpty()) {
                isUnique = true;
            }
        } while (!isUnique);

        return  response;
    }

    public void addUser(UUID userId) {
        var emptyUser = new PrivateEncounterEntity();

        emptyUser.setUserId(userId);
        emptyUser.setId(UUID.randomUUID());
        this.userRepository.save(emptyUser);
    }
}
