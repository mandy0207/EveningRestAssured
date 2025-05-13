package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.net.HttpURLConnection;

import org.testng.Assert;
import org.testng.annotations.Test;

import enums.ApiResources;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payLoad.Payload;
import utils.ParseJson;
import utils.UniqueGenerator;

public class LibraryManagement {

	@Test
	public void verifyE2ELMS() {

		/**
		 *
		 * given => body, headers, authentication when => post, get, put, del then =>
		 * everything about response => assertion
		 */
		// isbn aisle
		String isbn = UniqueGenerator.getFaker().name().firstName();
		String aisle = Integer.toString(UniqueGenerator.getRandomNumber());

		RestAssured.baseURI = ApiResources.LibraryURL.getResource();
		String response = given().log().all().header("Content-Type", "application/json")
				.body(Payload.getBookPayload(isbn, aisle)).when().post(ApiResources.postBook.getResource()).then().log().all()
				.assertThat().statusCode(HttpURLConnection.HTTP_OK).body("Msg", equalTo("successfully added")).extract().asString();

		JsonPath js = ParseJson.getJsonParser(response);
		String bookID = js.getString("ID");
		String actualBookMsg = js.getString("Msg");
		//Assertion
		Assert.assertEquals(actualBookMsg, "successfully added", "Issue Found : Unable to add book");

		System.out.println("My Added book has id : :" + bookID);

		String getBookResponse = given().log().all().queryParam("ID", bookID).when().get(ApiResources.getBook.getResource()).then()
				.log().all().assertThat().statusCode(HttpURLConnection.HTTP_OK).extract().asString();

		String responseISBN = (String) ParseJson.getJsonParser(getBookResponse).getList("isbn").get(0);
		String responseAisle = (String) ParseJson.getJsonParser(getBookResponse).getList("aisle").get(0);

		String responseId = responseISBN + responseAisle;
		//Assertion
		Assert.assertEquals(responseId, bookID, "Issue Found : Business Logic of Id Failed");
		
		String deleteBookResponse=given().log().all().header("Content-Type", "application/json").body(Payload.getDeleteBookPayload(bookID)).
		when().delete(ApiResources.deleteBook.getResource()).then().log().all().assertThat().statusCode(HttpURLConnection.HTTP_OK).extract().response().asString();		
		
		 Assert.assertEquals(ParseJson.getJsonParser(deleteBookResponse).get("msg"), "book is successfully deleted");
		 
		 
	}

}
