package com.github.alekseyvideman.seleniumdemo;

import com.github.alekseyvideman.seleniumdemo.mapper.TransactionLogWebElementMapper;
import com.github.alekseyvideman.seleniumdemo.math.FibbonacciCalculator;
import com.github.alekseyvideman.seleniumdemo.pageobject.AccountPage;
import com.github.alekseyvideman.seleniumdemo.pageobject.LoginPage;
import com.github.alekseyvideman.seleniumdemo.pageobject.TransactionsPage;
import com.github.alekseyvideman.seleniumdemo.transaction.TransactionType;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//the code is dirty somewhere i know
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumWebDriverDemoTest {

    private static final String CUSTOMER_NAME = "Harry Potter";
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() throws URISyntaxException, MalformedURLException {
        var options = new FirefoxOptions();
        driver = new RemoteWebDriver(new URI("http://selenium-hub:4444").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    @Disabled
    public void calcFibbonacci() {
        var calculator = new FibbonacciCalculator();

        assertEquals(34, calculator.calc(9));
        assertEquals(55, calculator.calc(10));
        assertEquals(377, calculator.calc(14));
    }

    @Test
    @Order(1)
    @DisplayName("Авторизоваться пользователем Harry Potter")
    public void shouldLoginAsHarryPotter(){
        var loginPage = new LoginPage(driver);
        loginPage.login(CUSTOMER_NAME);

        assertEquals(CUSTOMER_NAME, loginPage.getCustomerNameText());
    }

    @Test
    @DisplayName("Выполнить Deposit на сумму из п.4")
    public void givenCustomerAccount_calcFibbSum_thenDeposit() {
        var calculator = new FibbonacciCalculator();
        var dayOfMonth = LocalDate.now().getDayOfMonth();
        var accountPage = new AccountPage(driver);
        accountPage.deposit(calculator.calc(dayOfMonth + 1));

        assertEquals("Deposit Successful", accountPage.getTransactionStatus());
    }

    @Test
    @DisplayName("Выполнить Withdraw на сумму из п.4")
    public void givenCustomerAccount_calcFibbSum_thenWithdraw() {
        var calculator = new FibbonacciCalculator();
        var dayOfMonth = LocalDate.now().getDayOfMonth();
        var accountPage = new AccountPage(driver);
        accountPage.withdraw(calculator.calc(dayOfMonth + 1));

        assertEquals("Transaction successful", accountPage.getTransactionStatus());
    }

    @Test
    @DisplayName("Баланс равен 0")
    public void balanceShouldEqualsZero() {
        var accountPage = new AccountPage(driver);

        assertEquals(0, accountPage.getBalance());
    }

    @Test
    @DisplayName("Открыть страницу транзакций и проверить наличие обеих транзакций")
    public void givenTransactionsPage_whenCountRows_then2Expected() throws IOException {
        var transactionsPage = new TransactionsPage(driver, new TransactionLogWebElementMapper());
        var history = transactionsPage.getHistory();

        assertTrue(history.size() >= 2);
        assertEquals(history.get(0).type(), TransactionType.CREDIT);
        assertEquals(history.get(1).type(), TransactionType.DEBIT);

        transactionsPage.printCsv(history);
        Allure.addAttachment("transactions-report.csv", Files.readString(Path.of("transactions-report.csv")));
    }

}
