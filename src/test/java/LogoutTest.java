import client.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.*;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import client.Client;
import client.ClientFaker;
import java.util.concurrent.TimeUnit;

public class LogoutTest extends BrowserConfiguration {
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

    // Тест на выход из аккаунта в личном кабинете
    @Test
    @DisplayName("Выход из аккаунта в личном кабинете")
    public void logoutFromProfilePageTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.clickAccountButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickAccountButton();
        profilePage.clickLogoutButton();
        String text = loginPage.getEnterLabelText();
        Assert.assertEquals("Вход", text);
    }

    @After
    public void tearDown() {
        driver.quit();
        if (accessToken != null) {
            ClientSteps.deleteClient(accessToken);
        }
    }
}
