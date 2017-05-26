package jp.eunika.dorenisuru.common.aop;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bugsnag.Bugsnag;

import jp.eunika.dorenisuru.common.util.AppProfile;
import jp.eunika.dorenisuru.common.util.AppProperties;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class HandleExceptionAspect {
	@Autowired
	private AppProfile appProfile;
	@Autowired
	private AppProperties appProperties;

	@Around("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))")
	public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = joinPoint.proceed();
		HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
		if (request.getAttribute("ERROR_LOGGED") == null) {
			Object handler = joinPoint.getArgs()[2];
			Exception exception = (Exception) joinPoint.getArgs()[3];
			log.error(
					String.format(
							"<<<< Error Occurred >>>> [%1s %2s :: %3s]",
							request.getMethod(),
							request.getRequestURI(),
							handler),
					exception);
			request.setAttribute("ERROR_LOGGED", true);

			if (appProfile.isProduction()) {
				if (!EntityNotFoundException.class.isInstance(exception)) {
					new Bugsnag(appProperties.getBugsnagApiKey()).notify(exception);
				}
			}
		}
		return returnValue;
	}
}
