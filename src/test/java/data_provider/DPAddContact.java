package data_provider;

import dto.ContactDtoLombok;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DPAddContact {

    @DataProvider
    public ContactDtoLombok[] addNewContactDP(){
        ContactDtoLombok contact1 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("qamail.com")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        ContactDtoLombok contact2 = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(10))
                .phone(generatePhone(10))
                .email("qa@mailcom")
                .address(generateString(20))
                .description(generateString(10))
                .build();
        return new ContactDtoLombok[] {contact1,contact2};
    }

    @DataProvider
    public Iterator<ContactDtoLombok> addNewContactDPFile(){
        List<ContactDtoLombok> contactList = new ArrayList<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/test/resources/wrong_email.csv"));
            String line = bufferedReader.readLine();
            while (line != null){
                String[] splitArray = line.split(",");
                contactList.add(ContactDtoLombok.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .phone(splitArray[2])
                        .email(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contactList.listIterator();
    }
}
