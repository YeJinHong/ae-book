package com.c201.aebook.api.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
		assertNotNull(actualBook);
	}

	// @Test
	// @DisplayName("happy case")
	// public void testRead() throws Exception {
	// 	//given
	// 	List<BookEntity> bookList = new ArrayList<>();
	// 	//bookList.add(BookEntity.builder().author("author1").title("title1").build());
	// 	//bookList.add(BookEntity.builder().author("author2").title("title2").build());
	//
	// 	// restTemplate mock 설정
	// 	Mockito.when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("dummy response");
	//
	// 	// subject.getDataFromApi() mock 설정
	// 	AladinBatchItemReader mockReader = Mockito.spy(subject);
	// 	NodeList mockNodeList = mock(NodeList.class);
	//
	// 	doReturn(mockNodeList).when(mockReader).getItemElementByUrl(any(UriComponentsBuilder.class), eq("item"));
	// 	when(mockNodeList.getLength()).thenReturn(bookList.size()); // getLength() 메소드 수정
	// 	when(mockReader.parseBook(any(Node.class))).thenReturn(BookEntity.builder().author("author").title("title").build());
	// 	when(mockReader.getDataFromApi()).thenReturn(bookList);
	//
	// 	// when
	// 	BookEntity actualBook = mockReader.read();
	//
	// 	// then
	// 	assertNotNull(actualBook);
	// 	//assertEquals("title", actualBook.getTitle());
	// 	//assertEquals("author", actualBook.getAuthor());
	// }

	@Test
	@DisplayName("happy case")
	public void testGetDataFromApi() {
		//given

		//when

		//then

	}

	@Test
	void testParseBook() {

	}

	@Test
	void testGetItemElementByUrl() {
	}

}
