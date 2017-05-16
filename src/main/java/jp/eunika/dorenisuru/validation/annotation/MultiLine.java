package jp.eunika.dorenisuru.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.eunika.dorenisuru.validation.validator.MultiLineValidator;

@Documented
@Constraint(validatedBy = MultiLineValidator.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface MultiLine {
	String message() default "{validation.MultiLine}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int min() default 0;

	int max() default Integer.MAX_VALUE;
}
