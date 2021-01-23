package com.cd.onlinetest.payload.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class MessageResponse {

	private HttpStatus status;
	private String message;

	public MessageResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

}
