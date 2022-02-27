package testData;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends RestAssuredClient {

    public static final String INGREDIENTS_PATH = "/ingredients/";

    @Step("Получаем список всех ингредиентов")
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

    @Step("Формируем список ингредиентов для создания заказа")
    public ArrayList<String> getIngredientsOrderList(ArrayList<String> ingredientsList) {
        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(ingredientsList.get(0));
        orderIngredients.add(ingredientsList.get(1));
        orderIngredients.add(ingredientsList.get(2));
        return orderIngredients;
    }

    @Step("Формируем список ингредиентов с некорректыми id для создания заказа")
    public ArrayList<String> getIngredientsOrderListIncorrect() {
        ArrayList<String> orderIngredients = new ArrayList<>();
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        orderIngredients.add(RandomStringUtils.randomAlphabetic(10));
        return orderIngredients;
    }

}
