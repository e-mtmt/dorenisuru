package jp.eunika.dorenisuru.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topics/{topicHash}/votes")
public class VotesController {
	@PostMapping
	public String create(@PathVariable String topicHash) {
		// TODO
		return "redirect:/";
	}

	@PatchMapping("{id}")
	public String update(@PathVariable String topicHash, @PathVariable String id) {
		// TODO
		return "redirect:/";
	}

	@DeleteMapping("{id}")
	public String delete(@PathVariable String topicHash, @PathVariable String id) {
		// TODO
		return "redirect:/";
	}
}
