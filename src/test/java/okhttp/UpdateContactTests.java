package okhttp;

import dto.*;
import interfaces.BaseApi;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;
import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class UpdateContactTests implements BaseApi {

    TokenDto token;
    ContactDtoLombok contact;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass(alwaysRun = true)
    public void loginUser(){
        UserDto user = new UserDto(getProperty("data.properties", "email"),
                getProperty("data.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
            token  = GSON.fromJson(response.body().string(), TokenDto.class);
            System.out.println(token.getToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void getAllUserPhonesPositiveTest_getContactList() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .get()
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(response.isSuccessful()) {
            ContactsDto contacts = GSON.fromJson(response.body().string(), ContactsDto.class);
            contact = contacts.getContacts()[0];
            System.out.println(contact);
        }else
            System.out.println("Something went wrong");
    }

    @Test(groups = {"smoke", "positive"})
    public void updateContactPositiveTest(){   //contact     update    contactNew
        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .id(contact.getId())
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactNew), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .put(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(response.isSuccessful());
    }

    @Test(groups = "negative")
    public void updateContactNegativeTest_WOName() throws IOException {
        ContactDtoLombok contactNew = ContactDtoLombok.builder()
                .id(contact.getId())
                .name("")
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email(generateEmail(12))
                .address(generateString(20))
                .description(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactNew), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+GET_ALL_CONTACTS_PATH)
                .addHeader("Authorization", token.getToken())
                .put(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ErrorMessageDto errorMessage = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
        softAssert.assertEquals(response.code(), 400);
        softAssert.assertEquals(errorMessage.getStatus(), 400);
        softAssert.assertTrue(errorMessage.getMessage().toString().contains("name=must not be blank"));
        System.out.println(errorMessage.getMessage().toString());
        softAssert.assertAll();
    }
}
