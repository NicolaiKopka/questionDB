package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;

import io.github.nicolaikopka.backend_questionsdbproject.users.LoginResponse;
import io.github.nicolaikopka.backend_questionsdbproject.users.LoginUser;
import io.github.nicolaikopka.backend_questionsdbproject.users.MyUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionDBControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCallAddQuestionMethodAndGetQuestionsAllAndById() {
        Question question1 = new Question("question1", "answer1");
        Question question2 = new Question("question2", "answer2");
        Question question3 = new Question("question3", "answer3");

        //setUser and Register
        MyUser testUser = new MyUser();
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");

        ResponseEntity<Void> addUserResponse = restTemplate.postForEntity("/api/user", testUser, Void.class);
        Assertions.assertThat(addUserResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Login User
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("testUser");
        loginUser.setPassword("testPassword");

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity("/api/login", loginUser, LoginResponse.class);
        Assertions.assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(loginResponse.getBody().getJwtToken()).isNotBlank();

        //Create Auth Header
        String token = loginResponse.getBody().getJwtToken();

        //Test Post
        // TODO add body
        ResponseEntity<Question> addResponse = restTemplate.exchange("/api/questions",
                HttpMethod.POST,
                new HttpEntity<>(question1, createHeader(token)),
                Question.class);
        Assertions.assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<Question> addResponse2 = restTemplate.exchange("/api/questions",
                HttpMethod.POST,
                new HttpEntity<>(question2, createHeader(token)),
                Question.class);
        Assertions.assertThat(addResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<Question> addResponse3 = restTemplate.exchange("/api/questions",
                HttpMethod.POST,
                new HttpEntity<>(question3, createHeader(token)),
                Question.class);
        Assertions.assertThat(addResponse3.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Test Get All
        ResponseEntity<Question[]> getAllResponse = restTemplate.exchange("/api/questions",
                HttpMethod.GET,
                new HttpEntity<>(createHeader(token)),
                Question[].class);
        Assertions.assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllResponse.getBody()[1].getQuestion()).isEqualTo("question2");
        Assertions.assertThat(getAllResponse.getBody().length).isEqualTo(3);

        // Test Put
        Question editedQuestion = getAllResponse.getBody()[1];
        editedQuestion.setQuestion("editedQuestion");
        editedQuestion.setAnswer("editedAnswer");
        ResponseEntity<Void> getPutResponse = restTemplate.exchange("/api/questions",
                HttpMethod.PUT,
                new HttpEntity<>(editedQuestion, createHeader(token)),
                Void.class);
        Assertions.assertThat(getPutResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Test GetById and Resolve Put
        ResponseEntity<Question> getByIdResponse = restTemplate.exchange(
                "/api/questions/" + editedQuestion.getId(),
                HttpMethod.GET,
                new HttpEntity<>(createHeader(token)),
                Question.class);
        Assertions.assertThat(getByIdResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getByIdResponse.getBody().getQuestion()).isEqualTo("editedQuestion");
        Assertions.assertThat(getByIdResponse.getBody().getAnswer()).isEqualTo("editedAnswer");

        //Test Delete
        ResponseEntity<Question> getDeleteResponse = restTemplate.exchange(
                "/api/questions/" + addResponse.getBody().getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(createHeader(token)),
                Question.class);
        Assertions.assertThat(getDeleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<Question> getDeleteResponse2 = restTemplate.exchange(
                "/api/questions/" + addResponse2.getBody().getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(createHeader(token)),
                Question.class);
        Assertions.assertThat(getDeleteResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Question[]> getAllResponseAfterDelete = restTemplate.exchange(
                "/api/questions",
                HttpMethod.GET,
                new HttpEntity<>(createHeader(token)),
                Question[].class);

        Assertions.assertThat(getAllResponseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllResponseAfterDelete.getBody().length).isEqualTo(1);
        Assertions.assertThat(getAllResponseAfterDelete.getBody()[0].getQuestion()).isEqualTo("question3");

    }

    private HttpHeaders createHeader(String token) {
        String headerValue = "Bearer " + token;
        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", headerValue);
        return header;
    }
}