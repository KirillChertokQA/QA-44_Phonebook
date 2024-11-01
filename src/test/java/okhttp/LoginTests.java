package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDto;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.generateEmail;

public class LoginTests implements BaseApi {

    TokenDto token;

    UserDto user;

    @BeforeMethod(alwaysRun = true)
    public void registrationUser() {
        user = new UserDto(generateEmail(10), "Qwerty123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("registration response is successful --> " + response.isSuccessful());
    }

    @Test(groups = {"smoke", "positive"})
    public void loginPositiveTest() {

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
        user.setPassword("wrong_password");
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
        if (response.code() == 401) {
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            Assert.assertTrue(errorMessage.getMessage().toString().equals("Login or Password incorrect"));
        }else{
            Assert.fail("something went wrong, response code --> "+response.code());
//            System.out.println(response.code());
//            System.out.println(errorMessage.getMessage().toString());
//            Assert.assertEquals(response.code(), 401);
//        Assert.assertEquals(errorMessage.getStatus(), 401);

        }
    }

    @Test(groups = "negative")
    public void loginNegativeTest_wrongLogin() throws IOException {
        user.setUsername("logingmail.com");
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
        if (response.code() == 401) {
            ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            Assert.assertTrue(errorMessage.getMessage().toString().equals("Login or Password incorrect"));
        }else{
            Assert.fail("something went wrong, response code --> "+response.code());

        }
    }
}
