package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class QuestionsDBServiceTest {

    @Test
    void shouldReturnUnmodifiableCollectionOfQuestions() {
        Question question1 = new Question("q1", "a1");
        Question question2 = new Question("q1", "a1");

        QuestionsDBRepository questionsDBRepository = Mockito.mock(QuestionsDBRepository.class);

        Mockito.when(questionsDBRepository.findAll()).thenReturn(List.of(question1, question2));

        QuestionsDBService questionsDBService = new QuestionsDBService(questionsDBRepository);

        Assertions.assertThat(questionsDBService.getAllQuestions()).isUnmodifiable()
                .contains(question1, question2);
    }

}