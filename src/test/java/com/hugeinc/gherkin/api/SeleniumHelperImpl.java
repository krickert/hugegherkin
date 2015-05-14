package com.hugeinc.gherkin.api;


import com.hugeinc.gherkin.framework.BrowserClass;
import com.hugeinc.gherkin.framework.BrowserType;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Selenium Utility class provides all the common usable functionality in Selenium testing.
 */
@Component
public class SeleniumHelperImpl implements SeleniumHelper {

    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final String pathToScreenshot;
    private final String relativeScreenshotPath;
    private final BrowserClass browserClass;

    private static final Logger logger = Logger.getLogger(SeleniumHelperImpl.class);

    @Autowired
    public SeleniumHelperImpl( final BrowserClass browserClass,
                               final WebDriver driver,
                               final WebDriverWait webDriverWait,
                               final @Value("${server.screenshot.path}") String pathToScreenshot,
                               final @Value("${server.screenshot.relative.path}") String relativeScreenshotPath) {
        this.driver = driver;
        this.browserClass = browserClass;
        this.pathToScreenshot = pathToScreenshot;
        this.relativeScreenshotPath = relativeScreenshotPath;
        this.webDriverWait = webDriverWait;
    }

    /**
     * Resize browser window.
     *
     * @param driver        the driver
     * @param browserWidth  the browser width
     * @param browserHeight the browser height
     */
    public void resizeBrowserWindow(WebDriver driver, Integer browserWidth, Integer browserHeight) {
        driver.manage().window().setSize(new Dimension(browserWidth, browserHeight));
    }

    /**
     * Resize browser.
     *
     * @param browserType the browser type enum
     */
    public void resizeBrowser(BrowserType browserType) {
        driver.manage().window().setSize(browserClass.getDimension(browserType));
    }

    /**
     * Gets browser width.
     *
     * @param driver the driver
     *
     * @return the browser width
     */
    public int getBrowserWidth(WebDriver driver) {
        Dimension d = driver.manage().window().getSize();
        return d.getWidth();
    }


    /**
     * Create screen shot dir.
     *
     * @throws IOException the iO exception
     */
    private void createScreenShotDir() throws IOException {
        FileUtils.forceMkdir(new File(pathToScreenshot + "/target/screenshots/"));
    }

    /**
     * Gets screen shot.
     *
     * @param driver   the driver
     * @param filename the filename
     *
     * @return the screen shot
     *
     * @throws IOException the iO exception
     */
    public File getScreenShot(WebDriver driver, String filename) throws IOException {
        File imageFile = null;
        try {
            createScreenShotDir();

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String path = pathToScreenshot + relativeScreenshotPath + filename + scrFile.getName();
            imageFile = new File(path);
            FileUtils.copyFile(scrFile, imageFile);
            logger.debug("We are taking screenshots!");
        } catch (Exception ex) {
            // Any exception in Screen shot should be eaten here. It should not hamper selenium tests.
            logger.debug("Something went wrong getting a screenshots");
        }
        return imageFile;
    }

    /**
     * Gets attribute value.
     *
     * @param pageElement   the page element
     * @param attributeName the attribute name
     *
     * @return the attribute value
     */
    public Object getAttributeValue(WebElement pageElement, String attributeName) {
        if (attributeName.equals("isVisible")) {
            return pageElement.isDisplayed();
        } else if (attributeName.equals("width")) {
            return pageElement.getSize().getWidth();
        } else if (attributeName.equals("color")) {
            return pageElement.getCssValue("color");
        } else if (attributeName.equals("height")) {
            return pageElement.getSize().getHeight();
        } else if (attributeName.equals("width")) {
            return pageElement.getSize().getWidth();
        } else if (attributeName.equals("posX")) {
            return pageElement.getLocation().getX();
        } else if (attributeName.equals("posY")) {
            return pageElement.getLocation().getY();
        } else if (attributeName.equals("opacity")) {
            return pageElement.getCssValue("opacity");
        } else if (attributeName.equals("borderWidth")) {
            return pageElement.getCssValue("border-width");
        } else if (attributeName.equals("borderColor")) {
            return pageElement.getCssValue("border-color");
        } else if (attributeName.equals("src")) {
            return pageElement.getAttribute("src");
        } else if (attributeName.equals("alt")) {
            return pageElement.getAttribute("alt");
        } else if (attributeName.equals("liCount")) {
            return pageElement.findElements(By.tagName("li")).size();
        } else if (attributeName.equals("href")) {
            return pageElement.getAttribute("href");
        } else {
            return "";
        }
    }

