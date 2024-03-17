import client.ClientFaker;
import client.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.*;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;
import client.Client;
import java.util.concurrent.TimeUnit;

public class ProfileToConstructorTest extends BrowserConfiguration {
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

    // Тест на переход из личного кабинета в конструктор по клику на «Конструктор»
    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void profileToConstructorFromButtonTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.clickAccountButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickAccountButton();
        profilePage.clickConstructorButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    // Тест на переход из личного кабинета в конструктор по клику на логотип Stellar Burgers
    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void profileToConstructorFromLogoTest() {
        MainPage mainPage = new MainPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        ProfilePage profilePage = new ProfilePage(driver);
        mainPage.clickAccountButton();
        loginPage.setClientLoginData(client.getEmail(), client.getPassword());
        loginPage.clickLoginButton();
        mainPage.clickAccountButton();
        profilePage.clickLogoButton();
        String text = mainPage.getCreateOrderButtonText();
        Assert.assertEquals("Оформить заказ", text);
    }

    @After
    public void tearDown() {
        driver.quit();
        if (accessToken != null) {
            ClientSteps.deleteClient(accessToken);
        }
    }
}
