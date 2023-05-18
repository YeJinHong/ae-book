package com.c201.aebook.helper;

import static org.mockito.Mockito.*;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.Date;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

@ExtendWith(MockitoExtension.class)
class ItemReaderDelegateTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ItemReaderDelegate subject;

	@BeforeEach
	protected void setUp() throws Exception {
		ReflectionTestUtils.setField(subject, "API_KEY", "ttbleedy9091441001");
		ReflectionTestUtils.setField(subject, "restTemplate", restTemplate);
	}


	@Test
	@DisplayName("happy case")
	public void testReplaceCharacterFromText(){
		//given
		String input = "This is a &quot;test&quot; string.";
		String expectedOutput = "This is a \"test\" string.";

		//when
		String output = subject.replaceCharacterFromText(input);


		//then
		Assertions.assertEquals(output, expectedOutput);
	}

	@Test
	@DisplayName("happy case")
	public void testParseBook() throws Exception{

	}


	@Test
	public void testGetItemElementByUrl() throws ParserConfigurationException, IOException, SAXException {
		//given
		String dummyUrl = "http://example.com";
		String dummyResponse = "<response><item><subInfo>Some information</subInfo></item></response>";
		String dummyTagName = "item";

		//mocking restTemplate.exchange 메서드
		ResponseEntity<String> responseEntityMock = mock(ResponseEntity.class);
		when(responseEntityMock.getBody()).thenReturn(dummyResponse);
		when(restTemplate.exchange(eq(dummyUrl), eq(HttpMethod.GET), isNull(), eq(String.class)))
			.thenReturn(responseEntityMock);


		//when
		NodeList result = subject.getElementsByUrl(UriComponentsBuilder.fromHttpUrl(dummyUrl), dummyTagName);

		//then
		Assertions.assertNotNull(result);

		Node itemNode = result.item(0);
		Assertions.assertEquals("Some information", itemNode.getTextContent());
	}




}