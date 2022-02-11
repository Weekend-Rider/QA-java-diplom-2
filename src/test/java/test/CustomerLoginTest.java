package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Customer;
import testData.CustomerClient;
import testData.CustomerCredentials;

public class CustomerLoginTest {

    public CustomerClient customerClient;
    public Assertions assertions;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Check is the customer authorized")
    public void customerIsAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer login failed");
    }

    @Test
    @DisplayName("Check is the customer cannot authorise with wrong email")
    public void customerCannotAuthoriseWrongEmailTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentialsWrongEmail(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 401);
        assertions.assertFalseValue(isAuthorised, "Customer with wrong email should not be authorised");
    }

    @Test
    @DisplayName("Check is the customer cannot authorise with wrong password")
    public void customerCannotAuthoriseWrongPasswordTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentialsWrongPassword(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 401);
        assertions.assertFalseValue(isAuthorised, "Customer with wrong password should not be authorised");
    }

}
