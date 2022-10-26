package com.epam.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import feign.FeignException;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(value = LibraryException.class)
	public ResponseEntity<ExceptionResponce> handleQuestionException(LibraryException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getMassage());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = NumberFormatException.class)
	public ResponseEntity<ExceptionResponce> handleNuberFormat(NumberFormatException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError("check input variable");
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = FeignException.class)
	public ResponseEntity<ExceptionResponce> handleFeignException(FeignException exception,WebRequest request) {
		 ExceptionResponce exceptionResponse = new ExceptionResponce();
	        exceptionResponse.setError(exception.getMessage());
	        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
	        exceptionResponse.setTimeStamp(LocalDate.now().toString());
	        exceptionResponse.setPath(request.getDescription(false));
	        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
		
	}
}
