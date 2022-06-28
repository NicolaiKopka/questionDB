package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionDBControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCallAddQuestionMethodAndGetQuestionsAllAndById() {
        Question question1 = new Question("question1", "answer1");
        Question question2 = new Question("question2", "answer2");
        Question question3 = new Question("question3", "answer3");

        //Test Post
        ResponseEntity<Question> addResponse = restTemplate.postForEntity("/questions", question1, Question.class);
        Assertions.assertThat(addResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<Question> addResponse2 = restTemplate.postForEntity("/questions", question2, Question.class);
        Assertions.assertThat(addResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        ResponseEntity<Question> addResponse3 = restTemplate.postForEntity("/questions", question3, Question.class);
        Assertions.assertThat(addResponse3.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Test Get All
        ResponseEntity<Question[]> getAllResponse = restTemplate.getForEntity("/questions", Question[].class);
        Assertions.assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllResponse.getBody()[1].getQuestion()).isEqualTo("question2");
        Assertions.assertThat(getAllResponse.getBody().length).isEqualTo(3);

        // Test Put
        Question editedQuestion = getAllResponse.getBody()[1];
        editedQuestion.setQuestion("editedQuestion");
        editedQuestion.setAnswer("editedAnswer");
        restTemplate.put("/questions", editedQuestion);

        //Test GetById and Resolve Put
        ResponseEntity<Question> editedById = restTemplate.getForEntity("/questions/" + editedQuestion.getId(), Question.class);
        Assertions.assertThat(editedById.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(editedById.getBody().getQuestion()).isEqualTo("editedQuestion");
        Assertions.assertThat(editedById.getBody().getAnswer()).isEqualTo("editedAnswer");

        //Test Delete
        restTemplate.delete("/questions/" + addResponse.getBody().getId());
        restTemplate.delete("/questions/" + addResponse2.getBody().getId());
        ResponseEntity<Question[]> getAllResponseAfterDelete = restTemplate.getForEntity("/questions", Question[].class);
        Assertions.assertThat(getAllResponseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(getAllResponseAfterDelete.getBody().length).isEqualTo(1);
        Assertions.assertThat(getAllResponseAfterDelete.getBody()[0].getQuestion()).isEqualTo("question3");

    }
}