    /**
     * Gets tangible elements.
     *
     * @return the tangible elements
     */
    public List<WebElement> getTangibleElements() {
        List<WebElement> visibleElements = getVisibleElements();
        List<WebElement> tangibleElements = new LinkedList<WebElement>();
        for (WebElement pageElement : visibleElements) {
            if (!pageElement.getTagName().equals("html") && !pageElement.getTagName().equals("body") && !pageElement.getTagName().equals("nav") && !pageElement.getTagName().equals("ul")) {
                if (pageElement.getSize().getWidth() > 0 && pageElement.getSize().getHeight() > 0) {
                    tangibleElements.add(pageElement);
                }
            }

        }
        return tangibleElements;
    }

    /**
     * Gets visible elements.
     *
     * @return the visible elements
     */
    public List<WebElement> getVisibleElements() {
        List<WebElement> pageElements = driver.findElements(By.xpath("//*"));
        List<WebElement> visibleElements = new LinkedList<WebElement>();
        for (WebElement pageElement : pageElements) {
            if (pageElement.isDisplayed()) {
                visibleElements.add(pageElement);
            }
        }

        return visibleElements;
    }

    /**
     * Gets element x path.
     *
     * @param element the element
     *
     * @return the element x path
     */
    public String getElementXPath(WebElement element) {
        String elementClass = element.getAttribute("class");
        String elementId = element.getAttribute("id");
        String elementText = element.getText();
        String elementTag = element.getTagName();
        Boolean elementXPathComma = false;
        String elementXPath;
        if (elementClass.contains("section")) {
            elementXPath = "//" + elementTag;
        } else {
            elementXPath = getElementXPath(element.findElement(By.xpath("/parent::*"))) + "/" + elementTag;
        }
        if (!elementClass.equals("") || !elementId.equals("") || !elementText.equals("")) {
            elementXPath = elementXPath + "[";
            if (!elementClass.equals("")) {
                elementXPath = elementXPath + "@class='" + elementClass + "'";
                elementXPathComma = true;
            }
            if (!elementId.equals("")) {
                if (elementXPathComma) {
                    elementXPath = elementXPath + " and ";
                }
                elementXPath = elementXPath + "@id='" + elementClass + "'";
                elementXPathComma = true;
            }
            if (!elementText.equals("") && !elementXPathComma) {
                elementXPath = elementXPath + "text()='" + elementText + "'";
            }
            elementXPath = elementXPath + "]";
        }
        return elementXPath;

    }

