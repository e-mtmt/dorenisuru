package jp.eunika.dorenisuru.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.eunika.dorenisuru.validation.annotation.ChoicesCount;
import jp.eunika.dorenisuru.web.form.TopicForm;

public class ChoicesCountValidator implements ConstraintValidator<ChoicesCount, TopicForm> {
	@Override
	public void initialize(ChoicesCount annotation) {}

	@Override
	public boolean isValid(TopicForm topicForm, ConstraintValidatorContext context) {
		if (topicForm == null) return true;
		else if (!topicForm.getChoiceText().isEmpty() || topicForm.getDeleteChoiceIds().isEmpty()) return true;
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode("choiceText")
				.addConstraintViolation();
		return false;
	}
}
