package testData;

import com.github.javafaker.Faker;

import java.util.ArrayList;


public class Order {

    public final ArrayList<String> ingredients;

    public Order(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order generateOrderData(ArrayList<String> ingredientsOrderList) {

        return new Order(ingredientsOrderList);
    }

    /*public static Order generateRandomCustomerWithoutEmail() {
        Faker faker = new Faker();
        final String password = faker.internet().password();
        final String name = faker.name().firstName();
        return new Order(null, password, name);
    }

    public static Order generateRandomCustomerWithoutPassword() {
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String name = faker.name().firstName();
        return new Order(email, null, name);
    }

    public static Order generateRandomCustomerWithoutName() {
        Faker faker = new Faker();
        final String email = faker.internet().emailAddress();
        final String password = faker.internet().password();
        return new Order(email, password, null);
    }
*/

}
