package com.hugeinc.gherkin.pages.pageload;

import com.hugeinc.gherkin.api.SeleniumAPI;
import com.hugeinc.gherkin.framework.AbstractSeleniumTest;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.log4testng.Logger;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: ajammula
 * Date: 3/7/14
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageloadsIT extends AbstractSeleniumTest {
    /**
     * The constant logger.
     */
    private static final Logger logger = Logger.getLogger(PageloadsIT.class);

    /**
     * The Selenium aPI.
     */
    @Autowired
    private SeleniumAPI seleniumAPI;

    /**
     * Enginetrim web element.
     *
     * @return the web element
     */
    private WebElement enginetrim() {
        return seleniumAPI.findElementByCss("div.details div.specifications-dropdown div.dropdown-heading div.dropdown-hit-area div.selected-trim span.trim-name");
    }

    /**
     * Images void.
     */
    private void images() {
        List<WebElement> images = seleniumAPI.findElementsByCss(".gallery-container");
        assertTrue("There are images displayed", images.size() > 1);
        WebElement gallery = seleniumAPI.findElementByClassName("homepage gallery");
        assertTrue(gallery.isDisplayed());

    }


    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before("@AllPagesLoadSuccessfully")
    public void setUp() throws Exception {
        logger.debug("Opens the home page");
        seleniumAPI.start();
    }

    /**
     * Tear down.
     */
    @After("@AllPagesLoadSuccessfully")
    public void tearDown() {
        seleniumAPI.quit();
    }

    /**
     * User _ is _ on _ home _ page.
     *
     * @throws Throwable the throwable
     */
    @Given("^User is on home page$")
    public void User_is_on_home_page() throws Throwable {
        seleniumAPI.openPage(getPageUrl("/"));
        Assert.assertEquals(true, seleniumAPI.findElementByClassName("homepage").isDisplayed());

    }

    /**
     * Sets viewport size.
     *
     * @param viewportSize the viewport size
     */
    @Then("^Ensure the Hero image is displayed properly on (Desktop|Tablet|Mobile)  view$")
    public void setViewportSize(String viewportSize) {
        if (viewportSize.equals("Mobile")) {
            seleniumAPI.resizeToMobile();
        } else if (viewportSize.equals("Tablet")) {
            seleniumAPI.resizeToTablet();
        } else if (viewportSize.equals("Desktop")) {
            seleniumAPI.resizeToWeb();
        }
        WebElement Heroimage = seleniumAPI.findElementByCss("html.js body#home div#wrapper div#content div.grid div#tile-0.tile");
        assertTrue(Heroimage.isDisplayed());

    }

    /**
     * Sets viewport size 1.
     *
     * @param viewportSize the viewport size
     */
    @Then("^Page is displayed properly on (desktop|tablet|mobile) viewport$")
    public void setViewportSize1(String viewportSize) {
        if (viewportSize.equals("desktop")) {
            seleniumAPI.resizeToWeb();
        } else if (viewportSize.equals("tablet")) {
            seleniumAPI.resizeToTablet();
        } else if (viewportSize.equals("mobile")) {
            seleniumAPI.resizeToMobile();
        }
        assertTrue(seleniumAPI.findElementByCss("div.grid-container div.nav-wrapper div.title").isDisplayed());

    }



    /**
     * Ensure _ specs _ section _ with _ modules _ is _ loaded.
     *
     * @throws Throwable the throwable
     */
    @Then("^Ensure Specs section with modules is loaded.$")
    public void Ensure_Specs_section_with_modules_is_loaded() throws Throwable {
        WebElement enginetrim = enginetrim();
        assertTrue(enginetrim.isDisplayed());
    }


    /**
     * Sets viewport size 3.
     *
     * @param viewportSize the viewport size
     */
    @And("^Specifications page should display properly in (Desktop|Tablet|Mobile) mode$")
    public void setViewportSize3(String viewportSize) {
        if (viewportSize.equals("Mobile")) {
            seleniumAPI.resizeToMobile();
        } else if (viewportSize.equals("Tablet")) {
            seleniumAPI.resizeToTablet();
        } else if (viewportSize.equals("Desktop")) {
            seleniumAPI.resizeToWeb();
        }
        WebElement enginetrim = enginetrim();
        assertTrue(enginetrim.isDisplayed());


    }

    /**
     * Sets viewport size 4.
     *
     * @param viewportSize the viewport size
     */
    @And("^Page should display properly in (Desktop|Tablet|Mobile) mode$")
    public void setViewportSize4(String viewportSize) {
        if (viewportSize.equals("Mobile")) {
            seleniumAPI.resizeToMobile();
        } else if (viewportSize.equals("Tablet")) {
            seleniumAPI.resizeToTablet();
        } else if (viewportSize.equals("Desktop")) {
            seleniumAPI.resizeToWeb();
        }
        seleniumAPI.openPage("/");
    }


    @When("^User clicks on features link in secondary navigation$")
    public void User_clicks_on_features_link_in_secondary_navigation() throws Throwable {
        //User clicks features tab in secondary nav
        WebElement secondarynavfeatures = seleniumAPI.findElementByXpath("//div[@class='sticky-wrapper']/nav/ul/li[3]/a");
        secondarynavfeatures.click();
        //By default the page lands on Keyfeatures
    }

    @Then("^Verify features page is displayed properly in (Desktop|Tablet|Mobile) mode$")
    public void setViewportSize10(String viewportSize) {
        if (viewportSize.equals("Mobile")) {
            seleniumAPI.resizeToMobile();
        } else if (viewportSize.equals("Tablet")) {
            seleniumAPI.resizeToTablet();
        } else if (viewportSize.equals("Desktop")) {
            seleniumAPI.resizeToWeb();
        }

        WebElement featureitem = seleniumAPI.findElementByCss("div.features-content div.feature-block div.feature-item");
        assertTrue(featureitem.isDisplayed());
        WebElement title = seleniumAPI.findElementByCss("div.features-content h1");
        assertTrue(title.getText().equalsIgnoreCase("key features"));

    }
}

