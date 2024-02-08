package com.finki.ukim.selenium;

import com.finki.ukim.CoffeeAppApplication;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CoffeeControllerSeleniumTest {

    private WebDriver driver;
    private static ApplicationRunner applicationRunner;
    private static ConfigurableApplicationContext applicationContext;
    private static ApplicationArguments applicationArguments;

    @BeforeAll
    public static void setupApplicationContext() throws Exception {
        applicationArguments = new DefaultApplicationArguments(new String[]{});
        applicationRunner = args -> {
            applicationContext = SpringApplication.run(CoffeeAppApplication.class, args.getSourceArgs());
        };
        applicationRunner.run(applicationArguments);
    }

    @BeforeEach
    public void init() {


        System.setProperty("webdriver.gecko.driver", "./src/main/resources/driver/geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void destroy() {
        if (driver != null) {
            driver.quit();
        }

    }


    @AfterAll
    public static void closeApp() {
        if (applicationContext != null) {
            SpringApplication.exit(applicationContext, () -> 0);
        }
    }


    @Test
    public void addNewCoffeeTest(){

        driver.get("http://localhost:8080/");
        WebElement addNewCoffeeButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-secondary']"));
        addNewCoffeeButton.click();

        driver.get("http://localhost:8080/showNewCoffeeForm");

        WebElement coffeeName = driver.findElement(By.id("name"));
        coffeeName.sendKeys("Oatmilk Latte Test");
        WebElement coffeeSize = driver.findElement(By.id("size"));
        coffeeSize.sendKeys("Medium");
        WebElement coffeePrice = driver.findElement(By.id("price"));
        coffeePrice.clear();
        coffeePrice.sendKeys("7");
        WebElement coffeeCaffeine = driver.findElement(By.id("caffeine"));
        coffeeCaffeine.click();

        WebElement addCoffeeButton = driver.findElement(By.cssSelector("button[type='submit']"));
        addCoffeeButton.click();
        driver.get("http://localhost:8080/");

        WebElement addedCoffeeName = driver.findElement(By.xpath("//td[contains(text(), 'Oatmilk Latte Test')]"));
        assertEquals("Oatmilk Latte Test", addedCoffeeName.getText());
        WebElement addedCoffeeSize = driver.findElement(By.xpath("//td[contains(text(), 'MEDIUM')]"));
        assertEquals("MEDIUM", addedCoffeeSize.getText());
        WebElement addedCoffeePrice = driver.findElement(By.xpath("//td[contains(text(), '7.0')]"));
        assertEquals("7.0", addedCoffeePrice.getText());
        WebElement addedCoffeeCaffeine = driver.findElement(By.xpath("//td[contains(text(), 'true')]"));
        assertEquals("true", addedCoffeeCaffeine.getText());

    }

    @Test
    public void deleteCoffeeTest(){

        driver.get("http://localhost:8080/");
        int numRows = driver.findElements(By.cssSelector("body > div > table > tbody > tr")).size();

        if (numRows == 0){

            WebElement addNewCoffeeButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-secondary']"));
            addNewCoffeeButton.click();
            driver.get("http://localhost:8080/showNewCoffeeForm");

            WebElement coffeeName = driver.findElement(By.id("name"));
            coffeeName.sendKeys("Oatmilk Latte Test");
            WebElement coffeeSize = driver.findElement(By.id("size"));
            coffeeSize.sendKeys("Medium");
            WebElement coffeePrice = driver.findElement(By.id("price"));
            coffeePrice.clear();
            coffeePrice.sendKeys("7");
            WebElement coffeeCaffeine = driver.findElement(By.id("caffeine"));
            coffeeCaffeine.click();

            WebElement addCoffeeButton = driver.findElement(By.cssSelector("button[type='submit']"));
            addCoffeeButton.click();
            driver.get("http://localhost:8080/");
            numRows++;
        }

        //se zema info na prvoto kafe u tabelata
        WebElement firstRow = driver.findElement(By.cssSelector("body > div > table > tbody > tr:first-child"));
        List<WebElement> tdElements = firstRow.findElements(By.tagName("td"));
        String coffeeName = tdElements.get(0).getText();
        String coffeeSize = tdElements.get(1).getText();
        String coffeePrice = tdElements.get(2).getText();
        String coffeeCaffeine = tdElements.get(3).getText();

        //se brise kafeto
        WebElement deleteCoffeeButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-danger']"));
        deleteCoffeeButton.click();
        driver.get("http://localhost:8080/");

        //se proveruva dali kafeto e uste vo tabelata, flag e true ako uste stoe kafeto vo tabelata
        List<WebElement> rows = driver.findElements(By.cssSelector("body > div > table > tbody > tr"));
        boolean flag = false;
        for(WebElement row : rows){
            List<WebElement> tdElementsRow = row.findElements(By.tagName("td"));

            if (tdElementsRow.size() >= 4 &&
                    tdElementsRow.get(0).getText().equals(coffeeName) &&
                    tdElementsRow.get(1).getText().equals(coffeeSize) &&
                    tdElementsRow.get(2).getText().equals(coffeePrice) &&
                    tdElementsRow.get(3).getText().equals(coffeeCaffeine)){
                flag = true;
            }
        }

        //ako flag e false-ako kafeto ne e vo tabelata, proveri dali brojot na redovi-1 e ist so brojot na redovi na krajot
        int finalNumRows = driver.findElements(By.cssSelector("body > div > table > tbody > tr")).size();
        if (flag == false){
            assertEquals(numRows-1, finalNumRows);
        }

    }

    @Test
    public void editCoffeeTest(){

        driver.get("http://localhost:8080/");
        int numRows = driver.findElements(By.cssSelector("body > div > table > tbody > tr")).size();

        if (numRows == 0){
            WebElement addNewCoffeeButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-secondary']"));
            addNewCoffeeButton.click();
            driver.get("http://localhost:8080/showNewCoffeeForm");

            WebElement coffeeName = driver.findElement(By.id("name"));
            coffeeName.sendKeys("Oatmilk Latte Test");
            WebElement coffeeSize = driver.findElement(By.id("size"));
            coffeeSize.sendKeys("Medium");
            WebElement coffeePrice = driver.findElement(By.id("price"));
            coffeePrice.clear();
            coffeePrice.sendKeys("7");
            WebElement coffeeCaffeine = driver.findElement(By.id("caffeine"));
            coffeeCaffeine.click();

            WebElement addCoffeeButton = driver.findElement(By.cssSelector("button[type='submit']"));
            addCoffeeButton.click();
            driver.get("http://localhost:8080/");
        }

        //se izbira update
        WebElement editCoffeeButton = driver.findElement(By.cssSelector("a[class='btn btn-outline-primary']"));
        editCoffeeButton.click();
        driver.get("http://localhost:8080/showFormForUpdate/1");

        //se menuvaat podatocite za kafeto vo UPDATE formata
        WebElement coffeeNameEdit = driver.findElement(By.id("name"));
        coffeeNameEdit.clear();
        coffeeNameEdit.sendKeys("editedName");

        WebElement coffeeSizeEdit = driver.findElement(By.id("size"));
        coffeeSizeEdit.sendKeys("Large");

        WebElement coffeePriceEdit = driver.findElement(By.id("price"));
        coffeePriceEdit.clear();
        coffeePriceEdit.sendKeys("10");


        //se klika kopceto za update
        WebElement updateCoffeeButton = driver.findElement(By.cssSelector("button[type='submit']"));
        updateCoffeeButton.click();
        driver.get("http://localhost:8080/");


        //sega na pocetnata strana se proveruva dali navistina se izmenil elementot
        WebElement updatedName = driver.findElement(By.xpath("//td[contains(text(), 'editedName')]"));
        assertTrue(updatedName.isDisplayed());
        WebElement updatedSize = driver.findElement(By.xpath("//td[contains(text(), 'LARGE')]"));
        assertTrue(updatedSize.isDisplayed());
        WebElement updatedPrice = driver.findElement(By.xpath("//td[contains(text(), '10')]"));
        assertTrue(updatedPrice.isDisplayed());

    }



}
