package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.example.model.Person;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static Faker faker = new Faker();
    private static List<Person> people = new ArrayList();

    private static void populate(int totalPopulation) {
        for (int i = 0; i < totalPopulation; i++) {
            people.add(new Person(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.phoneNumber().phoneNumber(),
                    faker.address().buildingNumber(),
                    faker.address().streetAddress(),
                    faker.address().city(),
                    faker.address().zipCode())
            );
        }
    }

    private static void saveToFile(byte[] value) throws IOException {
        Path path = Paths.get("db.bin");
        Files.write(path, value);
    }

    private static void saveToFile(Person person) throws Exception {
        FileOutputStream outputStream = new FileOutputStream("db.bin");
        ObjectOutputStream stream = new ObjectOutputStream(outputStream);
        stream.writeObject(person);
    }

    private static String convertToJson(Person person) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(person);
    }

    public static void main(String[] args) throws Exception {
        populate(1);
        Person person = people.get(0);
        String personJson = convertToJson(person);
        System.out.println(String.format("Person : %s", person));
        System.out.println(String.format("Person JSDN : %s", personJson));
        saveToFile(person);
    }
}
