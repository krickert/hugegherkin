package com.hugeinc.gherkin.framework;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
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
    public BrowserClass(@Value("${browser.web}") final String browserWebPropertyStr,
                        @Value("${browser.tablet}") final String browserTabletPropertyStr,
                        @Value("${browser.mobile}") final String browserMobilePropertyStr,
                        @Value("${browser.phantom}") final String browserPhantomPropertyStr) {
        List<Integer> browserWebProperty = stringToInts(browserWebPropertyStr);
        List<Integer> browserTabletProperty = stringToInts(browserTabletPropertyStr);
        List<Integer> browserMobileProperty = stringToInts(browserMobilePropertyStr);
        List<Integer> browserPhantomProperty = stringToInts(browserPhantomPropertyStr);


        browserClassMap.put(BrowserType.WEB, new Dimension(browserWebProperty.get(0), browserWebProperty.get(1)));
        browserClassMap.put(BrowserType.TABLET, new Dimension(browserTabletProperty.get(0), browserTabletProperty.get(1)));
        browserClassMap.put(BrowserType.MOBILE, new Dimension(browserMobileProperty.get(0), browserMobileProperty.get(1)));
        browserClassMap.put(BrowserType.PHANTOM, new Dimension(browserPhantomProperty.get(0), browserPhantomProperty.get(1)));

    }

    private List<Integer> stringToInts(String stringOfInts) {
        String[] futureIntsOfAmerica = stringOfInts.split(",");
        List<Integer> integersToReturn = Lists.newArrayList();
        for(String stringToInt : futureIntsOfAmerica) {
            integersToReturn.add(Integer.parseInt(stringToInt));
        }
        return integersToReturn;
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