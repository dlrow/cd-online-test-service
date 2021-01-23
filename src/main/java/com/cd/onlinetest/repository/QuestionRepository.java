package com.cd.onlinetest.repository;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.mongoDomain.Question;

@Configuration
public interface QuestionRepository extends MongoRepository<Question, String> {

	List<Question> findByIdStartingWithAndLevel(String topic, DifficultyLevel difficultylevel);

	List<Question> findByIdLike(String topic);
}