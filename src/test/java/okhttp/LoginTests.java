package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;

public class LoginTests implements BaseApi {

    TokenDto token;

    @Test
    public void loginUser() {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.code());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void loginPositiveTest_returnToken() throws IOException {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccessful()) {
            TokenDto token = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
            Assert.assertEquals(response.code(), 200);
        } else {
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            System.out.println(errorMessage.getError());
            Assert.fail("response code --> " + response.code());
        }
    }

    @Test
    public void loginNegativeTest_ExRes401() throws IOException {
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                "Qwerty123");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
        Assert.assertEquals(response.code(), 401);
        System.out.println(response.code());
        Assert.assertEquals(errorMessage.getStatus(), 401);
        Assert.assertTrue(errorMessage.getMessage().toString().contains("Login or Password incorrect"));
        System.out.println(errorMessage.getMessage().toString());


    }
}