package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ClientSteps {
    public static String baseURL = "https://stellarburgers.nomoreparties.site/";
    public static String clientLoginPath = "/api/auth/login";
    public static String clientPath = "/api/auth/user";
    public static String clientRegisterPath = "api/auth/register";

    @Step("Создание пользователя")
    public static Response createNewClient(Client client) {
        return given()
                .header("Content-type", "application/json")
                .body(client)
                .when()
                .post(clientRegisterPath);
    }

    @Step("Логин пользователя")
    public static Response loginClient(ClientLogin clientLogin) {
        return given()
                .header("Content-type", "application/json")
                .body(clientLogin)
                .when()
                .post(clientLoginPath);
    }

    @Step("Удаление пользователя")
    public static Response deleteClient(String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .when()
                .delete(clientPath);
    }
}
