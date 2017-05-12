package jp.eunika.dorenisuru.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.repository.ChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.TopicRepository;
import jp.eunika.dorenisuru.domain.repository.VoterChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.VoterRepository;

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
		return topicRepository.findByHash(hash);
	}

	public Topic create(Topic topic) {
		return topicRepository.save(topic);
	}

	public Topic update(Topic topic) {
		topic.setUpdatedAt(LocalDateTime.now());
		return topicRepository.save(topic);
	}

	public void delete(String hash) {
		topicRepository.deleteByHash(hash);
	}
}
