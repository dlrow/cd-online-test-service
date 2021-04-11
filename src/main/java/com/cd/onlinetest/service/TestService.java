package com.cd.onlinetest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cd.onlinetest.dto.TestDto;
import com.cd.onlinetest.mongoDomain.Test;
import com.cd.onlinetest.payload.response.MessageResponse;
import com.cd.onlinetest.repository.TestRepository;
import com.cd.onlinetest.request.CreateTestRequest;

@Service
public class TestService {

	@Autowired
	TestRepository testRepository;

	//TODO return tests associated with the signed in user 
	public List<TestDto> getAllTests() {
		List<Test> dbTests = testRepository.findAll();
		return dbTestToDto(dbTests);
	}
	
	public List<TestDto> getTestById() {
		List<Test> dbTests = testRepository.findAll();
		return dbTestToDto(dbTests);
	}

	// TODO : match if questionId's exists in db
	public MessageResponse createTest(CreateTestRequest createTestRequest) {
		Test dbtest = new Test();
		dbtest.setQuestionIds(createTestRequest.getQuestionIds());
		dbtest.setName(createTestRequest.getName());
		dbtest.setDuration(createTestRequest.getDuration());
		testRepository.save(dbtest);
		return new MessageResponse(HttpStatus.OK, "test created successfully");
	}
	
	private List<TestDto> dbTestToDto(List<Test> dbTests) {
		List<TestDto> testDtos = new ArrayList<>();
		dbTests.forEach(dbTest -> {
			TestDto tdto = new TestDto(dbTest.getId(), dbTest.getName(), dbTest.getDuration());
			testDtos.add(tdto);
		});
		return testDtos;
	}

}
