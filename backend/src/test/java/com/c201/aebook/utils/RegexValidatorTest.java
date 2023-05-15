package com.c201.aebook.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegexValidatorTest {
	@InjectMocks
	private RegexValidator regexValidator;

	@BeforeAll
	protected static void init() {
		System.out.println("=== RegexValidator Test Init ===");
	}

	@AfterAll
	protected static void close() {
		System.out.println("=== RegexValidator Test Close ===");
	}

	@BeforeEach
	protected void setUp() throws Exception {
		System.out.println("=== Start ===");
	}

	@AfterEach
	protected void finish() throws Exception {
		System.out.println("=== Finish ===");
	}

	@Test
	@DisplayName("testValidateIsbn: Happy Case 10")
	public void testValidateIsbn1() {
		// throw new RuntimeException("not yet implemented");

		// given
		String isbn = "A123456789";

		// when

		// then
	}

	@Test
	@DisplayName("testValidateIsbn: Happy Case 13")
	public void testValidateIsbn2() {
		// throw new RuntimeException("not yet implemented");

		// given
		String isbn = "1234567891011";

		// when
		regexValidator.validateIsbn(isbn);

		// then
	}

}
