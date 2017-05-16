package jp.eunika.dorenisuru;

import static jp.eunika.dorenisuru.common.util.CollectionsUtil.*;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import jp.eunika.dorenisuru.common.util.ThymeleafViewHelper;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	private static final Map<String, Object> viewHelpers = $map($("h", new ThymeleafViewHelper()));

	@Autowired
	private MessageSource messageSource;

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource);
		return localValidatorFactoryBean;
	}

	@Bean
	public IExpressionObjectDialect customThymeleafDialect() {
		return new IExpressionObjectDialect() {
			@Override
			public String getName() {
				return "customThymeleafDialect";
			}

			@Override
			public IExpressionObjectFactory getExpressionObjectFactory() {
				return new IExpressionObjectFactory() {
					@Override
					public Set<String> getAllExpressionObjectNames() {
						return viewHelpers.keySet();
					}

					@Override
					public Object buildObject(IExpressionContext context, String objectName) {
						return viewHelpers.get(objectName);
					}

					@Override
					public boolean isCacheable(String objectName) {
						return false;
					}
				};
			}
		};
	}
}
