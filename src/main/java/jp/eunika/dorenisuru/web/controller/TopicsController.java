package jp.eunika.dorenisuru.web.controller;

import javax.validation.groups.Default;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eunika.dorenisuru.common.constants.AppConstants;
import jp.eunika.dorenisuru.common.util.Tuple2;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.misc.VoteSummary;
import jp.eunika.dorenisuru.service.TopicService;
import jp.eunika.dorenisuru.web.form.TopicForm;
import jp.eunika.dorenisuru.web.form.VoteForm;

@Controller
@RequestMapping("/topics")
public class TopicsController extends ApplicationController {
	@Autowired
	private TopicService topicService;

	@ModelAttribute
	public TopicForm setUpTopicForm() {
		return new TopicForm();
	}

	@ModelAttribute
	public VoteForm setUpVoteForm() {
		return new VoteForm();
	}

	@GetMapping("{topicHash}")
	public String show(@PathVariable String topicHash, Model viewData) {
		Tuple2<Topic, VoteSummary> result = topicService.prepareShowableTopicData(topicHash);
		viewData.addAttribute(result.getValue1()).addAttribute(result.getValue2());
		return "topics/show";
	}

	@GetMapping("add")
	public String add() {
		return "topics/add";
	}

	@GetMapping("{topicHash}/edit")
	public String edit(@PathVariable String topicHash, TopicForm topicForm, Model viewData) {
		Topic topic = topicService.prepareEditableTopicData(topicHash, topicForm);
		viewData.addAttribute(topic);
		return "topics/edit";
	}

	@PostMapping
	public String create(
			@Validated({ Default.class, TopicForm.Create.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData) {
		if (result.hasErrors()) return add();
		Topic createdTopic = topicService.createTopic(topicForm);
		return "redirect:/topics/" + createdTopic.getHash();
	}

	@PatchMapping("{topicHash}")
	public String update(
			@PathVariable String topicHash,
			@Validated({ Default.class, TopicForm.Update.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData,
			RedirectAttributes redirectAttr) {
		if (result.hasErrors()) {
			Topic topic = topicService.findTopic(topicHash);
			viewData.addAttribute(topic);
			return "topics/edit";
		}
		topicService.updateTopic(topicForm, topicHash);
		redirectAttr.addFlashAttribute(AppConstants.NoticeMessage.Info, "トピックの内容を更新しました");
		return "redirect:/topics/" + topicHash;
	}

	@DeleteMapping("{topicHash}")
	public String delete(@PathVariable String topicHash, RedirectAttributes redirectAttr) {
		Topic deletedTopic = topicService.deleteTopic(topicHash);
		redirectAttr.addFlashAttribute(AppConstants.NoticeMessage.Info, "トピック「" + deletedTopic.getTitle() + "」を削除しました");
		return "redirect:/";
	}
}
