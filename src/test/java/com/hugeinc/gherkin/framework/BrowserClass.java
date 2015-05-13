package com.hugeinc.gherkin.framework;

import com.google.common.collect.Maps;
import org.openqa.selenium.Dimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * User: krickert
 * Date: 11/14/13
 * Time: 10:44 AM
 */
@Component
public class BrowserClass {

    private final Map<BrowserType, Dimension> browserClassMap = Maps.newHashMapWithExpectedSize(5);

    /**
     * Example: =1204,1024
     */
    @Autowired
    public BrowserClass(@Value("browser.web") final List<Integer> browserWebProperty,
                        @Value("browser.tablet") final List<Integer> browserTabletProperty,
                        @Value("browser.mobile") final List<Integer> browserMobileProperty,
                        @Value("browser.phantom") final List<Integer> browserPhantomProperty) {
        browserClassMap.put(BrowserType.WEB, new Dimension(browserWebProperty.get(0), browserWebProperty.get(1)));
        browserClassMap.put(BrowserType.TABLET, new Dimension(browserTabletProperty.get(0), browserTabletProperty.get(1)));
        browserClassMap.put(BrowserType.MOBILE, new Dimension(browserMobileProperty.get(0), browserMobileProperty.get(1)));
        browserClassMap.put(BrowserType.PHANTOM, new Dimension(browserPhantomProperty.get(0), browserPhantomProperty.get(1)));

    }
    /**
     * Gets dimension.
     *
     * @return the dimension
     */
    public Dimension getDimension(BrowserType browserType) {
        return browserClassMap.get(browserType);
    }


}