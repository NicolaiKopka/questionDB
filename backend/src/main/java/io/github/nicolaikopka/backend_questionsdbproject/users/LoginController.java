package io.github.nicolaikopka.backend_questionsdbproject.users;

import io.github.nicolaikopka.backend_questionsdbproject.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginUser loginUser) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
            return ResponseEntity.ok(new LoginResponse(jwtService.createToken(new HashMap<>(), loginUser.getUsername())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}
