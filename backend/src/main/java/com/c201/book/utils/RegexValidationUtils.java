package com.c201.book.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.c201.book.utils.exeption.CustomException;
import com.c201.book.utils.exeption.ErrorCode;

@Component
public class RegexValidationUtils {

	/**
	 * [문희주] ISBN 정규 표현식 검사
	 * @param isbn
	 */
	public void IsbnValidation(String isbn) {
		Pattern pattern = Pattern.compile("\\d{13}");
		Matcher matcher = pattern.matcher((isbn));

		if (!matcher.find()) {
			throw new CustomException(ErrorCode.INVALID_ISBN);
		}

	}
}
