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
    @DisplayName("Проверка изменения email пользователя с авторизацией")
    public void customerChangeEmailWhenAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(response, 200);
        String token = customerClient.getAccessToken(response);
        Response responseChanged = customerClient.updateCustomerWithAuthorisation(CustomerCredentials.getCustomerCredentialsNewEmail(customer), token);

        assertions.assertStatusCode(responseChanged, 200);
        assertions.assertSuccessValue(responseChanged,true);
        assertions.assertCustomerBodyChangeData(responseChanged, "new" + customer.email, customer.name);
    }

    @Test
    @DisplayName("Проверка изменения имени пользователя с авторизацией")
    public void customerChangeNameWhenAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(response, 200);
        String token = customerClient.getAccessToken(response);
        Response responseChanged = customerClient.updateCustomerWithAuthorisation(CustomerCredentials.getCustomerCredentialsNewName(customer), token);

        assertions.assertStatusCode(responseChanged, 200);
        assertions.assertSuccessValue(responseChanged,true);
        assertions.assertCustomerBodyChangeData(responseChanged, customer.email, "new" + customer.name);
    }

    @Test
    @DisplayName("Проверка невозможнсти изменения email пользователя без авторизации")
    public void customerChangeEmailWhenNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(response, 200);
        Response responseChanged = customerClient.updateCustomerNoAuthorisation(CustomerCredentials.getCustomerCredentialsNewEmail(customer));

        assertions.assertStatusCode(responseChanged, 401);
        assertions.assertSuccessValue(responseChanged,false);
        assertions.assertMessageValue(responseChanged, "You should be authorised");
    }

    @Test
    @DisplayName("Проверка невозможнсти изменения имени пользователя без авторизации")
    public void customerChangeNameWhenNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(response, 200);
        Response responseChanged = customerClient.updateCustomerNoAuthorisation(CustomerCredentials.getCustomerCredentialsNewName(customer));

        assertions.assertStatusCode(responseChanged, 401);
        assertions.assertSuccessValue(responseChanged,false);
        assertions.assertMessageValue(responseChanged, "You should be authorised");
    }

}
