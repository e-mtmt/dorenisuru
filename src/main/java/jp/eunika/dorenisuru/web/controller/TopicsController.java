package jp.eunika.dorenisuru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.service.TopicService;
import jp.eunika.dorenisuru.web.form.TopicForm;

@Controller
@RequestMapping("/topics")
public class TopicsController {
	@Autowired
	private TopicService topicService;

	@ModelAttribute
	public TopicForm setUpForm() {
		return new TopicForm();
	}

	@GetMapping("{hash}")
	public String show(@PathVariable String hash) {
		return "topics/show";
	}

	@GetMapping("add")
	public String add() {
		return "topics/add";
	}

	@PostMapping
	public String create(@Validated TopicForm form, BindingResult result, Model viewData) throws Exception {
		if (result.hasErrors()) return add();
		Topic newTopic = topicService.create(form);
		return "redirect:/topics/" + newTopic.getHash();
	}

	@GetMapping("{hash}/edit")
	public String edit(@PathVariable String hash) {
		// TODO
		return "redirect:/";
	}

	@PatchMapping("{hash}")
	public String update(@PathVariable String hash) {
		// TODO
		return "redirect:/";
	}

	@DeleteMapping("{hash}")
	public String delete(@PathVariable String hash) {
		// TODO
		return "redirect:/";
	}
}
