package basics;

import static io.restassured.RestAssured.given;

import java.net.HttpURLConnection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import enums.ApiResources;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Book;

public class LearnDeserialization {

	@Test
	public void mygetBookTest() throws JsonMappingException, JsonProcessingException {
		
		RestAssured.baseURI = ApiResources.LibraryURL.getResource();
		String getBookResponse = given().log().all().queryParam("ID", "YZ599291").when().get(ApiResources.getBook.getResource()).then()
				.log().all().assertThat().statusCode(HttpURLConnection.HTTP_OK).extract().asString();
			
		ObjectMapper mapper = new ObjectMapper();
	
		Book[] bookArray = mapper.readValue(getBookResponse,  Book[].class);
		
		for(Book book : bookArray) {
			System.out.println(book.getAuthor());
			System.out.println(book.getName());
		}
		
		Assert.assertEquals(bookArray[0].getName(), "Learn API Automation");
		


		
	}
}
