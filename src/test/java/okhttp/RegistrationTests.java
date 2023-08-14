package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTests {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationTestsPositive() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000 % 3600);


        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .username("lsop" + i + "@abc.com")
                .password("$Abcd1234")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(requestDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseJson = response.body().string();
            AuthResponseDTO responseDTO = gson.fromJson(responseJson, AuthResponseDTO.class);
            System.out.println("Token is   -  " + responseDTO.getToken());
            System.out.println("Response code is ----> " + response.code());
            Assert.assertTrue(response.isSuccessful());
        } else {
            System.out.println("Response code is ----> " + response.code());
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println(errorDTO.getStatus() + " " + errorDTO.getMessage() + " " + errorDTO.getError());
            Assert.assertTrue(response.isSuccessful());

        }


    }
}
