package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CustomerClient extends RestAssuredClient {

    public static final String AUTH_PATH = "/auth/";

    @Step("Register a new customer")
    public Response registerCustomer(Customer customer) {
        return given()
                .spec(getBaseSpec())
                .body(customer)
                .when()
                .post(AUTH_PATH + "register");
    }

    @Step("Login the customer")
    public Response loginCustomer(CustomerCredentials customerCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(customerCredentials)
                .when()
                .post(AUTH_PATH + "login");
    }
    @Step("Update the customer")
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


}
