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
    @DisplayName("Проверка авторизации клиента")
    public void customerIsAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentials(customer));

        assertions.assertStatusCode(response, 200);
        assertions.assertSuccessValue(response,true);
        assertions.assertCustomerBody(response);
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизовать клиента, если не указан email")
    public void customerCannotAuthoriseWrongEmailTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentialsIncorrectEmail(customer));

        assertions.assertStatusCode(response, 401);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "email or password are incorrect");
    }

    @Test
    @DisplayName("Проверка, что нельзя авторизовать клиента, если не указан пароль")
    public void customerCannotAuthoriseWrongPasswordTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.loginCustomer(CustomerCredentials.getCustomerCredentialsIncorrectPassword(customer));

        assertions.assertStatusCode(response, 401);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "email or password are incorrect");
    }

}
