package jp.eunika.dorenisuru.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.eunika.dorenisuru.validation.validator.MultiLineTextValidator;

@Documented
@Constraint(validatedBy = MultiLineTextValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MultiLineText {
	String message() default "[error]";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int min() default 0;

	int max() default Integer.MAX_VALUE;

	int lineMin() default 0;

	int lineMax() default Integer.MAX_VALUE;
}
