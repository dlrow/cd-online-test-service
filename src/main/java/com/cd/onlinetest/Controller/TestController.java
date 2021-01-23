package com.cd.onlinetest.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.onlinetest.enums.CDConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/test")
public class TestController {

	@GetMapping("/all")
	@PreAuthorize(CDConstants.STUDENT_ROLE)
	public String getAllTests() {
		return "Public Content.";
	}
	
	@GetMapping("/allpub")
	public String pub() {
		return "Public Content.";
	}

	@GetMapping("/all/{id}")
	@PreAuthorize(CDConstants.ANY_ROLE)
	public String getTestById() {
		return "Public Content.";
	}

}
