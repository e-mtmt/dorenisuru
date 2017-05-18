package jp.eunika.dorenisuru.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorsController {
	@RequestMapping(value = "/404", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFound() {
		return "error-404";
	}
}
