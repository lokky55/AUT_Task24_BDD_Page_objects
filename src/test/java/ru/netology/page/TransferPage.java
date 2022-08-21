package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id='dashboard'].heading").shouldHave(text("Личный кабинет"));
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage transferOperation(int amount, String from) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(from);
        transferButton.click();
        return new DashboardPage();
    }
}
