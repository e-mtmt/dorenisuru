package jp.eunika.dorenisuru.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TopicForm {
	@NotNull
	@Size(min = 1, max = 50, message = "{validation.topic.title}")
	private String title;

	@NotNull
	@Size(min = 1, max = 1000, message = "{validation.topic.contents}")
	private String contents;

	@NotNull
	@Size(min = 1, max = 2000, message = "{validation.topic.choiceText}")
	private String choiceText;
}
