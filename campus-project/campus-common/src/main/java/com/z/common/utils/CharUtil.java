package com.z.common.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class CharUtil {
	public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	public static String getRandomString(Integer num) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getRandomNum(Integer num) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static boolean isEmail(final CharSequence input) {
		return isMatch(REGEX_EMAIL, input);
	}

	public static boolean isMatch(final String regex, final CharSequence input) {
		return input != null && input.length() > 0 && Pattern.matches(regex, input);
	}
}
