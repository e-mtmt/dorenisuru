package jp.eunika.dorenisuru.web.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ApplicationController {
	@RequestMapping(value = "/403")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(SecurityException.class)
	public String handleSecurityException() {
		return "error-403";
	}

	@RequestMapping(value = "/404")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public String handleEntityNotFoundException() {
		return "error-404";
	}
}
