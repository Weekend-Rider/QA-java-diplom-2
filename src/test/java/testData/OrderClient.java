package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    public static final String ORDERS_PATH = "/orders/";

    @Step("Создание заказа с атворизацией")
    public Response makeOrder(Order order, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Создание заказа без авторизации")
    public Response makeOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов авторизованного пользоватлея")
    public Response getCustomerOrdersListAuthorised(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(ORDERS_PATH);
    }

    @Step("Получение списка заказов неавторизованного пользоватлея")
    public Response getCustomerOrdersListNotAuthorised() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH);
    }

}
