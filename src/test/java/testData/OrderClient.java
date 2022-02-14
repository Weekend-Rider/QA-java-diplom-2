package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    public static final String ORDERS_PATH = "/orders/";


   @Step("Make an order with the authorisation")
    public Response makeOrder(Order order, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("ake an order without the authorisation")
    public Response makeOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Get the customer's orders list with authorisation")
    public Response getCustomerOrdersListAuthorised(String token) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(ORDERS_PATH);
    }

    @Step("Get the customer's orders list without authorisation")
    public Response getCustomerOrdersListNotAuthorised() {

        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }

}
