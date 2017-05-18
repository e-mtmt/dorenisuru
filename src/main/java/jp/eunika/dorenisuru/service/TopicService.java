package jp.eunika.dorenisuru.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.eunika.dorenisuru.common.util.BeanUtil;
import jp.eunika.dorenisuru.domain.entity.Choice;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.entity.Voter;
import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import jp.eunika.dorenisuru.domain.repository.TopicRepository;
import jp.eunika.dorenisuru.web.form.TopicForm;
import jp.eunika.dorenisuru.web.form.VoteForm;

@Service
@Transactional
public class TopicService {
	@Autowired
	private TopicRepository topicRepository;

	public Topic findOne(String hash) {
		Topic topic = topicRepository.findByHash(hash);
		if (topic == null) throw new EntityNotFoundException("トピックが存在しません [hash: " + hash + "]");
		return topic;
	}

	public Topic create(TopicForm topicForm) {
		Topic newTopic = BeanUtil.copy(topicForm, Topic.class);
		List<Choice> choices = buildChoicesByText(newTopic, topicForm.getChoiceText());
		newTopic.setChoices(choices);
		return topicRepository.save(newTopic);
	}

	public Topic update(TopicForm topicForm, String hash) {
		Topic topic = topicRepository.findByHash(hash);
		BeanUtil.copy(topicForm, topic);
		if (!topicForm.getDeleteChoiceIds().isEmpty()) {
			List<Choice> deleteChoices = topic.getChoices()
					.stream()
					.filter(choice -> topicForm.getDeleteChoiceIds().contains(choice.getId()))
					.collect(Collectors.toList());
			topic.getChoices().removeAll(deleteChoices);
		}
		List<Choice> addChoices = buildChoicesByText(topic, topicForm.getChoiceText());
		topic.getChoices().addAll(addChoices);
		return topicRepository.save(topic);
	}

	public Topic delete(String hash) {
		Topic topic = this.findOne(hash);
		topicRepository.delete(topic);
		return topic;
	}

	public void addVote(String topicHash, VoteForm voteForm) {
		Topic topic = this.findOne(topicHash);
		Voter voter = Voter.of(voteForm.getVoterName(), voteForm.getVoteComment(), topic);
		topic.getVoters().add(voter);
		List<VoterChoice> voterChoices = voteForm.getChoiceFeelings()
				.entrySet()
				.stream()
				.map(entry -> VoterChoice.of(entry.getValue(), voter, topic.getChoice(entry.getKey())))
				.collect(Collectors.toList());
		voter.setVoterChoices(voterChoices);
		topicRepository.save(topic);
	}

	private List<Choice> buildChoicesByText(Topic topic, String choiceText) {
		if (choiceText == null || choiceText.isEmpty()) return Collections.emptyList();
		return Stream.of(choiceText.split("\n")).map(text -> Choice.of(text.trim(), topic)).collect(Collectors.toList());
	}
}
