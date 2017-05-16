package jp.eunika.dorenisuru.validation.validator;

import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.eunika.dorenisuru.validation.annotation.MultiLine;

public class MultiLineValidator implements ConstraintValidator<MultiLine, String> {
	private int min;
	private int max;

	@Override
	public void initialize(MultiLine annotation) {
		min = annotation.min();
		max = annotation.max();
	}

	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt) {
		if (input == null || input.isEmpty()) return true;
		return Stream.of(input.split("\n")).map(String::trim).allMatch(t -> t.length() >= min && t.length() <= max);
	}
}
