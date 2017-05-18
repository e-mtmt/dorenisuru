package jp.eunika.dorenisuru.validation.validator;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import jp.eunika.dorenisuru.validation.annotation.ChoicesCount;
import jp.eunika.dorenisuru.web.form.TopicForm;

public class ChoicesCountValidator implements ConstraintValidator<ChoicesCount, TopicForm> {
	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(ChoicesCount annotation) {}

	@Override
	public boolean isValid(TopicForm topicForm, ConstraintValidatorContext context) {
		Objects.requireNonNull(topicForm);
		if (topicForm.getDeleteChoiceIds().size() < request.getParameterValues("_deleteChoiceIds").length
				|| !topicForm.getChoiceText().isEmpty()) {
			return true;
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode("choiceText")
				.addConstraintViolation();
		return false;
	}
}
