package org.example.pages;

import org.example.helpers.WebUI;
import org.openqa.selenium.By;

public class SecureAreaPage {
    private final By buttonLogout = By.xpath("a//[contains(@href, 'logout')");
    private final By headerTitle = By.cssSelector("div[class='example'] h2");

    public void clickLogout() {
        WebUI.clickElement(buttonLogout);
    }

    public String getPageTitle() {
        return WebUI.getElementText(headerTitle);
    }
}
