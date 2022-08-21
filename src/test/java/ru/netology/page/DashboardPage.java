package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private ElementsCollection cardsButtons = $$("[data-test-id='action-deposit']");
    private SelenideElement ErrorNotification = $("[data-test-id='error-notification']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    private SelenideElement head = $("[data-test-id='dashboard']");

    public DashboardPage() {
        head.shouldBe(visible);
    }

    public int getCardBalance(int id) {
        val text = cards.get(id).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage chooseCardForTransfer(int id) {
        cardsButtons.get(id).click();
        return new TransferPage();
    }

    public void getErrorNotification() {
        ErrorNotification.shouldBe(visible);
    }
}