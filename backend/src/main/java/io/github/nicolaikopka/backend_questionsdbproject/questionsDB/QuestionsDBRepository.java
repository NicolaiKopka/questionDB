package io.github.nicolaikopka.backend_questionsdbproject.questionsDB;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsDBRepository extends MongoRepository<Question, String> {

    List<Question> findAllByApproved(boolean approvalStatus);
}
