package com.c201.aebook.api.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

@ExtendWith(MockitoExtension.class)
public class AladinBatchItemReaderTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private AladinBatchItemReader subject;

	@BeforeEach
	protected void setUp() throws Exception {
		ReflectionTestUtils.setField(subject, "API_KEY", "ttbleedy9091441001");
	}

	@Test
	public void readTest() throws Exception {
		// given
		AladinBatchItemReader mockReader = Mockito.spy(subject);
		List<BookEntity> mockBooks = new ArrayList<>();

		mockBooks.add(BookEntity.builder().author("author").title("title").build());
		Mockito.doReturn(mockBooks).when(mockReader).getDataFromApi();

		// when
		BookEntity actualBook = mockReader.read();

		// then
		Assertions.assertNotNull(actualBook);
	}


	@Test
	public void testGetDataFromApi() throws IOException, ParserConfigurationException, SAXException, ParseException {


	}

	@Test
	void testParseBook() {

	}

	@Test
	void testGetItemElementByUrl() {
	}

}
