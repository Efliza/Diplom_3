package client;

import com.github.javafaker.Faker;

public class ClientFaker {
    // Метод для генерации случайного пользователя
    public static Client getRandomClient(){
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password(10, 15);
        final String name = faker.name().fullName();
        return new Client(email, password, name);
    }

    // Метод для генерации случайного пользователя с неправильным паролем
    public static Client getRandomClientWithWrongPassword(){
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password(4, 6);
        final String name = faker.name().fullName();
        return new Client(email, password, name);
    }
}
