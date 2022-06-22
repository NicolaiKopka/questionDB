package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionsDBService {

    private final QuestionsDBRepository questionsDBRepository;
    public Collection<Question> getAllQuestions(){
        return Collections.unmodifiableCollection(questionsDBRepository.findAll());
    }

    public Optional<Question> getQuestionById(String id) {
        return questionsDBRepository.findById(id);
    }

    public Question addQuestion(Question question) {
        return questionsDBRepository.save(question);
    }

    public void deleteById(String id) {
        questionsDBRepository.deleteById(id);
    }

    public void editQuestion(Question question) {
        questionsDBRepository.save(question);
    }
}
