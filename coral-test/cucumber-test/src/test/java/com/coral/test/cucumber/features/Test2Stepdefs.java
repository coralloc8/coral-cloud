package com.coral.test.cucumber.features;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

/**
 * @author huss
 * @version 1.0
 * @className com.coral.test.cucumber.features.Test2Stepdefs
 * @description todo
 * @date 2023/3/2 18:30
 */
public class Test2Stepdefs {

    @Given("I am on the \"([^\"]*)\" page$")
    public boolean iAmOnThePage(String arg0) {
        System.out.println("#####我已经进入了" + arg0 + "页面");
        return true;
    }

    @And("^I click the \"([^\"]*)\" button$")
    public void iClickTheButton(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should go to the \"([^\"]*)\" page$")
    public void iShouldGoToThePage(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
