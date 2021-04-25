package com.moon.moon_commons.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 对字符串中的特殊标签包含的内容进行查找和处理，比如${xxxxx},可以对xxxxx进行查找和替换
 * @author 浩
 *
 */
public class TokenParser {

	private final String openToken;
	private final String closeToken;

	public TokenParser(String openToken, String closeToken) {
		this.openToken = openToken;
		this.closeToken = closeToken;
	}
	
	public List<String> getTokens(String text) {
		return getTokens(text, false);
	}

	/**
	 * 查找
	 * @param text
	 * @param ignoreDup 是否去重
	 * @return
	 */
	public List<String> getTokens(String text, boolean ignoreDup) {
		List<String> contents = new ArrayList<String>();
		if ((text != null) && (text.length() > 0)) {
			char[] src = text.toCharArray();
			int offset = 0;
			int start = text.indexOf(this.openToken, offset);
			while (start > -1) {
				if ((start > 0) && (src[(start - 1)] == '\\')) {

					offset = start + this.openToken.length();
				} else {
					int end = text.indexOf(this.closeToken, start);
					if (end == -1) {

						offset = src.length;
					} else {

						offset = start + this.openToken.length();
						String content = new String(src, offset, end - offset);
						if(!ignoreDup||!contents.contains(content)){
							contents.add(content);
						}
						offset = end + this.closeToken.length();
					}
				}
				start = text.indexOf(this.openToken, offset);
			}
		}
		return contents;
	}

	public String parse(TokenHandler handler, String text) {
		StringBuilder builder = new StringBuilder();
		if ((text != null) && (text.length() > 0)) {
			char[] src = text.toCharArray();
			int offset = 0;
			int start = text.indexOf(this.openToken, offset);
			while (start > -1) {
				if ((start > 0) && (src[(start - 1)] == '\\')) {
					builder.append(src, offset, start - 1).append(
							this.openToken);
					offset = start + this.openToken.length();
				} else {
					int end = text.indexOf(this.closeToken, start);
					if (end == -1) {
						builder.append(src, offset, src.length - offset);
						offset = src.length;
					} else {
						builder.append(src, offset, start - offset);
						offset = start + this.openToken.length();
						String content = new String(src, offset, end - offset);
						builder.append(handler.handleToken(content));
						offset = end + this.closeToken.length();
					}
				}
				start = text.indexOf(this.openToken, offset);
			}
			if (offset < src.length) {
				builder.append(src, offset, src.length - offset);
			}
		}

		return builder.toString();
	}

	public static interface TokenHandler {
		public abstract String handleToken(String paramString);
	}
}
