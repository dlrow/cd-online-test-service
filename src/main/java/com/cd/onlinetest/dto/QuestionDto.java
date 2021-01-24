package com.cd.onlinetest.dto;

import java.util.List;
import java.util.Map;

import com.cd.onlinetest.enums.Opt;

import lombok.Data;

@Data
public class QuestionDto {

	public String id;

	private String question;

	private Map<Opt, String> options;

	private List<String> imageUrls;

	private String topic;

	Opt correctAns;

}
