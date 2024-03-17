import client.ClientFaker;
import client.ClientSteps;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.*;
import pages.MainPage;
import client.Client;

import java.util.concurrent.TimeUnit;

public class ConstructorTest extends BrowserConfiguration {
    private Client client;


    @Before
    public void setUp() {
        driver.get(ClientSteps.baseURL);
        RestAssured.baseURI = ClientSteps.baseURL;
        client = ClientFaker.getRandomClient();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Тест на переход к разделу "Булки" на главной странице
    @Test
    @DisplayName("Переход к разделу Булки на главной странице")
    public void bunSectionTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSauceButton();
        mainPage.clickBunButton();
        String text = mainPage.getMenuTabLocator();
        Assert.assertEquals("Булки", text);
    }

    // Тест на переход к разделу "Соусы" на главной странице
    @Test
    @DisplayName("Переход к разделу Соусы на главной странице")
    public void sauceSectionTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickSauceButton();
        String text = mainPage.getMenuTabLocator();
        Assert.assertEquals("Соусы", text);
    }

    // Тест на переход к разделу "Начинки" на главной странице
    @Test
    @DisplayName("Переход к разделу Начинки на главной странице")
    public void fillingSectionTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickFillingButton();
        String text = mainPage.getMenuTabLocator();
        Assert.assertEquals("Начинки", text);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
