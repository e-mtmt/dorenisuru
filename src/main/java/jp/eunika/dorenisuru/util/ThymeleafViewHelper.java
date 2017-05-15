package jp.eunika.dorenisuru.util;

public class ThymeleafViewHelper {
	public String br(String text) {
		return text.replaceAll("\r\n|\r|\n", "<br/>");
	}
}
