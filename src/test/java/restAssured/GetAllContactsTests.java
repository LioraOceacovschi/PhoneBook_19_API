package restAssured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class GetAllContactsTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MzU3NDkxOCwiaWF0IjoxNjkyOTc0OTE4fQ.2FsmrZfzBiTfub5jWbWxQ9ROK5oyTAieMEdJZImGevg";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void getAllContactsPositive(){
        GetAllContactsDTO allContactsDTO = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .as(GetAllContactsDTO.class);
        int countContacts = 0;
        for (ContactDTO contact: allContactsDTO.getContacts()) {
            System.out.println(contact.toString());
            countContacts++;
        }
        System.out.println(countContacts);


    }
}
