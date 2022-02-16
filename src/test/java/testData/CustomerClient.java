package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CustomerClient extends RestAssuredClient {

    public static final String AUTH_PATH = "/auth/";

    @Step("Регистрируем нового клиента")
    public Response registerCustomer(Customer customer) {
        return given()
                .spec(getBaseSpec())
                .body(customer)
                .when()
                .post(AUTH_PATH + "register");
    }

    @Step("Авторизуем клиента")
    public Response loginCustomer(CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .post(AUTH_PATH + "login");
    }

    @Step("Обновляем данные клиента, запрос с авторизацией")
    public Response updateCustomerWithAuthorisation(CustomerCredentials customerCredentials, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(customerCredentials)
                .when()
                .patch(AUTH_PATH + "user");
    }

    @Step("Обновляем данные клиента, запрос без авторизации")
    public Response updateCustomerNoAuthorisation(CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .patch(AUTH_PATH + "user");
    }

    @Step("Получаем accessToken клиента")
    public String getAccessToken(Response response) {
        String token = response.then()
                .extract()
                .path("accessToken");

        return token.substring(7);
    }

    @Step("Получаем refreshToken клиента")
    public String getRefreshToken(Response response) {
        return response.then()
                .extract()
                .path("refreshToken");
    }


}
