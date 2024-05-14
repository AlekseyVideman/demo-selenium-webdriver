package com.github.alekseyvideman.seleniumdemo;

import com.github.alekseyvideman.seleniumdemo.mapper.TransactionLogWebElementMapper;
import com.github.alekseyvideman.seleniumdemo.math.FibbonacciCalculator;
import com.github.alekseyvideman.seleniumdemo.pageobject.AccountPage;
import com.github.alekseyvideman.seleniumdemo.pageobject.LoginPage;
import com.github.alekseyvideman.seleniumdemo.pageobject.TransactionsPage;
import com.github.alekseyvideman.seleniumdemo.service.TransactionLogCsvReportingService;
import com.github.alekseyvideman.seleniumdemo.transaction.TransactionType;
import com.github.alekseyvideman.seleniumdemo.transaction.dto.TransactionLog;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//the code is dirty somewhere i know
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumWebDriverDemoTest {

    private static final String CUSTOMER_NAME = "Harry Potter";

    private WebDriver driver;
    private TransactionLogCsvReportingService logCsvReportingService;

    @BeforeEach
    public void setUp() throws URISyntaxException, MalformedURLException {
        var options = new FirefoxOptions();
            driver = new RemoteWebDriver(new URI("http://selenium-hub:4444").toURL(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(15));

        logCsvReportingService = new TransactionLogCsvReportingService();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Авторизоваться пользователем Harry Potter")
    public void shouldLoginAsHarryPotter() {
        var loginPage = new LoginPage(driver);

        loginPage.login(CUSTOMER_NAME);
        assertEquals(CUSTOMER_NAME, loginPage.getCustomerNameText());

        depositWithdrawStep();
        givenTransactionsPage_whenCountRows_then2Expected();
    }

    @Step("Снятие/Пополнение")
    public void depositWithdrawStep() {
        var accountPage = new AccountPage(driver);

        var calculator = new FibbonacciCalculator();
        var dayOfMonth = LocalDate.now().getDayOfMonth();
        int money = calculator.calc(dayOfMonth + 1);

        givenCustomerAccount_calcFibbSum_thenDeposit(money, accountPage);
        givenCustomerAccount_calcFibbSum_thenWithdraw(money, accountPage);
        givenCustomerAccount_expectZeroBalance(accountPage);
    }

    @Step("Выполнить Deposit на сумму {money}")
    public void givenCustomerAccount_calcFibbSum_thenDeposit(int money, AccountPage accountPage) {
        accountPage.deposit(money);

        assertEquals("Deposit Successful", accountPage.getTransactionStatus());
    }

    @Step("Выполнить Withdraw на сумму {money}")
    public void givenCustomerAccount_calcFibbSum_thenWithdraw(int money, AccountPage accountPage) {
        accountPage.withdraw(money);

        assertEquals("Transaction successful", accountPage.getTransactionStatus());
    }

    @Step("Баланс равен 0")
    public void givenCustomerAccount_expectZeroBalance(AccountPage accountPage) {
        assertEquals(0, accountPage.getBalance(),
                () -> String.format("Баланс равен: %s", accountPage.getBalance()));
    }

    @Step("Открыть страницу транзакций и проверить наличие обеих транзакций")
    @Description("Первая транзакция должна иметь тип CREDIT, вторая - DEBIT.")
    public void givenTransactionsPage_whenCountRows_then2Expected() {
        var transactionsPage = new TransactionsPage(driver, new TransactionLogWebElementMapper());
        var history = transactionsPage.getHistory();

        assertTrue(history.size() >= 2,
                () -> String.format("Количество транзакций: %s", history.size()));
        assertAll(
                () -> String.format("Тип первой и второй транзакции: %s и %s",
                        history.get(0).type(),
                        history.get(1).type()),
                () -> assertEquals(history.get(0).type(), TransactionType.CREDIT),
                () -> assertEquals(history.get(1).type(), TransactionType.DEBIT)
        );

        attachCsv(history);
    }

    @Attachment(value = "transactions", type = "text/plain", fileExtension = ".csv")
    private String attachCsv(List<TransactionLog> history) {
        return logCsvReportingService.generateCsv(history);
    }
}
