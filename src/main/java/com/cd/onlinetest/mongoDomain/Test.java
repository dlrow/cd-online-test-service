package com.cd.onlinetest.mongoDomain;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "test")
public class Test implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	public String id;

	private String name;

	private String duration;

	private Set<String> questionIds;

	private String createdBy;

}
