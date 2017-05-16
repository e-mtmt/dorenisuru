package jp.eunika.dorenisuru.validation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import jp.eunika.dorenisuru.validation.validator.ChoicesCountValidator;

@Documented
@Constraint(validatedBy = ChoicesCountValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ChoicesCount {
	String message() default "{validation.ChoicesCount}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
