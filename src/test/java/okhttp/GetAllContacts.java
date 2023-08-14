package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContacts {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibHNvQGFiYy5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY5MjYzOTk1OCwiaWF0IjoxNjkyMDM5OTU4fQ.hoMTnP5eptdCxzVRjjS1z4BINcC9Z-MZZjTAjP5uoFI";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContactsPositive() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDTO contacts = gson.fromJson(response.body().string(), GetAllContactsDTO.class);

        for (ContactDTO contactDto:
             contacts.getContacts()) {
            System.out.println(contactDto.getId());
            System.out.println(contactDto.getEmail());
            System.out.println("=======================================");
        }


    }
}
