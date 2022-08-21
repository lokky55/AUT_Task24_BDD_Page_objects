package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondCard() {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();
        int firstCardOldBalance = dashboardPage.getCardBalance(firstCard.getId());
        int secondCardOldBalance = dashboardPage.getCardBalance(secondCard.getId());
        dashboardPage.chooseCardForTransfer(secondCard.getId());
        int transferAmount = 100;
        transferPage.transferOperation(transferAmount, firstCard.getCardNumber());
        int firstCardNewBalance = dashboardPage.getCardBalance(firstCard.getId());
        int secondCardNewBalance = dashboardPage.getCardBalance(secondCard.getId());
        assertEquals(firstCardNewBalance, firstCardOldBalance - transferAmount);
        assertEquals(secondCardNewBalance, secondCardOldBalance + transferAmount);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstCard() {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();
        int firstCardOldBalance = dashboardPage.getCardBalance(firstCard.getId());
        int secondCardOldBalance = dashboardPage.getCardBalance(secondCard.getId());
        dashboardPage.chooseCardForTransfer(firstCard.getId());
        int transferAmount = 100;
        transferPage.transferOperation(transferAmount, secondCard.getCardNumber());
        int firstCardNewBalance = dashboardPage.getCardBalance(firstCard.getId());
        int secondCardNewBalance = dashboardPage.getCardBalance(secondCard.getId());
        assertEquals(firstCardNewBalance, firstCardOldBalance + transferAmount);
        assertEquals(secondCardNewBalance, secondCardOldBalance - transferAmount);
    }
}