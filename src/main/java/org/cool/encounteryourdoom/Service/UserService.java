package org.cool.encounteryourdoom.Service;

import org.cool.encounteryourdoom.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //TODO : Implement UserService

    private final UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
