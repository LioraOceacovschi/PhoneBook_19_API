package restAssured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class RegistrationTests {
    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationPositive(){
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("lso"+i+"@abc.com")
                .password("$Abcd1234")
                .build();

        String token = given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("token");

        System.out.println(token);


    }

    @Test
    public void registrationNegativeWrongEmail(){
        int i = new Random().nextInt(1000)+1000;
        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("lso"+i+"abc.com")
                .password("$Abcd1234")
                .build();

         given()
                .body(requestDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("user/registration/usernamepassword")
                .then()
                .assertThat().statusCode(400)
               .assertThat().body("message.username", containsString("must be a well-formed email address"));


    }
}
