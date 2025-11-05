package org.cool.encounteryourdoom.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	//TODO is a dumb example so i remember the syntax but we actually need to implement proper exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Void> handleAllExceptions(Exception ex) {
		System.err.println("An error occurred: " + ex.getMessage());
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> String.format("%s: %s (rejected value: %s)", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
				.collect(Collectors.toList());
		Map<String, Object> body = new HashMap<>();
		body.put("message", "Validation failed");
		body.put("errors", errors);
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleJsonParseException(HttpMessageNotReadableException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", "JSON parse error");
		body.put("details", ex.getMostSpecificCause().getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

}
