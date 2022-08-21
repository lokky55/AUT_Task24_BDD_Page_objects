package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferWithLimitTest {

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
    void ShouldTryTransferMoneyFromCardWhenLimitExceeded() {
        var dashboardPage = new DashboardPage();
        var transferPage = new TransferPage();
        var firstCard = DataHelper.getFirstCardInfo();
        var secondCard = DataHelper.getSecondCardInfo();
        dashboardPage.chooseCardForTransfer(firstCard.getId());
        int transferAmount = 40_000;
        transferPage.transferOperation(transferAmount, secondCard.getCardNumber());
        dashboardPage.getErrorNotification();  // сообщение не появляется - баг!
    }
}
