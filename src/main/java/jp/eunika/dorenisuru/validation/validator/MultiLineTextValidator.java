package jp.eunika.dorenisuru.validation.validator;

import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.eunika.dorenisuru.validation.annotation.MultiLineText;

public class MultiLineTextValidator implements ConstraintValidator<MultiLineText, String> {
	private int min;
	private int max;
	private int lineMin;
	private int lineMax;

	@Override
	public void initialize(MultiLineText annotation) {
		min = annotation.min();
		max = annotation.max();
		lineMin = annotation.lineMin();
		lineMax = annotation.lineMax();
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt) {
		if (input == null) return true;
		else if (min == 0 && input.isEmpty()) return true;
		else if (input.length() < min || input.length() > max) return false;
		return Stream.of(input.split("\n")).map(String::trim).allMatch(t -> t.length() >= lineMin && t.length() <= lineMax);
	}
}
