package com.epam.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = BookNotFoundException.class)
	public ResponseEntity<ExceptionResponce> handleQuestionException(BookNotFoundException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getMassage());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponce> handleFeignException(Exception exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        //exceptionResponse.setError(exception.getMassage());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
}
