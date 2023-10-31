package restAssured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import dto.ContactResponseDTO;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateContactTests {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MzU3NDkxOCwiaWF0IjoxNjkyOTc0OTE4fQ.2FsmrZfzBiTfub5jWbWxQ9ROK5oyTAieMEdJZImGevg";
    String id;
ContactDTO contactDTO;
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000) + 1000;
        contactDTO = ContactDTO.builder()
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
                .path("message");
        id = contactResponseMessage.substring(contactResponseMessage.lastIndexOf(" ") + 1);

    }

    @Test
    public void updateContactPositive() {
        String name = contactDTO.getName();
        System.out.println("name = " +name);
        contactDTO.setId(id);
        contactDTO.setName("AUTO");
        System.out.println("name = " +contactDTO.getName());


        given()
                .header("Authorization",token)
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",containsString("Contact was updated"));
    }

}
