package com.youllknow.game.utils;

public class GwtUtils {
	public static String format(String format, String... strings) {
		String value = format;
		for (int i = 0;i < strings.length;i++) {
			value = value.replaceFirst("%%s", strings[i]);
		}
		return value;
	}
}
