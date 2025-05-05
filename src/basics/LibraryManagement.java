package basics;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import payLoad.Payload;
public class LibraryManagement {

	
	@Test
	public void verifyE2ELMS() {

		/**
		 * 
		    given => body, headers, authentication
			when  => post, get, put, del
		    then  => everything about response => assertion
		 */
		
		//unique isbn, aisle
		
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().header("Content-Type","application/json").body(Payload.addBookPayload("", "")).when().post("/Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"));
		
		
		
	}
	
	
}
