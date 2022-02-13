package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    public static final String INGREDIENTS_PATH = "/ingredients/";

    @Step("Register a new customer")
    public ArrayList<String> getIngredientsList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .extract()
                .body()
                .path("data._id");
    }

    @Step("Register a new customer")
    public ArrayList<String> getIngredientsOrderList(ArrayList<String> ingredientsList) {
        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredientsList.get(0));
        orderIngredients.add(ingredientsList.get(1));
        orderIngredients.add(ingredientsList.get(2));
        System.out.println(orderIngredients);
        return orderIngredients;

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
