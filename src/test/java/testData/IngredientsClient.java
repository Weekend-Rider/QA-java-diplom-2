package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    public static final String INGREDIENTS_PATH = "/ingredients/";

    @Step("Get the list of all the ingredients")
    public ArrayList<String> getAllIngredientsList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .extract()
                .body()
                .path("data._id");
    }

    @Step("Prepare the ingredients list for the order")
    public ArrayList<String> getIngredientsOrderList(ArrayList<String> ingredientsList) {
        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredientsList.get(0));
        orderIngredients.add(ingredientsList.get(1));
        orderIngredients.add(ingredientsList.get(2));
        System.out.println(orderIngredients);
        return orderIngredients;

    }

    @Step("Prepare the incorrect ingredients list for the order")
    public ArrayList<String> getIngredientsOrderListIncorrect() {
        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        System.out.println(orderIngredients);
        return orderIngredients;
    }

}
