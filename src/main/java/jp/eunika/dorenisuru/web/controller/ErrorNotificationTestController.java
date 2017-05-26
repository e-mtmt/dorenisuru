package jp.eunika.dorenisuru.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error-notification-test")
public class ErrorNotificationTestController {
	@SuppressWarnings("unused")
	@GetMapping
	public String testErrorNotification() {
		if (true) throw new RuntimeException("テスト障害");
		return null;
	}
}
