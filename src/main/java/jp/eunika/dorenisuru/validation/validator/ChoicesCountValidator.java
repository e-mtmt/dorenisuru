package jp.eunika.dorenisuru.validation.validator;

import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.eunika.dorenisuru.validation.annotation.ChoicesCount;
import jp.eunika.dorenisuru.web.form.TopicForm;

public class ChoicesCountValidator implements ConstraintValidator<ChoicesCount, TopicForm> {
	@Autowired
	private HttpServletRequest request;

	private int min;
	private int max;

	@Override
	public void initialize(ChoicesCount annotation) {
		min = annotation.min();
		max = annotation.max();
	}

	@Override
	public boolean isValid(TopicForm topicForm, ConstraintValidatorContext context) {
		Objects.requireNonNull(topicForm);
		String[] deleteChoiceIdsParams = request.getParameterValues("_deleteChoiceIds");
		String choiceText = topicForm.getChoiceText();
		long choiceCount = Stream.of(choiceText.split("\n")).filter(text -> !text.trim().isEmpty()).count();
		// トピック作成
		if (deleteChoiceIdsParams == null) {
			if (choiceCount >= min && choiceCount <= max) return true;
		}
		// トピック編集
		else {
			long totalChoiceCount = (deleteChoiceIdsParams.length - topicForm.getDeleteChoiceIds().size() + choiceCount);
			if (totalChoiceCount >= min && totalChoiceCount <= max) return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode("choiceText")
				.addConstraintViolation();
		return false;
	}
}
