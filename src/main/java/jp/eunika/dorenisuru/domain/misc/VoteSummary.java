package jp.eunika.dorenisuru.domain.misc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.eunika.dorenisuru.common.util.Tuple2;
import jp.eunika.dorenisuru.domain.entity.Choice;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import jp.eunika.dorenisuru.domain.entity.VoterChoice.Feeling;;

public class VoteSummary {
	private Map<Integer, Tuple2<List<Feeling>, Integer>> summaryByChoice;

	public static VoteSummary of(Topic topic) {
		return new VoteSummary(topic);
	}

	private VoteSummary(Topic topic) {
		summaryByChoice = topic.getChoices().stream().collect(Collectors.toMap(Choice::getId, choice -> {
			List<Feeling> feelings = topic.getVoters()
					.stream()
					.map(
							voter -> choice.getVoterChoices()
									.stream()
									.filter(voterChoice -> voterChoice.getChoice().equals(choice) && voterChoice.getVoter().equals(voter))
									.findFirst()
									.map(VoterChoice::getFeeling)
									.orElse(Feeling.Unknown))
					.collect(Collectors.toList());

			Integer percentage = new BigDecimal(
					feelings.stream().collect(Collectors.averagingInt(Feeling::getPercentage)).doubleValue())
							.setScale(0, BigDecimal.ROUND_HALF_UP)
							.intValue();

			return Tuple2.of(feelings, percentage);
		}));
	}

	public List<Feeling> getFeelings(Integer choiceId) {
		return summaryByChoice.get(choiceId).getValue1();
	}

	public int getPercentage(Integer choiceId) {
		return summaryByChoice.get(choiceId).getValue2();
	}

	public double getRatio(Integer choiceId) {
		return getPercentage(choiceId) / 100.0;
	}
}
