package com.cd.onlinetest.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CreateTestRequest {

	@Size(min = 3, max = 20)
	private String name;

	@NotBlank
	@Size(max = 10)
	private String duration;

	private Set<String> questionIds;

}
