package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.*;

import java.util.ArrayList;
import java.util.List;

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
    @DisplayName("Check is the customer can change email when authorized")
    public void makeOrderTest() {
        ArrayList<String> ingredientsList = ingredientsClient.getIngredientsList();
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



        /*customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer email change failed");
        Response responseChanged = customerClient.authoriseAndUpdateCustomer(CustomerCredentials.getCustomerCredentialsNewEmail(customer), response);
        boolean isChanged = assertions.assertCodeAndReturnSuccessValue(responseChanged, 200);
        assertions.assertCodeAndBodyKeyValue(responseChanged, 200, "user.email", "new" + customer.email );*/
    }
/*
    @Test
    @DisplayName("Check is the customer can change name when authorized")
    public void customerChangeNameWhenAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer email change failed");
        Response responseChanged = customerClient.authoriseAndUpdateCustomer(CustomerCredentials.getCustomerCredentialsNewName(customer), response);
        boolean isChanged = assertions.assertCodeAndReturnSuccessValue(responseChanged, 200);
        assertions.assertCodeAndBodyKeyValue(responseChanged, 200, "user.name", "new" + customer.name );
    }

    @Test
    @DisplayName("Check is the customer cannot change name when not authorized")
    public void customerChangeEmailWhenNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer email change failed");
        Response responseChanged = customerClient.notAuthoriseAndUpdateCustomer(CustomerCredentials.getCustomerCredentialsNewName(customer), response);
        boolean isChanged = assertions.assertCodeAndReturnSuccessValue(responseChanged, 401);
        assertions.assertCodeAndBodyKeyValue(responseChanged, 401, "message", "You should be authorised");
    }

    @Test
    @DisplayName("Check is the customer cannot change name when not authorized")
    public void customerChangeNameWhenNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer email change failed");
        Response responseChanged = customerClient.notAuthoriseAndUpdateCustomer(CustomerCredentials.getCustomerCredentialsNewName(customer), response);
        boolean isChanged = assertions.assertCodeAndReturnSuccessValue(responseChanged, 401);
        assertions.assertCodeAndBodyKeyValue(responseChanged, 401, "message", "You should be authorised");
    }
*/
}
