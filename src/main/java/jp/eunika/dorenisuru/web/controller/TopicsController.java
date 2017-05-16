package jp.eunika.dorenisuru.web.controller;

import javax.persistence.EntityNotFoundException;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eunika.dorenisuru.common.constants.AppConstants;
import jp.eunika.dorenisuru.common.util.BeanUtil;
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

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEntityNotFoundException() {
		return "error-404";
	}

	@GetMapping("{hash}")
	public String show(@PathVariable String hash, Model viewData) {
		Topic topic = topicService.findOne(hash);
		viewData.addAttribute(topic);
		return "topics/show";
	}

	@GetMapping("add")
	public String add() {
		return "topics/add";
	}

	@PostMapping
	public String create(
			@Validated({ Default.class, TopicForm.Create.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData) {
		if (result.hasErrors()) return add();
		Topic newTopic = topicService.create(topicForm);
		return "redirect:/topics/" + newTopic.getHash();
	}

	@GetMapping("{hash}/edit")
	public String edit(@PathVariable String hash, TopicForm topicForm, Model viewData) {
		Topic topic = topicService.findOne(hash);
		BeanUtil.copy(topic, topicForm);
		viewData.addAttribute(topic);
		return "topics/edit";
	}

	@PatchMapping("{hash}")
	public String update(
			@PathVariable String hash,
			@Validated({ Default.class, TopicForm.Update.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData,
			RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			Topic topic = topicService.findOne(hash);
			viewData.addAttribute(topic);
			return "topics/edit";
		}
		Topic updatedTopic = topicService.update(topicForm, hash);
		viewData.addAttribute(updatedTopic);
		redirectAttr.addFlashAttribute(AppConstants.NoticeMessage.Info, "トピックの内容を更新しました");
		return "redirect:/topics/" + hash;
	}

	@DeleteMapping("{hash}")
	public String delete(@PathVariable String hash, RedirectAttributes redirectAttr) {
		Topic deletedTopic = topicService.delete(hash);
		redirectAttr.addFlashAttribute(AppConstants.NoticeMessage.Info, "トピック「" + deletedTopic.getTitle() + "」を削除しました");
		return "redirect:/";
	}
}
