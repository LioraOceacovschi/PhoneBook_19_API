package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.ContactResponseDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContact {


    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MjYzOTk1OCwiaWF0IjoxNjkyMDM5OTU4fQ.hoMTnP5eptdCxzVRjjS1z4BINcC9Z-MZZjTAjP5uoFI";


    @Test
    public void addNewContactPositive() throws IOException {
        int i = (int) (System.currentTimeMillis()/1000%3600);
        ContactDTO contactDTO = ContactDTO.builder()
                .name("name")
                .lastName ("lastName")
                .email("email@mail.com")
                .phone("5500994477" + i)
                .address("address")
                .description("description")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if(response.isSuccessful()){
            ContactResponseDTO responseMessageDto =
                    gson.fromJson(response.body().string(), ContactResponseDTO.class);
            String message = responseMessageDto.getMessage();
            System.out.println(message);
            String id = message.substring(message.lastIndexOf(" ") + 1);
            System.out.println(id);

            System.out.println("Response code is -----> " +response.code());
            Assert.assertTrue(response.isSuccessful());
        }else{
            ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
            System.out.println(errorDTO.getStatus() + " " + errorDTO.getMessage() + " " + errorDTO.getError());
            System.out.println("Response code is -----> " +response.code());
            Assert.assertTrue(response.isSuccessful());

        }

    }
}
