package com.hugeinc.gherkin;


import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


/**
 * Controller class for all Cucumber Selenium tests. This class execute All TestNgCucumberTests present in existing package.
 * <p/>
 * User: Atish Narlawar
 * This is the cucumber Test
 * Date: 11/4/13
 * Time: 10:58 AM
 */

/**
 * started cucumber option
 */

@CucumberOptions(features = "src/test/resources/features", tags = {"@ready" , "~@wip"}, format = {"html:target/cucumber-html-report", "json:target/cucumber-json-report.json"})
public class RunCukesTestIT extends AbstractTestNGCucumberTests {

}