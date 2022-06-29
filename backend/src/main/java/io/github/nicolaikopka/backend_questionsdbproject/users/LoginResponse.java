package io.github.nicolaikopka.backend_questionsdbproject.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String jwtToken;

}
