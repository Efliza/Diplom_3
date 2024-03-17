import client.Client;
import client.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.*;
import client.ClientFaker;
import java.util.concurrent.TimeUnit;

public class LoginTest extends BrowserConfiguration  {
    private Client client;
    private String accessToken;


    @Before
    public void setUp() {
        driver.get(ClientSteps.baseURL);
        RestAssured.baseURI = ClientSteps.baseURL;
        client = ClientFaker.getRandomClient();
        accessToken = ClientSteps.createNewClient(client).then().extract().path("accessToken");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Тест на вход с главной страницы через кнопку "Войти в аккаунт"
    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginFromMainPageTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        mainPage.clickLoginButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    // Тест на вход через кнопку "Личный кабинет"
    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginFromProfilePageTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        mainPage.clickAccountButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    // Тест на вход через кнопку в форме регистрации
    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginFromRegisterPageTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        mainPage.clickAccountButton();
        loginPage.clickRegisterButton();
        registerPage.clickLoginButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    // Тест на вход через кнопку в форме восстановления пароля
    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginFromForgotPasswordPageTest(){
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        mainPage.clickAccountButton();
        loginPage.clickForgotPasswordButton();
        forgotPasswordPage.clickLoginButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    @After
    public void tearDown(){
        driver.quit();
        if (accessToken != null) {
            ClientSteps.deleteClient(accessToken);
        }
    }
}
