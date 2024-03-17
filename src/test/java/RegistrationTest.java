import client.Client;
import client.ClientLogin;
import client.ClientSteps;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import pages.MainPage;
import pages.LoginPage;
import pages.RegisterPage;
import client.ClientFaker;
import io.restassured.RestAssured;
import java.util.concurrent.TimeUnit;

public class RegistrationTest extends BrowserConfiguration {
    private Client client;
    private String accessToken;

    @Before
    public void setUp() {
        driver.get(ClientSteps.baseURL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void registerNewClientTest() {
        client = ClientFaker.getRandomClient();
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        mainPage.clickLoginButton();
        loginPage.clickRegisterButton();
        registerPage.setClientInfo(client.getName(), client.getEmail(), client.getPassword());
        registerPage.clickRegisterButton();
        String text = loginPage.getEnterLabelText();
        Assert.assertEquals("Вход", text);
        ClientLogin login = new ClientLogin(client.getEmail(), client.getPassword());
        RestAssured.baseURI = ClientSteps.baseURL;
        accessToken = ClientSteps.loginClient(login).then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Регистрация пользователя с некорректным паролем")
    public void registerNewClientWithWrongPasswordTest() {
        client = ClientFaker.getRandomClientWithWrongPassword();
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        mainPage.clickLoginButton();
        loginPage.clickRegisterButton();
        registerPage.setClientInfo(client.getName(), client.getEmail(), client.getPassword());
        registerPage.clickRegisterButton();
        String text = registerPage.getPasswordErrorText();
        Assert.assertEquals("Некорректный пароль", text);
    }

    @After
    public void tearDown() {
        driver.quit();
        if (accessToken != null) {
            try {
                ClientSteps.deleteClient(accessToken);
            } catch (Exception e) {
                System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
            }
        }
    }
}
