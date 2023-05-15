package com.c201.aebook.api.book.presentation.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;
import com.c201.aebook.api.book.service.impl.BookServiceImpl;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.utils.RegexValidator;

@ExtendWith(SpringExtension.class)
@Import(BookController.class)
public class BookControllerTest {
	@Autowired
	private BookController bookController;
	private MockMvc mockMvc;
	@MockBean
	private BookServiceImpl bookService;
	@MockBean
	private RegexValidator regexValidator;

	@BeforeEach
	protected void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
	}

	@Test
	public void testSearchBookDetail() throws Exception {
		// given
		String isbn = "9791185121147";
		CustomUserDetails customUserDetails = new CustomUserDetails(UserEntity.builder().id(1L).build());
		BookResponseDTO responseDTO = BookResponseDTO.builder().isbn(isbn).build();
		BDDMockito.given(bookService.searchBookDetail(isbn, customUserDetails)).willReturn(responseDTO);
		// when
		mockMvc.perform(get("/books/" + isbn)).andExpect(status().isOk());
		// then
		BDDMockito.verify(bookService).searchBookDetail(eq(isbn), any(CustomUserDetails.class));
	}

	@Test
	public void testGetAutocompleteTitle() throws Exception {
		// given
		String keyword = "keyword";
		List<String> titleList = new ArrayList<>();
		titleList.add("title1");
		titleList.add("title2");
		BDDMockito.given(bookService.getAutocompleteTitle(keyword)).willReturn(titleList);
		// when
		mockMvc.perform(get("/books/autocomplete?keyword=" + keyword)).andExpect(status().isOk());
		// then
		BDDMockito.verify(bookService).getAutocompleteTitle(keyword);
	}

	@Test
	public void testSearchBookList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetNewBookList() {
		throw new RuntimeException("not yet implemented");
	}

}
