package jp.eunika.dorenisuru.domain.misc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jp.eunika.dorenisuru.domain.entity.Choice;
import jp.eunika.dorenisuru.domain.entity.Topic;
import jp.eunika.dorenisuru.domain.entity.VoterChoice;

public class VoteSummary {
	private final Map<Integer, List<VoterChoice.Feeling>> feelingsByChoice;
	private final Map<Integer, Integer> percentageByChoice;

	public static VoteSummary of(Topic topic) {
		return new VoteSummary(topic);
	}

	private VoteSummary(Topic topic) {
		feelingsByChoice = topic.getChoices().stream().collect(
				Collectors.toMap(Choice::getId, choice -> topic.getVoters().stream().map(voter -> {
					return choice.getVoterChoices()
							.stream()
							.filter(voterChoice -> voterChoice.getChoice().equals(choice) && voterChoice.getVoter().equals(voter))
							.findFirst()
							.map(VoterChoice::getFeeling)
							.orElse(VoterChoice.Feeling.Unknown);
				}).collect(Collectors.toList())));
		percentageByChoice = feelingsByChoice.entrySet().stream().collect(
				Collectors.toMap(
						Entry::getKey,
						entry -> new BigDecimal(
								entry.getValue()
										.stream()
										.collect(Collectors.averagingInt(VoterChoice.Feeling::getPercentage))
										.doubleValue()).setScale(0, BigDecimal.ROUND_HALF_UP).intValue()));
	}

	public List<VoterChoice.Feeling> getFeelings(Integer choiceId) {
		return feelingsByChoice.get(choiceId);
	}

	public int getPercentage(Integer choiceId) {
		return percentageByChoice.get(choiceId);
	}

	public double getRatio(Integer choiceId) {
		return percentageByChoice.get(choiceId) / 100.0;
	}
}
