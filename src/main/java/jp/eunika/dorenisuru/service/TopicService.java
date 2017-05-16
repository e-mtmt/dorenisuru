package jp.eunika.dorenisuru.service;

import java.time.LocalDateTime;
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
import jp.eunika.dorenisuru.domain.repository.ChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.TopicRepository;
import jp.eunika.dorenisuru.domain.repository.VoterChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.VoterRepository;
import jp.eunika.dorenisuru.web.form.TopicForm;

@Service
@Transactional
public class TopicService {
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private ChoiceRepository choiceRepository;
	@Autowired
	private VoterRepository voterRepository;
	@Autowired
	private VoterChoiceRepository voterChoiceRepository;

	public Topic findOne(String hash) {
		Topic topic = topicRepository.findByHash(hash);
		if (topic == null) throw new EntityNotFoundException("トピックが存在しません [hash: " + hash + "]");
		return topic;
	}

	public Topic create(TopicForm topicForm) throws Exception {
		Topic newTopic = BeanUtil.copy(topicForm, Topic.class);
		List<Choice> choices = Stream.of(topicForm.getChoiceText().split("\n"))
				.map(text -> Choice.of(text.trim(), newTopic))
				.collect(Collectors.toList());
		newTopic.setChoices(choices);
		return topicRepository.save(newTopic);
	}

	public Topic update(Topic topic) {
		topic.setUpdatedAt(LocalDateTime.now());
		return topicRepository.save(topic);
	}

	public Topic deleteByHash(String hash) {
		Topic topic = topicRepository.findByHash(hash);
		if (topic == null) throw new EntityNotFoundException("トピックが存在しません [hash: " + hash + "]");
		topicRepository.delete(topic);
		return topic;
	}
}
