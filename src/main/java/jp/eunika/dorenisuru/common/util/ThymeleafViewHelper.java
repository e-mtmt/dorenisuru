package jp.eunika.dorenisuru.common.util;

public class ThymeleafViewHelper {
	public String br(String text) {
		return text.replaceAll("\r\n|\r|\n", "<br/>");
	}
}
