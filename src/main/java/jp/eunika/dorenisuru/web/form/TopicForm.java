package jp.eunika.dorenisuru.web.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jp.eunika.dorenisuru.validation.annotation.MultiLineText;
import lombok.Data;

@Data
public class TopicForm {
	@NotNull
	@Size(min = 1, max = 50, message = "{validation.topic.title}")
	private String title;

	@NotNull
	@Size(min = 0, max = 1000, message = "{validation.topic.contents}")
	private String contents;

	@NotNull
	@MultiLineText(min = 1, max = 1000, lineMin = 1, lineMax = 30, message = "{validation.topic.choiceText}")
	private String choiceText;
}
