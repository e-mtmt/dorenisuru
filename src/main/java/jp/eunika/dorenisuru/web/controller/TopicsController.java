package jp.eunika.dorenisuru.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.eunika.dorenisuru.common.constants.AppConstants.NoticeMessage;
import jp.eunika.dorenisuru.common.util.EncryptionUtil;
import jp.eunika.dorenisuru.common.util.Tuple2;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.misc.VoteSummary;
import jp.eunika.dorenisuru.service.TopicService;
import jp.eunika.dorenisuru.web.form.TopicForm;
import jp.eunika.dorenisuru.web.form.VoteForm;

@Controller
@RequestMapping("/topics")
public class TopicsController extends ApplicationController {
	private static final String cookieTopicOwnerKey = "topic-owner-key";

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
	public String show(
			@PathVariable String topicHash,
			Model viewData,
			@CookieValue(value = cookieTopicOwnerKey, defaultValue = "") String topicOwnerKey) {
		Tuple2<Topic, VoteSummary> result = topicService.prepareShowableTopicData(topicHash);
		viewData.addAttribute(result.getValue1()).addAttribute(result.getValue2());
		if (isTopicOwner(topicHash, topicOwnerKey)) viewData.addAttribute("topicOwner", true);
		return "topics/show";
	}

	@GetMapping("add")
	public String add() {
		return "topics/add";
	}

	@GetMapping("{topicHash}/edit")
	public String edit(
			@PathVariable String topicHash,
			TopicForm topicForm,
			Model viewData,
			@CookieValue(value = cookieTopicOwnerKey, defaultValue = "") String topicOwnerKey) {
		requireTopicOwner(topicHash, topicOwnerKey);
		Topic topic = topicService.prepareEditableTopicData(topicHash, topicForm);
		viewData.addAttribute(topic);
		return "topics/edit";
	}

	@PostMapping
	public String create(
			@Validated({ Default.class, TopicForm.Create.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData,
			HttpServletResponse response) {
		if (result.hasErrors()) return add();
		Topic createdTopic = topicService.createTopic(topicForm);
		/* トピックオーナーの鍵をクッキーに埋め込む */ {
			Cookie cookie = new Cookie(cookieTopicOwnerKey, EncryptionUtil.encryptText(createdTopic.getHash()));
			cookie.setPath("/topics/" + createdTopic.getHash());
			cookie.setMaxAge(Integer.MAX_VALUE);
			response.addCookie(cookie);
		}
		return "redirect:/topics/" + createdTopic.getHash();
	}

	@PatchMapping("{topicHash}")
	public String update(
			@PathVariable String topicHash,
			@Validated({ Default.class, TopicForm.Update.class }) TopicForm topicForm,
			BindingResult result,
			Model viewData,
			RedirectAttributes redirectAttr,
			@CookieValue(value = cookieTopicOwnerKey, defaultValue = "") String topicOwnerKey) {
		requireTopicOwner(topicHash, topicOwnerKey);
		if (result.hasErrors()) {
			Topic topic = topicService.findTopic(topicHash);
			viewData.addAttribute(topic);
			return "topics/edit";
		}
		topicService.updateTopic(topicForm, topicHash);
		redirectAttr.addFlashAttribute(NoticeMessage.Info, "トピックの内容を更新しました");
		return "redirect:/topics/" + topicHash;
	}

	@DeleteMapping("{topicHash}")
	public String delete(
			@PathVariable String topicHash,
			RedirectAttributes redirectAttr,
			@CookieValue(value = cookieTopicOwnerKey, defaultValue = "") String topicOwnerKey) {
		requireTopicOwner(topicHash, topicOwnerKey);
		Topic deletedTopic = topicService.deleteTopic(topicHash);
		redirectAttr.addFlashAttribute(NoticeMessage.Info, "トピック「" + deletedTopic.getTitle() + "」を削除しました");
		return "redirect:/";
	}

	private boolean isTopicOwner(String topicHash, String topicOwnerKey) {
		return !topicOwnerKey.isEmpty() && topicOwnerKey.equals(EncryptionUtil.encryptText(topicHash));
	}

	private void requireTopicOwner(String topicHash, String topicOwnerKey) {
		if (!isTopicOwner(topicHash, topicOwnerKey)) throw new SecurityException("オーナー権限がありません [hash: " + topicHash + "]");
	}
}
