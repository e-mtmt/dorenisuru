package jp.eunika.dorenisuru.web.form;

import java.util.Map;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import jp.eunika.dorenisuru.domain.entity.VoterChoice;
import lombok.Data;

@Data
public class VoteForm {
	@NotBlank
	@Size(max = 20, message = "{validation.Size.max}")
	private String voterName;

	@Size(max = 50, message = "{validation.Size.max}")
	private String voteComment;

	@NotEmpty
	private Map<Integer, VoterChoice.Feeling> choiceFeelings;
}
