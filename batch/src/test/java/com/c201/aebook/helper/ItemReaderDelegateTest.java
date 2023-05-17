package com.c201.aebook.helper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemReaderDelegateTest {

	@InjectMocks
	private ItemReaderDelegate helper;


	@Test
	@DisplayName("happy case")
	void testReplaceCharacterFromText(){
		//given
		String input = "This is a &quot;test&quot; string.";
		String expectedOutput = "This is a \"test\" string.";

		//when
		String output = helper.replaceCharacterFromText(input);


		//then
		Assertions.assertEquals(output, expectedOutput);
	}

}