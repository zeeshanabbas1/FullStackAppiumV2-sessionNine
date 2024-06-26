package com.qa.pages;

import com.qa.base.AppDriver;
import com.qa.base.AppFactory;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends AppFactory {
        public CartPage() {
            PageFactory.initElements(new AppiumFieldDecorator(AppDriver.getDriver()), this);
        }
        @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-CONTINUE SHOPPING\"]")
        private WebElement continueShoppingButton;

        @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]")
        private WebElement SLBackpackTitle;
        @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"$29.99\"]")
        private WebElement SLBackpackPrice;

        @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"YOUR CART\"]")
        private WebElement pageTitle;


        public String getProductTitle() {
        return getText(SLBackpackTitle, "Product Title in cart is: ");
    }
        public String getProductPrice() { return getText(SLBackpackPrice, "Product Price in cart is: "); }

    public String getTitle() {
        return getText(pageTitle, "Cart Page Title is: ");
    }

    public ProductsPage clickContinueShoppingButton() {
            waitForElementToBeClickable(continueShoppingButton);
            clickElement(continueShoppingButton, "Clicking on Continue Shopping Button");
            return new ProductsPage();
        }
}
