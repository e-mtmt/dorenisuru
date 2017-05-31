package jp.eunika.dorenisuru.web.form;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import jp.eunika.dorenisuru.validation.annotation.ChoicesCount;
import jp.eunika.dorenisuru.validation.annotation.MultiLine;
import lombok.Data;

@Data
@ChoicesCount(min = 1, max = 20)
public class TopicForm {
	public interface Create {}

	public interface Update {}

	@NotBlank
	@Size(max = 50, message = "{validation.Size.max}")
	private String title;

	@Size(max = 1000, message = "{validation.Size.max}")
	private String contents;

	@NotBlank(groups = Create.class)
	@Size(max = 1000, message = "{validation.Size.max}")
	@MultiLine(min = 1, max = 30)
	private String choiceText;

	private List<Integer> deleteChoiceIds;
}
