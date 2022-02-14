package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.*;

import java.util.ArrayList;

public class OrdersTest {

    public CustomerClient customerClient;
    public IngredientsClient ingredientsClient;
    public Assertions assertions;
    public OrderClient orderClient;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        ingredientsClient = new IngredientsClient();
        assertions = new Assertions();
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check is a new order created with customer authorisation and correct ingredients")
    public void makeOrderWithAuthorisationAndIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response customerLogin = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(customerLogin, 200);
        System.out.println(isAuthorised);

        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        System.out.println(ingredientsList);
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);
        String token = customerClient.getAccessToken(customerLogin);
        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order, token);

        boolean isCreated = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isCreated, "Order creation failed");
        String nameValue = assertions.assertCodeAndReturnKeyStringValue(response,200, "name");
        System.out.println(nameValue);
        int numberValue = assertions.assertCodeAndReturnKeyIntValue(response,200, "order.number");
        System.out.println(numberValue);
    }

    @Test
    @DisplayName("Check is a new order created without customer authorisation and correct ingredients")
    public void makeOrderNoAuthorisationAndIngredientsTest() {
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        System.out.println(ingredientsList);
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);

        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order);

        boolean isCreated = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isCreated, "Order creation failed");
        String nameValue = assertions.assertCodeAndReturnKeyStringValue(response,200, "name");
        System.out.println(nameValue);
        int numberValue = assertions.assertCodeAndReturnKeyIntValue(response,200, "order.number");
        System.out.println(numberValue);
    }

    @Test
    @DisplayName("Check is a new order created with customer authorisation and no ingredients")
    public void makeOrderWithAuthorisationAndNoIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response customerLogin = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(customerLogin, 200);
        System.out.println(isAuthorised);
        String token = customerClient.getAccessToken(customerLogin);
        Order order = Order.generateOrderData();
        Response response = orderClient.makeOrder(order,token);

        boolean isCreated = assertions.assertCodeAndReturnSuccessValue(response, 400);
        assertions.assertFalseValue(isCreated, "Order cannot be created without any ingredients");
        String nameValue = assertions.assertCodeAndReturnKeyStringValue(response,400, "message");
        System.out.println(nameValue);

    }

    @Test
    @DisplayName("Check is a new order created with customer authorisation and incorrect ingredients ids")
    public void makeOrderWithAuthorisationAndIncorrectIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response customerLogin = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(customerLogin, 200);
        System.out.println(isAuthorised);

        ArrayList<String> ingredientsOrderList = ingredientsClient.getIngredientsOrderListIncorrect();
        String token = customerClient.getAccessToken(customerLogin);
        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order,token);

        response.then()
                .assertThat()
                .statusCode(500);

    }

    @Test
    @DisplayName("Check is the orders list with customer authorisation can be received")
    public void getCustomerOrdersListAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response customerLogin = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(customerLogin, 200);
        System.out.println(isAuthorised);
        String token = customerClient.getAccessToken(customerLogin);
        System.out.println(token);
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        System.out.println(ingredientsList);
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);

        Order order = Order.generateOrderData(ingredientsOrderList);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);

        Response response = orderClient.getCustomerOrdersListAuthorised(token);

        boolean isGot = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isGot, "Customer order list getting failed");

        ArrayList<String> nameValue = assertions.assertCodeAndReturnKeyArrayValue(response,200, "orders.number");
        System.out.println(nameValue);
    }

    @Test
    @DisplayName("Check is the orders list with customer authorisation cannot be received")
    public void getCustomerOrdersListNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response customerLogin = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(customerLogin, 200);
        System.out.println(isAuthorised);
        String token = customerClient.getAccessToken(customerLogin);
        System.out.println(token);
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        System.out.println(ingredientsList);
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);

        Order order = Order.generateOrderData(ingredientsOrderList);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);

        Response response = orderClient.getCustomerOrdersListNotAuthorised();

        boolean isGot = assertions.assertCodeAndReturnSuccessValue(response, 401);
        assertions.assertFalseValue(isGot, "Customer order list getting failed");

        String nameValue = assertions.assertCodeAndReturnKeyStringValue(response,401, "message");
        System.out.println(nameValue);

    }

}
