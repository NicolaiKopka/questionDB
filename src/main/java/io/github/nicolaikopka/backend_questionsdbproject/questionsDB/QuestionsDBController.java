package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionsDBController {

    private final QuestionsDBService questionsDBService;

    @GetMapping()
    public Collection<Question> getAllApprovedQuestions() {
        return questionsDBService.getAllQuestions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Question> getQuestionById(@PathVariable String id) {
            return ResponseEntity.of(questionsDBService.getQuestionById(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Question addQuestion(@RequestBody Question question) {
        return questionsDBService.addQuestion(question);
    }

    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable String id) {
        questionsDBService.deleteById(id);
    }

    @PutMapping()
    public void editQuestion(@RequestBody Question question) {
        questionsDBService.editQuestion(question);

    }
}
