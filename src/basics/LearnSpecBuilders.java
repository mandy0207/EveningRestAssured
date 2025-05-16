package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.net.HttpURLConnection;

import org.testng.annotations.Test;

import enums.ApiResources;
import io.restassured.http.ContentType;
import payLoad.Payload;
import pojo.Book;
import utils.SpecBuildersUtil;
import utils.UniqueGenerator;

public class LearnSpecBuilders {

	@Test
	public void mytestUsingSpecs() {
		
		String isbn = UniqueGenerator.getFaker().name().firstName();
		String aisle = Integer.toString(UniqueGenerator.getRandomNumber());
		String author= UniqueGenerator.getFaker().book().author();
		String bookName= UniqueGenerator.getFaker().book().title();
		
		Book book = new Book(bookName, isbn, aisle, author);
		
		//SpecBuilders are used basically for enhancing code reusablity
		
		given().spec(SpecBuildersUtil.makeRequestSpec(ApiResources.LibraryURL.getResource(), ContentType.JSON)).body(book)
				.when().post(ApiResources.postBook.getResource()).then().log().all()
				.spec(SpecBuildersUtil.makeResponseSpec("Msg", HttpURLConnection.HTTP_OK,  equalTo("successfully added")));
		
		
	}
}
