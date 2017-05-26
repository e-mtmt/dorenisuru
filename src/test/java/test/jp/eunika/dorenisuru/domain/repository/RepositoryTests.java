package test.jp.eunika.dorenisuru.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.eunika.dorenisuru.Application;
import jp.eunika.dorenisuru.common.util.AppProperties;
import jp.eunika.dorenisuru.domain.entity.Choice;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.entity.Voter;
import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import jp.eunika.dorenisuru.domain.entity.VoterChoice.Feeling;
import jp.eunika.dorenisuru.domain.repository.ChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.TopicRepository;
import jp.eunika.dorenisuru.domain.repository.VoterChoiceRepository;
import jp.eunika.dorenisuru.domain.repository.VoterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class RepositoryTests {
	@Autowired
	private AppProperties appProperties;
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
		newTopic.setVoters(voterNames.stream().map(name -> Voter.of(name, "コメント", newTopic)).collect(Collectors.toList()));
		newTopic.getChoices().forEach(choice -> {
			choice.setVoterChoices(
					newTopic.getVoters().stream().map(voter -> VoterChoice.of(Feeling.OK, voter, choice)).collect(
							Collectors.toList()));
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
	public void findAndDeleteTopic() {
		Topic newTopic = Topic.of("テストイベント", "イベント内容");
		topicRepository.save(newTopic);

		Topic queryTopic = topicRepository.findByHash(newTopic.getHash());
		assertThat(queryTopic).isNotNull().isEqualToIgnoringGivenFields(newTopic, "choices", "voters");

		topicRepository.delete(newTopic);
		assertThat(topicRepository.count()).isEqualTo(0);
	}

	@Test
	public void findExpiredTopics() {
		LocalDateTime now = LocalDateTime.now();
		int effectiveDays = appProperties.getEffectiveDaysOfTopics();
		Topic topic1 = Topic.of("テストトピック１", "内容１");
		Topic topic2 = Topic.of("テストトピック２", "内容２");
		Topic topic3 = Topic.of("テストトピック３", "内容３");
		topic1.setLastAccessedAt(now.minusDays(effectiveDays + 1));
		topic2.setLastAccessedAt(now.minusDays(effectiveDays));
		topic3.setLastAccessedAt(now.minusDays(effectiveDays - 1));
		topicRepository.save(Arrays.asList(topic1, topic2, topic3));
		List<Topic> expiredTopics = topicRepository.findByLastAccessedAtLessThan(now.minusDays(effectiveDays));
		assertThat(expiredTopics.size()).isEqualTo(1);
	}
}
