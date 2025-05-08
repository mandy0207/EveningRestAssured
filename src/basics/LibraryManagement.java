package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

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
		    given => body, headers, authentication
			when  => post, get, put, del
		    then  => everything about response => assertion
		 */
		//isbn aisle
		String isbn= UniqueGenerator.getFaker().name().firstName();
		String aisle= Integer.toString(UniqueGenerator.getRandomNumber());
	
		RestAssured.baseURI="http://216.10.245.166";
	   String response=given().log().all().header("Content-Type","application/json").body(Payload.getBookPayload(isbn, aisle))
		.when().post("/Library/Addbook.php").then()
		.log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().asString();
	
	   JsonPath js = ParseJson.getJsonParser(response);
	   String bookID=js.getString("ID");
	   String actualBookMsg = js.getString("Msg");
	   
	   Assert.assertEquals(actualBookMsg, "successfully added", "Issue Found : Unable to add book");
	   
	   System.out.println("My Added book has id : :"+ bookID);
	   
		String getBookResponse=given().log().all().queryParam("ID", bookID).when().get("/Library/GetBook.php").then().log().all()
		.assertThat().statusCode(200).extract().asString();
		
		String responseISBN=(String)ParseJson.getJsonParser(getBookResponse).getList("isbn").get(0);
        String responseAisle=(String)ParseJson.getJsonParser(getBookResponse).getList("aisle").get(0);
		
        String responseId=responseISBN+responseAisle;
        Assert.assertEquals(responseId, bookID, "Issue Found : Business Logic of Id Failed");
		
	}
	
	
}
