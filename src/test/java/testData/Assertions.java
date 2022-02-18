package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;


public class Assertions {

    @Step("Проверяем кода ответа")
    public void assertStatusCode(Response response, int statusCode) {
        response.then()
                .assertThat()
                .statusCode(statusCode);
    }

    @Step("Проверяем значение success")
    public void assertSuccessValue(Response response, boolean value) {
        response.then()
                .assertThat()
                .body("success", equalTo(value));
    }

    @Step("Проверяем тело ответа для заказа с авторизацией")
    public void assertOrderBodyAuthorised(Response response) {
         response.then()
                 .assertThat()
                 .body("name", notNullValue())
                 .and().body("order.ingredients", not(emptyArray()))
                 .and().body("order._id", not(equalTo(0)))
                 .and().body("order.owner", notNullValue())
                 .and().body("order.status", notNullValue())
                 .and().body("order.name", notNullValue())
                 .and().body("order.createdAt", notNullValue())
                 .and().body("order.updatedAt", notNullValue())
                 .and().body("order.number", not(equalTo(0)))
                 .and().body("order.price", not(equalTo(0)));
    }

    @Step("Проверяем тело ответа для заказа без авторизации")
    public void assertOrderBodyNotAuthorised(Response response) {
        response.then()
                .assertThat()
                .body("name", notNullValue())
                .and().body("order.number", not(equalTo(0)));
    }

    @Step("Проверяем значение ключа message")
    public void assertMessageValue(Response response, String message) {
         response.then()
                .assertThat()
                .body("message", equalTo(message));
    }

    @Step("Проверяем тело ответа для списка заказов пользователя с авторизацией")
    public void assertCustomersOrdersListBodyAuthorised(Response response) {
        response.then()
                .assertThat()
                .and().body("order", not(emptyArray()))
                .and().body("total", not(equalTo(0)))
                .and().body("totalToday", not(equalTo(0)));
    }

    @Step("Проверяем тело ответа для покупателя с авторизацией")
    public void assertCustomerBody(Response response) {
        response.then()
                .assertThat()
                .body("user.email", notNullValue())
                .and().body("user.name", notNullValue())
                .and().body("accessToken", notNullValue())
                .and().body("accessToken", notNullValue());
    }

    @Step("Проверяем тело ответа для покупателя с авторизацией")
    public void assertCustomerBodyChangeData(Response response, String newMail, String newName) {
        response.then()
                .assertThat()
                .body("user.email", equalTo(newMail))
                .and().body("user.name", equalTo(newName));
    }

}