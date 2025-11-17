package org.cool.encounteryourdoom.exceptions;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(BindException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> String.format(
						"%s: %s (rejected value: %s)",
						fieldError.getField(),
						fieldError.getDefaultMessage(),
						fieldError.getRejectedValue()
				))
				.collect(Collectors.toList());
		Map<String, Object> body = new HashMap<>();
		body.put("message", "Validation failed");
		body.put("errors", errors);
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NestedRuntimeException.class)
	public ResponseEntity<Map<String, Object>> handleJsonParseException(NestedRuntimeException ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", "JSON parse error");
		ex.getMostSpecificCause();
		body.put("details", ex.getMostSpecificCause().getMessage());
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}


}
