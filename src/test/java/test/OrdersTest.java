package test;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.*;

import java.util.ArrayList;

public class OrdersTest {

    public CustomerClient customerClient;
    public IngredientsClient ingredientsClient;
    public Assertions assertions;
    public OrderClient orderClient;
    public String token;

    @Before
    public void setUp() {
        customerClient = new CustomerClient();
        ingredientsClient = new IngredientsClient();
        orderClient = new OrderClient();
        assertions = new Assertions();
    }

    @After
    public void tearDown() {
        customerClient.deleteCustomer(token);
    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией клиента и корректными ингредиентами")
    public void makeOrderWithAuthorisationAndIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response newCustomer = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(newCustomer,200);
        token = customerClient.getAccessToken(newCustomer);
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);
        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order, token);

        assertions.assertStatusCode(response,200);
        assertions.assertSuccessValue(response,true);
        assertions.assertOrderBodyAuthorised(response);
    }

    @Test
    @DisplayName("Проверка создания заказа без авторизациии клиента и корректными ингредиентами")
    public void makeOrderNoAuthorisationAndIngredientsTest() {
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);
        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order);

        assertions.assertStatusCode(response,200);
        assertions.assertSuccessValue(response,true);
        assertions.assertOrderBodyNotAuthorised(response);
    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией клиента и без ингредиентов")
    public void makeOrderWithAuthorisationAndNoIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response newCustomer = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(newCustomer,200);
        token = customerClient.getAccessToken(newCustomer);
        Order order = Order.generateOrderData();
        Response response = orderClient.makeOrder(order,token);

        assertions.assertStatusCode(response,400);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "Ingredient ids must be provided");
    }

    @Test
    @DisplayName("Проверка создания заказа с авторизацией клиента и некорректными id ингредиентов")
    public void makeOrderWithAuthorisationAndIncorrectIngredientsTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response newCustomer = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(newCustomer,200);
        token = customerClient.getAccessToken(newCustomer);
        ArrayList<String> ingredientsOrderList = ingredientsClient.getIngredientsOrderListIncorrect();
        Order order = Order.generateOrderData(ingredientsOrderList);
        Response response = orderClient.makeOrder(order,token);

        assertions.assertStatusCode(response,500);
    }

    @Test
    @DisplayName("Проверка получения списка заказов клиента с авторизацией")
    public void getCustomerOrdersListAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response newCustomer = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(newCustomer,200);
        token = customerClient.getAccessToken(newCustomer);
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);
        Order order = Order.generateOrderData(ingredientsOrderList);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        Response response = orderClient.getCustomerOrdersListAuthorised(token);

        assertions.assertStatusCode(response,200);
        assertions.assertSuccessValue(response,true);
        assertions.assertCustomersOrdersListBodyAuthorised(response);
    }

    @Test
    @DisplayName("Проверка невозможности получения списка заказов клиента без авторизации")
    public void getCustomerOrdersListNotAuthorisedTest() {
        Customer customer = Customer.generateRandomCustomer();
        Response newCustomer = customerClient.registerCustomer(customer);
        assertions.assertStatusCode(newCustomer,200);
        token = customerClient.getAccessToken(newCustomer);
        ArrayList<String> ingredientsList = ingredientsClient.getAllIngredientsList();
        ArrayList<String> ingredientsOrderList =  ingredientsClient.getIngredientsOrderList(ingredientsList);
        Order order = Order.generateOrderData(ingredientsOrderList);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        orderClient.makeOrder(order,token);
        Response response = orderClient.getCustomerOrdersListNotAuthorised();

        assertions.assertStatusCode(response,401);
        assertions.assertSuccessValue(response,false);
        assertions.assertMessageValue(response, "You should be authorised");

    }

}
