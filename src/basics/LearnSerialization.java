package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.net.HttpURLConnection;

import org.testng.annotations.Test;

import enums.ApiResources;
import io.restassured.RestAssured;
import pojo.Book;
import utils.UniqueGenerator;

public class LearnSerialization {

	
	@Test
	public void myseriaLiseTest() {
		
		String isbn = UniqueGenerator.getFaker().name().firstName();
		String aisle = Integer.toString(UniqueGenerator.getRandomNumber());
		String author= UniqueGenerator.getFaker().book().author();
		String bookName= UniqueGenerator.getFaker().book().title();
		
		Book book = new Book(bookName, isbn, aisle, author);
		//Converting json  into java object : Serialization
		//Jackson is doing job for us
		RestAssured.baseURI = ApiResources.LibraryURL.getResource();
		given().log().all().header("Content-Type", "application/json")
				.body(book).when().post(ApiResources.postBook.getResource()).then().log().all()
				.assertThat().statusCode(HttpURLConnection.HTTP_OK).body("Msg", equalTo("successfully added"));
	}
	
	
}
