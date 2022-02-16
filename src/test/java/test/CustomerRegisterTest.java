package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import testData.Assertions;
import testData.Customer;
import testData.CustomerClient;

public class CustomerRegisterTest {

    public CustomerClient customerClient;
    public Assertions assertions;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        assertions = new Assertions();
    }

    @Test
    @DisplayName("Проверка регистрации нового клиента")
    public void customerIsRegisteredTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response response = customerClient.registerCustomer(customer);

        assertions.assertStatusCode(response, 200);
        assertions.assertSuccessValue(response,true);
        assertions.assertCustomerBody(response);
    }

    @Test
    @DisplayName("Проверка, что нельзя зарегистрировать клиента, который уже существует")
    public void customerIsNotRegisteredIfExistsTest() {
        Customer customer = Customer.generateRandomCustomer();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);

        assertions.assertStatusCode(response, 403);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "User already exists");
    }

    @Test
    @DisplayName("Проверка, что нельзя зарегистрировать клиента, если не указан email")
    public void customerIsNotRegisteredIfEmailIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutEmail();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);

        assertions.assertStatusCode(response, 403);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Проверка, что нельзя зарегистрировать клиента, если не указан пароль")
    public void customerIsNotRegisteredIfPasswordIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutPassword();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);

        assertions.assertStatusCode(response, 403);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "Email, password and name are required fields");
    }

    @Test
    @DisplayName("Проверка, что нельзя зарегистрировать клиента, если не указано имя")
    public void customerIsNotRegisteredIfNameIsNotFilledTest() {
        Customer customer = Customer.generateRandomCustomerWithoutName();
        customerClient.registerCustomer(customer);
        Response response = customerClient.registerCustomer(customer);

        assertions.assertStatusCode(response, 403);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "Email, password and name are required fields");
    }

}
