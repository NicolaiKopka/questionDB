package io.github.nicolaikopka.backend_questionsdbproject.users;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;
    private final PasswordEncoder encoder;

    @PostMapping
    public void addNewUser(@RequestBody MyUser myUser) {
        String hashedPW = encoder.encode(myUser.getPassword());
        myUser.setPassword(hashedPW);
        myUserService.addNewUser(myUser);
    }

    @GetMapping("/{username}")
    public Optional<MyUser> findUserByUsername(@PathVariable String username) {
        return myUserService.findByName(username);
    }

    @GetMapping("/me")
    public MyUser getLoggedInUser(Principal principal) {
        String username = principal.getName();

        return myUserService.findByName(username).orElseThrow();
    }
}
