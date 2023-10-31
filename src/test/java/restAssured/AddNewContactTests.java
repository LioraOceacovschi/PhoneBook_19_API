package restAssured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import dto.ContactResponseDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static org.hamcrest.Matchers.containsString;


import static com.jayway.restassured.RestAssured.given;

public class AddNewContactTests {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MzU3NDkxOCwiaWF0IjoxNjkyOTc0OTE4fQ.2FsmrZfzBiTfub5jWbWxQ9ROK5oyTAieMEdJZImGevg";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void addNewContactPositive() {
        int i = new Random().nextInt(1000) + 1000;
        String id;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("name")
                .lastName("lastName")
                .email("email@mail.com")
                .phone("5500994477" + i)
                .address("address")
                .description("description")
                .build();

        String contactResponseMessage = given()
                .contentType(ContentType.JSON)
                .body(contactDTO)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("Contact was added"))
                .extract()
                .as(ContactResponseDTO.class)
                .getMessage();

        System.out.println(contactResponseMessage);
        id = contactResponseMessage.substring(contactResponseMessage.lastIndexOf(" ") + 1);
        System.out.println(id);

    }


}
