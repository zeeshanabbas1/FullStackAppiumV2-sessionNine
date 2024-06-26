package com.qa.tests;

import com.qa.base.AppFactory;
import com.qa.pages.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Objects;

public class CartTest extends AppFactory {

        LoginPage loginPage;
        ProductsPage productsPage;
        InputStream inputStream;
        JSONObject loginUsers;
        SettingsPage settingsPage;
        CartPage cartPage;

        @BeforeClass
        public void setupDataStream() throws IOException {
            try {
                String dataFileName = "data/loginUsers.json";
                inputStream = getClass().getClassLoader().getResourceAsStream(dataFileName);
                JSONTokener jsonTokener = new JSONTokener(Objects.requireNonNull(inputStream));
                loginUsers = new JSONObject(jsonTokener);
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            closeApp();
            launchApp();
        }

        @BeforeMethod
        public void setup(Method method) {
            loginPage = new LoginPage();
            utilities.log().info("\n********** Starting Test: {} **********\n", method.getName());

            productsPage = loginPage.login(loginUsers.getJSONObject("validUserAndPassword").getString("userName"),
                    loginUsers.getJSONObject("validUserAndPassword").getString("password"));
        }

        @Test
        public void addProductToCart() {
            String actualProductTitle = productsPage.getTitle();
            String expectedProductTitle = stringHashMap.get("product_title");
            Assert.assertEquals(actualProductTitle, expectedProductTitle);

            productsPage.clickAddToCartButton();
            utilities.log().info("Product " + productsPage.getSLBackpackTitle() + " is added to the cart.");
            cartPage = productsPage.clickCartButton();

            // ************ Cart Page *********************//
            String actualCartPageTitle = cartPage.getTitle();
            String expectedCartPageTitle = stringHashMap.get("cart_title");
            Assert.assertEquals(actualCartPageTitle, expectedCartPageTitle);
            utilities.log().info("Actual Cart page title is - {}\nExpected Cart page title is - {}", actualCartPageTitle, expectedCartPageTitle);

        }
        @Test
        public void validateProductOnCartPage() {

            productsPage.clickAddToCartButton();
            utilities.log().info("Product " + productsPage.getSLBackpackTitle() + " is added to the cart.");
            cartPage = productsPage.clickCartButton();

            String SLBTitle = cartPage.getProductTitle();
            String SLBPrice = cartPage.getProductPrice();
            Assert.assertEquals(SLBTitle, stringHashMap.get("products_page_slb_title"));
            Assert.assertEquals(SLBPrice, stringHashMap.get("products_page_slb_price"));

            productsPage = cartPage.clickContinueShoppingButton();
            String actualProductTitle = productsPage.getTitle();
            String expectedProductTitle = stringHashMap.get("product_title");
            Assert.assertEquals(actualProductTitle, expectedProductTitle);
        }

    @AfterMethod
    public void tearDown() {
        settingsPage = productsPage.clickSettingsButton();
        loginPage = settingsPage.clickLogoutButton();
        }
    }

