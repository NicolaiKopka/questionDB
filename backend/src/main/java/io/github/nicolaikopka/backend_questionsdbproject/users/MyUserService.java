package io.github.nicolaikopka.backend_questionsdbproject.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepo myUserRepo;


    public void addNewUser(MyUser myUser) {
        myUserRepo.save(myUser);
    }

    public Optional<MyUser> findByName(String username) {
        return myUserRepo.findByUsername(username);
    }
}
