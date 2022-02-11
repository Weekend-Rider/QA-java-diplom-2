package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Customer;
import testData.CustomerClient;
import testData.CustomerCredentials;

public class CustomerChangeTest {

    public CustomerClient customerClient;
    public Assertions assertions;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Check is the customer can change email when authorized")
    public void customerChangeEmailWhenAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));
        boolean isAuthorised = assertions.assertCodeAndReturnSuccessValue(response, 200);
        assertions.assertTrueValue(isAuthorised, "Customer email change failed");
        Response responseChanged = customerClient.authoriseAndUpdateCustomer(CustomerCredentials.getCustomerCredentialsNewEmail(customer), response);
        boolean isChanged = assertions.assertCodeAndReturnSuccessValue(responseChanged, 200);
        assertions.assertCodeAndBodyKeyValue(responseChanged, 200, "user.email", "new" + customer.email );
    }

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

}
