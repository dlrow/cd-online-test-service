package com.cd.onlinetest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.mongoDomain.Question;
import com.cd.onlinetest.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/ques")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@CrossOrigin
	@RequestMapping(value = "/v1/getQuestion", method = RequestMethod.GET)
	public ResponseEntity<List<Question>> getQuestion(@RequestParam(required =false) String topic,
			@RequestParam(required = true) Integer numQues, @RequestParam DifficultyLevel difficultylevel) {
		log.info("getQuestion subject method called :");
		return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestion(topic, numQues, difficultylevel));
	}

	@RequestMapping(value = "/v1/updateQuestion", method = RequestMethod.POST)
	public ResponseEntity<String> updateQuestion(@RequestParam String pathOfExcel) {
		log.info("updateQuestion subject method called :");
		questionService.updateQuestion(pathOfExcel);
		return ResponseEntity.status(200).body("updated Successfully");
	}
	
	@RequestMapping(value = "/v1/deleteQuestion", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteQuestion(@RequestParam String topic) {
		log.info("deleteQuestion subject method called :");
		questionService.deleteQuestion(topic);
		return ResponseEntity.status(200).body("delete Successfully");
	}

}
