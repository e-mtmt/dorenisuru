package jp.eunika.dorenisuru.web.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eunika.dorenisuru.common.constants.AppConstants;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.service.TopicService;
import jp.eunika.dorenisuru.web.form.VoteForm;

@Controller
@RequestMapping("/topics/{topicHash}/votes")
public class VotesController {
	@Autowired
	private TopicService topicService;

	@ModelAttribute
	public VoteForm setUpVoteForm() {
		return new VoteForm();
	}

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleEntityNotFoundException() {
		return "error-404";
	}

	@PostMapping
	public String create(
			@PathVariable String topicHash,
			@Validated VoteForm voteForm,
			BindingResult result,
			Model viewData,
			RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			viewData.addAttribute("invalidVote", true);
			return showTopic(topicHash, viewData);
		}
		topicService.addVote(topicHash, voteForm);
		redirectAttr.addFlashAttribute(AppConstants.NoticeMessage.Info, "トピックに回答しました");
		return "redirect:/topics/" + topicHash;
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

	private String showTopic(String hash, Model viewData) {
		Topic topic = topicService.findOne(hash);
		viewData.addAttribute(topic);
		return "topics/show";
	}
}