    /**
     * Find broken images.
     *
     * @return the set
     */
    public Set<String> findBrokenImages() {
        List<WebElement> imagesList = driver.findElements(By.tagName("img"));
        Set<String> brokenImages = new LinkedHashSet<String>();
        for (WebElement image : imagesList) {
            try {
                HttpClient httpclient = HttpClientBuilder.create().build();
                HttpResponse response = httpclient.execute(new HttpGet(image.getAttribute("src")));
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    brokenImages.add(image.getAttribute("src"));
                }
            } catch (IOException ioe) {
                logger.warn("Bad image " + image);
            }
        }
        return brokenImages;
    }


    /**
     * Open page.
     *
     * @param pagePath the page path
     */
    public void openPage(String pagePath) {
        driver.get(pagePath);
    }

    /**
     * Find element by css.
     *
     * @param cssString the css string
     *
     * @return the web element
     */
    public WebElement findElementByCss(String cssString) {
        return driver.findElement(By.cssSelector(cssString));

    }

    /**
     * Find element by tag name.
     *
     * @param tagName the tag name
     *
     * @return the web element
     */
    public WebElement findElementByTagName(String tagName) {
        return driver.findElement(By.tagName(tagName));
    }

    /**
     * Find element by class name.
     *
     * @param className the class name
     *
     * @return the web element
     */
    public WebElement findElementByClassName(String className) {
        return driver.findElement(By.className(className));

    }

    /**
     * Find element by xpath.
     *
     * @param xpathExpression the xpath expression
     *
     * @return the web element
     */
    public WebElement findElementByXpath(String xpathExpression) {
        return driver.findElement(By.xpath(xpathExpression));

    }

    /**
     * Find element by link text.
     *
     * @param linkText the link text
     *
     * @return the web element
     */
    public WebElement findElementByLinkText(String linkText) {
        return driver.findElement(By.linkText(linkText));

    }

    /**
     * Find element by id.
     *
     * @param id the id
     *
     * @return the web element
     */
    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));

    }

    /**
     * Find element by css.
     *
     * @param cssString  the css string
     * @param webElement the web element
     *
     * @return the web element
     */
    public WebElement findElementByCss(String cssString, WebElement webElement) {
        if (webElement != null) {
            return webElement.findElement(By.cssSelector(cssString));
        }
        return null;
    }

    /**
     * Find element by class name.
     *
     * @param className  the class name
     * @param webElement the web element
     *
     * @return the web element
     */
    public WebElement findElementByClassName(String className, WebElement webElement) {
        if (webElement != null) {
            return webElement.findElement(By.className(className));
        }
        return null;
    }

    /**
     * Find element by id.
     *
     * @param id         the id
     * @param webElement the web element
     *
     * @return the web element
     */
    public WebElement findElementById(String id, WebElement webElement) {
        if (webElement != null) {
            return webElement.findElement(By.id(id));
        }
        return null;
    }

    /**
     * Find elements by css.
     *
     * @param cssString the css string
     *
     * @return the list
     */
    public List<WebElement> findElementsByCss(String cssString) {
        return driver.findElements(By.cssSelector(cssString));

    }

    /**
     * Find elements by class name.
     *
     * @param className the class name
     *
     * @return the list
     */
    public List<WebElement> findElementsByClassName(String className) {
        return driver.findElements(By.className(className));

    }

    /**
     * Find elements by id.
     *
     * @param id the id
     *
     * @return the list
     */
    public List<WebElement> findElementsById(String id) {
        return driver.findElements(By.id(id));
    }

    /**
     * Find elements by xpath.
     *
     * @param xpathExpression the xpath expression
     *
     * @return the list
     */
    public List<WebElement> findElementsByXpath(String xpathExpression) {
        return driver.findElements(By.xpath(xpathExpression));

    }

    /**
     * Gets screen shot.
     *
     * @param screenName the screen name
     */
    public void getScreenShot(String screenName) {
        try {
            getScreenShot(driver, screenName);
        } catch (Exception ex) {
            logger.error("Screen Shot functionality not working. Missing screen name " + screenName);
        }
    }

    /**
     * Start void.
     *
     * @throws Exception the exception
     */
    public void start() throws Exception {
        if (driver != null) {
            quit();
        }

    }

    /**
     * Quit void.
     */
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Gets current url.
     *
     * @return the current url
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Wait for cilckale by css.
     *
     * @param cssString the css string
     */
    public void waitForCilckaleByCss(String cssString) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssString)));

    }

    /**
     * Waits for a CSS element to come up
     * @param cssString string to find from a cssSelector
     */
    public void waitAnElementByCSS(String cssString) {
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssString)));
        driver.findElements(By.cssSelector(cssString));

    }

    /**
     * Maximize window.
     */
    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    /**
     * Move to element and click.
     *
     * @param element the element
     */
    public void moveToElementAndClick(WebElement element) {
        new Actions(driver).moveToElement(element).click().build().perform();
    }

    /**
     * Move to element.
     *
     * @param element the element
     */
    public void moveToElement(WebElement element) {
        new Actions(driver).moveToElement(element).build().perform();
    }

    /**
     * Click void.
     *
     * @param element the element
     */
    public void click(WebElement element) {
        WebElement webElement = webDriverWait.until(ExpectedConditions.visibilityOf(element));
        if (webElement != null) {
            webElement.click();
        } else {
            assertNotNull("Element for click not found");
        }
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * Resize to web.
     */
    public void resizeToWeb() {
        resizeBrowser( BrowserType.WEB);
    }

    /**
     * Resize to mobile.
     */
    public void resizeToMobile() {
        resizeBrowser( BrowserType.MOBILE);
    }

    /**
     * Resize to mobile.
     */
    public void resizeToPhantom() {
        resizeBrowser( BrowserType.PHANTOM);
    }

    /**
     * Resize to tablet.
     */
    public void resizeToTablet() {
        resizeBrowser(BrowserType.TABLET);
    }

    /**
     * Gets window handle.
     *
     * @return the window handle
     */
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }


    /**
     * Gets window handles.
     *
     * @return the window handles
     */
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }


    /**
     * Add cookie.
     *
     * @param name  the name
     * @param value the value
     */
    public void addCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
    }

    /**
     * Clear cookies.
     */
    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    /**
     * Gets cookies.
     *
     * @return the cookies
     */
    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    /**
     * Switch to.
     *
     * @param handle the handle
     */
    public void switchTo(String handle) {
        driver.switchTo().window(handle);
    }

    /**
     * Close void.
     */
    public void close() {
        driver.close();
    }

    /**
     * Navigate back.
     */
    public void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Execute script.
     *
     * @param script     the script
     * @param webElement the web element
     *
     * @return the object
     */
    public Object executeScript(String script, WebElement webElement) {
        return ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && " +
                        "typeof arguments[0].naturalWidth != \"undefined\" && " +
                        "arguments[0].naturalWidth > 0", webElement);
    }

    public WebElement findElementSafeCss(String cssString) {
        try {
            return driver.findElement(By.cssSelector(cssString));
        } catch (NoSuchElementException e) {
            return null;
        }
    }


    public WebElement findElementSafeXpath(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean exists(WebElement element) {
        return element != null;
    }


}
