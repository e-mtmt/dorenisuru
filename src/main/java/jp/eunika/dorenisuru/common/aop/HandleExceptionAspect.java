package jp.eunika.dorenisuru.common.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class HandleExceptionAspect {
	@Around("execution(* org.springframework.web.servlet.HandlerExceptionResolver.resolveException(..))")
	public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
		Object ret = joinPoint.proceed();
		HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
		if (request.getAttribute("ERROR_LOGGED") == null) {
			Object handler = joinPoint.getArgs()[2];
			Exception exception = (Exception) joinPoint.getArgs()[3];
			log.error(
					String.format("Detected Error!! [%1s %2s :: %3s]", request.getMethod(), request.getRequestURI(), handler),
					exception);
			// TODO 障害通知

			// mark as logged
			request.setAttribute("ERROR_LOGGED", true);
		}
		return ret;
	}
}
