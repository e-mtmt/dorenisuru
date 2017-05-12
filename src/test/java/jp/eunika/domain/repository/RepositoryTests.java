package jp.eunika.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import jp.eunika.dorenisuru.Application;
import jp.eunika.dorenisuru.domain.entity.Choice;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.entity.Voter;
import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import jp.eunika.dorenisuru.domain.repository.ChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.TopicRepository;
import jp.eunika.dorenisuru.domain.repository.VoterChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.VoterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@Transactional
public class RepositoryTests {
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private ChoiceRepository choiceRepository;
	@Autowired
	private VoterRepository voterRepository;
	@Autowired
	private VoterChoiceRepository voterChoiceRepository;

	@Before
	public void setUp() {
		topicRepository.deleteAll();
		choiceRepository.deleteAll();
		voterRepository.deleteAll();
		voterChoiceRepository.deleteAll();
	}

	@Test
	public void createAndFind() {
		Topic newTopic = Topic.of("テストイベント", "イベント内容");
		List<String> choiceTexts = Arrays.asList("選択肢１", "選択肢２", "選択肢３");
		List<String> voterNames = Arrays.asList("横尾", "鈴木", "大江", "芝山", "松本");

		newTopic.setChoices(choiceTexts.stream().map(text -> Choice.of(text, newTopic)).collect(Collectors.toList()));
		newTopic.setVoters(voterNames.stream().map(name -> Voter.of(name, newTopic)).collect(Collectors.toList()));
		newTopic.getChoices().forEach(choice -> {
			choice.setVoterChoices(
					newTopic.getVoters()
							.stream()
							.map(voter -> VoterChoice.of(VoterChoice.Feeling.OK, "コメント", voter, choice))
							.collect(Collectors.toList()));
		});
		topicRepository.save(newTopic);

		Topic queryTopic = topicRepository.findOne(newTopic.getId());
		List<Choice> queryChoices = queryTopic.getChoices();
		List<Voter> queryVoters = queryTopic.getVoters();
		assertThat(queryTopic).isNotNull().isEqualToIgnoringGivenFields(newTopic, "choices", "voters");
		assertThat(queryChoices.size()).isEqualTo(3);
		assertThat(queryVoters.size()).isEqualTo(5);
		assertThat(queryChoices.stream().map(Choice::getText).toArray()).containsOnlyElementsOf(choiceTexts);
		assertThat(queryVoters.stream().map(Voter::getName).toArray()).containsOnlyElementsOf(voterNames);
		assertThat(topicRepository.count()).isEqualTo(1);
		assertThat(choiceRepository.count()).isEqualTo(3);
		assertThat(voterRepository.count()).isEqualTo(5);
		assertThat(voterChoiceRepository.count()).isEqualTo(15);
	}

	@Test
	public void findAndDeleteTopicByHash() {
		Topic newTopic = Topic.of("テストイベント", "イベント内容");
		topicRepository.save(newTopic);

		Topic queryTopic = topicRepository.findByHash(newTopic.getHash());
		assertThat(queryTopic).isNotNull().isEqualToIgnoringGivenFields(newTopic, "choices", "voters");

		topicRepository.deleteByHash(newTopic.getHash());
		assertThat(topicRepository.count()).isEqualTo(0);
	}
}
