package jp.eunika.dorenisuru.common.util;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProfile {
	@Autowired
	private Environment environment;

	public boolean isDevelopment() {
		return profileIs("development");
	}

	public boolean isTest() {
		return profileIs("test");
	}

	public boolean isProduction() {
		return profileIs("production");
	}

	private boolean profileIs(String name) {
		return Arrays.asList(environment.getActiveProfiles()).contains(name);
	}
}
