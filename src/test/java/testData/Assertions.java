package testData;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Assertions {

    @Step("Assert the response code and return Success key value")
    public boolean assertCodeAndReturnSuccessValue(Response response, int statusCode) {
        return response.then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .path("success");
    }

    @Step("Assert the response code, key and String value")
    public void assertCodeAndBodyKeyValue(Response response, int statusCode, String key, String value) {
        response.then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body(key, equalTo(value));
    }

    @Step("Assert the response code, key and Matcher value")
    public void assertCodeAndBodyKeyValue(Response response, int statusCode, String key, Matcher<Object> value) {
        response.then()
                .assertThat()
                .statusCode(statusCode)
                .and()
                .body(key, value);
    }

    @Step("Assert the response code and return Ok key value")
    public boolean assertCodeAndReturnOkValue(Response response, int statusCode) {
        return response.then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .path("ok");
    }

    @Step("Assert the response code and return Ok key value")
    public String assertCodeAndReturnKeyStringValue(Response response, int statusCode, String key) {
        return response.then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .path(key);
    }

    @Step("Assert the response code and return Ok key value")
    public int assertCodeAndReturnKeyIntValue(Response response, int statusCode, String key) {
        return response.then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .path(key);
    }

    @Step("Assert is Success value equals true")
    public void assertTrueValue(boolean action, String message) {
        assertTrue(message, action);
    }

    @Step("Assert is Success value equals false")
    public void assertFalseValue(boolean action, String message) {
        assertFalse(message, action);
    }

}