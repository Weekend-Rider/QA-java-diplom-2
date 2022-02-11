package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Customer;
import testData.CustomerClient;

import static org.hamcrest.Matchers.equalTo;
//import testData.CustomerCredentials;

public class CustomerRegisterTest {

    public CustomerClient customerClient;
    public Assertions assertions;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Check is a new customer registered")
    public void customerIsRegisteredTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        boolean isRegistered = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isRegistered, "New customer registration failed");

        //courierId = courierClient.loginAndReturnCourierId(CourierCredentials.getCourierLoginData(courier));
        //boolean isRegistered = assertions.assertCodeAndReturnOkValue(response, 201);
        //assertions.assertOkTrue(isRegistered, "Courier registration failed");
    }

    @Test
    @DisplayName("Check is a new customer registered")
    public void customerIsNotRegisteredIfExistsTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);
        boolean isRegistered = assertions.assertCodeAndReturnSuccessValue(response, 403);
        assertions.assertFalseValue(isRegistered, "Two same customers should not be registered");
    }

    @Test
    @DisplayName("Check is a new customer cannot register without an email")
    public void customerIsNotRegisteredIfEmailIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutEmail();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);
        boolean isRegistered = assertions.assertCodeAndReturnSuccessValue(response, 403);
        assertions.assertFalseValue(isRegistered, "Customer with no email filled should not be registered");
    }

    @Test
    @DisplayName("Check is a new customer cannot register without a password")
    public void customerIsNotRegisteredIfPasswordIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutPassword();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);
        boolean isRegistered = assertions.assertCodeAndReturnSuccessValue(response, 403);
        assertions.assertFalseValue(isRegistered, "Customer with no password filled should not be registered");
    }

    @Test
    @DisplayName("Check is a new customer cannot register without a name")
    public void customerIsNotRegisteredIfNameIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutName();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);
        boolean isRegistered = assertions.assertCodeAndReturnSuccessValue(response, 403);
        assertions.assertFalseValue(isRegistered, "Customer with no name filled should not be registered");
    }

}
