package jp.eunika.dorenisuru.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TopController extends ApplicationController {
	@GetMapping
	public String index() {
		return "index";
	}
}
