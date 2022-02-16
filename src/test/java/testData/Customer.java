package testData;

import com.github.javafaker.Faker;

public class Customer {

    public final String email;
    public final String password;
    public final String name;

    public Customer(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static Customer generateRandomCustomer() {
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password();
        final String name = faker.name().firstName();
        return new Customer(email, password, name);
    }

    public static Customer generateRandomCustomerWithoutEmail() {
        Faker faker = new Faker();
        final String password = faker.internet().password();
        final String name = faker.name().firstName();
        return new Customer(null, password, name);
    }

    public static Customer generateRandomCustomerWithoutPassword() {
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String name = faker.name().firstName();
        return new Customer(email, null, name);
    }

    public static Customer generateRandomCustomerWithoutName() {
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password();
        return new Customer(email, password, null);
    }

}
