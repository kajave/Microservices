package com.epam.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExceptionResponce> handleQuestionException(UserNotFoundException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getMassage());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
}
