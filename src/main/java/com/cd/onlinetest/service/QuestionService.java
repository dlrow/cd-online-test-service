package com.cd.onlinetest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cd.onlinetest.config.DbChannel;
import com.cd.onlinetest.dto.QuestionDto;
import com.cd.onlinetest.dto.TestDto;
import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.mongoDomain.Question;
import com.cd.onlinetest.mongoDomain.Test;
import com.cd.onlinetest.util.ExcelReaderUtil;
import com.cd.onlinetest.util.GoogleSheetReader;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService {

	@Autowired
	DbChannel dbChannel;

	@Autowired
	GoogleSheetReader excelReaderUtil;
	
	@Autowired
	TestService testService;

	public List<Question> getQuestion(String topic, Integer numQues, DifficultyLevel difficultylevel) {
		List<Question> questions = dbChannel.getQuestion(topic, numQues, difficultylevel);
		Collections.shuffle(questions);
		List<Question> questionList = questions.subList(0, Math.min(numQues, questions.size()));
		return questionList;
	}

	// TODO : update question in chunks
	public void updateQuestion(String path, String range) {
		List<Question> questions = null;
		try {
			questions = excelReaderUtil.extractQuestionFromExcel(path, range);
		} catch (Exception e) {
			log.error("unable to read questions from excel", e);
		}
		dbChannel.saveQuestions(questions);
	}

	public void deleteQuestion(String topic) {
		dbChannel.deleteQuestion(topic);
	}

	public List<QuestionDto> getQuestionByTopicId(String topicId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<QuestionDto> getQuestionByTestId(String testId) {
		//testService.getTest
		return null;
	}
	
	private List<QuestionDto> dbQuestionToDto(List<Question> dbQuestion) {
		List<QuestionDto> questionDtos = new ArrayList<>();
		dbQuestion.forEach(dbQues -> {
			QuestionDto qdto = new QuestionDto();
			qdto.setId(dbQues.getId());
			qdto.setQuestion(dbQues.getQuestion());
			qdto.setCorrectAns(dbQues.getCorrectAns());
			qdto.setOptions(dbQues.getOptions());
			questionDtos.add(qdto);
		});
		return questionDtos;
	}

}
