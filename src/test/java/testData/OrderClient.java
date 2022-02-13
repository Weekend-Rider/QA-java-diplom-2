package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    public static final String ORDERS_PATH = "/orders/";

   @Step("Login the customer")
    public Response makeOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }
  /*   @Step("Update the customer")
    public Response authoriseAndUpdateCustomer(CustomerCredentials customerCredentials, Response response) {
        String token = getAccessToken(response);
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .body(customerCredentials)
                .when()
                .patch(AUTH_PATH + "user");
    }

    @Step("Update the customer")
    public Response notAuthoriseAndUpdateCustomer(CustomerCredentials customerCredentials, Response response) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .patch(AUTH_PATH + "user");
    }

    @Step("Get customer accessToken")
    public String getAccessToken(Response response) {
        String token = response.then()
                .extract()
                .path("accessToken");

        return token.substring(7);

    }
    @Step("Get customer refreshToken")
    public String getRefreshToken(Response response) {
        return response.then()
                .extract()
                .path("refreshToken");
    }
*/

}
