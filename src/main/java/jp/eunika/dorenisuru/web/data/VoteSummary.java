package jp.eunika.dorenisuru.web.data;

import java.util.List;
import java.util.Map;

import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import lombok.Data;

@Data
public class VoteSummary {
	private Map<Integer, List<VoterChoice.Feeling>> feelingsByChoice;

	public List<VoterChoice.Feeling> getFeelings(Integer choiceId) {
		return feelingsByChoice.get(choiceId);
	}
}
