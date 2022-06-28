package io.github.nicolaikopka.backend_questionsdbproject.users;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MyUserRepo extends MongoRepository<MyUser, String> {
    Optional<MyUser> findByUsername(String username);
}
