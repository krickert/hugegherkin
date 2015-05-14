package com.hugeinc.gherkin.api;

import com.hugeinc.gherkin.framework.BrowserType;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by krickert on 5/13/15.
 */
public interface SeleniumHelper {
    void resizeBrowserWindow(WebDriver driver, Integer browserWidth, Integer browserHeight);

    void resizeBrowser(BrowserType browserType);

    int getBrowserWidth(WebDriver driver);

    File getScreenShot(WebDriver driver, String filename) throws IOException;

    Object getAttributeValue(WebElement pageElement, String attributeName);

    List<WebElement> getTangibleElements();

    List<WebElement> getVisibleElements();

    String getElementXPath(WebElement element);

    Set<String> findBrokenImages();

    void openPage(String pagePath);

    WebElement findElementByCss(String cssString);

    WebElement findElementByTagName(String tagName);

    WebElement findElementByClassName(String className);

    WebElement findElementByXpath(String xpathExpression);

    WebElement findElementByLinkText(String linkText);

    WebElement findElementById(String id);

    WebElement findElementByCss(String cssString, WebElement webElement);

    WebElement findElementByClassName(String className, WebElement webElement);

    WebElement findElementById(String id, WebElement webElement);

    List<WebElement> findElementsByCss(String cssString);

    List<WebElement> findElementsByClassName(String className);

    List<WebElement> findElementsById(String id);

    List<WebElement> findElementsByXpath(String xpathExpression);

    void getScreenShot(String screenName);

    void start() throws Exception;

    void quit();

    String getCurrentUrl();

    void waitForCilckaleByCss(String cssString);

    void waitAnElementByCSS(String cssString);

    void maximizeWindow();

    void moveToElementAndClick(WebElement element);

    void moveToElement(WebElement element);

    void click(WebElement element);

    String getTitle();

    void resizeToWeb();

    void resizeToMobile();

    void resizeToPhantom();

    void resizeToTablet();

    String getWindowHandle();

    Set<String> getWindowHandles();

    void addCookie(String name, String value);

    void clearCookies();

    Set<Cookie> getCookies();

    void switchTo(String handle);

    void close();

    void navigateBack();

    Object executeScript(String script, WebElement webElement);

    WebElement findElementSafeCss(String cssString);

    WebElement findElementSafeXpath(String xpath);

    boolean exists(WebElement element);
}
