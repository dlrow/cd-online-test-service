package com.cd.onlinetest.mongoDomain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cd.onlinetest.enums.DifficultyLevel;
import com.cd.onlinetest.enums.Opt;

import lombok.Data;

@Data
@Document(collection = "questions")
public class Question implements Serializable {
	
	private static final long serialVersionUID = 1L;

	  @Id
	  public String id;
	  
	  private String question;
	  
	  DifficultyLevel level;
	  
	  private Map<Opt,String> options;
	  
	  private List<String> imageUrls;
	  
	  private String topic;
	  
	  Opt correctAns;
	  
}
