package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "questions")
@Data
public class Question {

    @Id
    private String id;
    private String question;
    private String answer;
    private boolean approved;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
