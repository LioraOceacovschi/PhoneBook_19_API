package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactById {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MjYzOTk1OCwiaWF0IjoxNjkyMDM5OTU4fQ.hoMTnP5eptdCxzVRjjS1z4BINcC9Z-MZZjTAjP5uoFI";


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    String id;

    @BeforeMethod
    public void precondition() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000 % 3600);

        ContactDTO contactDTO = ContactDTO.builder()
                .name("name")
                .lastName("lastName")
                .email("email@mail.com")
                .phone("5500994477" + i)
                .address("address")
                .description("description")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ContactResponseDTO contactResponseDTO = gson.fromJson(response.body().string(), ContactResponseDTO.class);
        String message = contactResponseDTO.getMessage();
        System.out.println(message);
        id = message.substring(message.lastIndexOf(" ") + 1);
        System.out.println(id);
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteContactByIDPositive() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/" + id)
                .addHeader("Authorization", token)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        ContactResponseDTO contactResponseDTO = gson.fromJson(response.body().string(), ContactResponseDTO.class);

        String message = contactResponseDTO.getMessage();
        System.out.println(message);

        Assert.assertTrue(response.isSuccessful());
    }

}
