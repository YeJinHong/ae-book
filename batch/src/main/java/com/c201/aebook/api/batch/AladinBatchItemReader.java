package com.c201.aebook.api.batch;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

import lombok.extern.slf4j.Slf4j;

/*
 * 알리딘 api에서 데이터 읽어옴
 * */

@Slf4j
@Component
public class AladinBatchItemReader implements ItemReader<BookEntity> {

	private final String API_URL = "https://www.aladin.co.kr/ttb/api/ItemList.aspx";
	private final String SEARCH_TARGET = "Used";
	private final String SUB_SEARCH_TARGET = "Book";
	private final String outputType = "xml";
	private final int maxResults = 50;
	@Value("${aladin.api.key}")
	private String API_KEY;
	private int nextIndex = 0;
	private List<BookEntity> bookList;

	private final String queryType = "ItemNewAll";

	/*
	 * 알라딘 api에서 책 정보를 읽어옴
	 * */
	@Override
	public BookEntity read() {

		if (bookList == null) {
			try {
				bookList = getDataFromApi();
				log.info("list size=" + bookList.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		BookEntity nextBookEntity = null;
		if (nextIndex < bookList.size()) {
			nextBookEntity = bookList.get(nextIndex);
			nextIndex++;
		}

		return nextBookEntity;
	}

	private List<BookEntity> getDataFromApi() throws
		IOException,
		ParserConfigurationException,
		SAXException,
		ParseException {
		RestTemplate restTemplate = new RestTemplate();

		List<BookEntity> books = new ArrayList<>();
		for (int pages = 1; pages <= 20; pages++) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL)
				.queryParam("ttbkey", API_KEY)
				.queryParam("QueryType", queryType)
				.queryParam("MaxResults", maxResults)
				.queryParam("start", pages)
				.queryParam("SearchTarget", SEARCH_TARGET)
				.queryParam("Version", "20131101")
				.queryParam("SubSearchTarget", SUB_SEARCH_TARGET)
				.queryParam("OptResult", "usedList")
				.queryParam("Output", outputType);

			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				String.class);
			String responseBody = response.getBody();

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource(new StringReader(responseBody));
			Document document = documentBuilder.parse(inputSource);

			NodeList itemNodes = document.getElementsByTagName("item");

			for (int i = 0; i < itemNodes.getLength(); i++) {
				Node itemNode = itemNodes.item(i);
				BookEntity entity = parseBook(itemNode);
				books.add(entity);
			}

		}

		return books;
	}

	private BookEntity parseBook(Node itemNode) throws ParseException {
		String title = getChildText(itemNode, "title");
		String author = getChildText(itemNode, "author");
		String publisher = getChildText(itemNode, "publisher");
		String pubDate = getChildText(itemNode, "pubDate");
		String coverUrl = getChildText(itemNode, "cover");
		String description = getChildText(itemNode, "description");
		String isbn13 = getChildText(itemNode, "isbn13");

		int price = Integer.parseInt(getChildText(itemNode, "priceSales"));

		String aladinUrl = getAladinUrl(itemNode);


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(pubDate);

		BookEntity book = BookEntity.builder()
			.coverImageUrl(coverUrl)
			.isbn(isbn13)
			.publishDate(date)
			.publisher(publisher)
			.title(title)
			.aladinUrl(aladinUrl)
			.author(author)
			.description(description)
			.price(price)
			.build();

		return book;
	}

	private String getAladinUrl(Node itemNode){
		NodeList nodeList = itemNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeName().equals("subInfo")){//subInfo > usedList > userUsed > link 가져옴
				NodeList subInfoList = node.getChildNodes();
				for(int j = 0; j < subInfoList.getLength(); j++){
					Node subInfoNode = subInfoList.item(j);
					if(subInfoNode.getNodeName().equals("usedList")){
						NodeList usedList = subInfoNode.getChildNodes();
						for(int k = 0; k < usedList.getLength(); k++){
							Node usedListNode = usedList.item(k);
							if(usedListNode.getNodeName().equals("userUsed")){
								NodeList userUsedList = usedListNode.getChildNodes();
								for(int m = 0; m < userUsedList.getLength(); m++){
									Node userUsedNode = userUsedList.item(m);
									if(userUsedNode.getNodeName().equals("link")){
										return userUsedNode.getTextContent();
									}
								}
							}
						}
					}
				}
			}
		}


		return null;
	}

	private String getChildText(Node itemNode, String tagName) {
		NodeList nodeList = itemNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			//해당 태그의 노드를 찾고, 그 노드의 텍스트 값 반환
			if (node.getNodeName().equals(tagName)) {
				return node.getTextContent();
			}
		}
		//해당 태그의 노드가 없는 경우 null 반환
		return null;
	}

}
