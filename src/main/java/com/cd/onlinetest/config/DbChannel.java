package com.cd.onlinetest.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.mongoDomain.Question;
import com.cd.onlinetest.repository.QuestionRepository;

@Repository
public class DbChannel {

	@Autowired
	QuestionRepository questionRepository;

	public List<Question> getQuestion(String topic, Integer numQues, DifficultyLevel difficultylevel) {
		
		switch (difficultylevel) {
		case ALL:
			return questionRepository.findByIdLike(topic);
		default:
			return questionRepository.findByIdStartingWithAndLevel(topic, difficultylevel);
		}
	}

	public void saveQuestions(List<Question> questions) {
		questionRepository.saveAll(questions);
	}

	public void deleteQuestion(String topic) {
		questionRepository.deleteAll();
	}

}
