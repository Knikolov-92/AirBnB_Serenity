package com.qualityhouse.serenity.steps.definitions;

import com.qualityhouse.serenity.page_objects.HomePage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BookingStepDefinitions {

    HomePage homePage;

    @Given("^John is on the Home page$")
    public void johnIsOnTheHomePage() {

        homePage.open();
    }

    @And("^John submits booking options:$")
    public void johnSubmitsBookingOptions() {
    }

    @Then("^John should see the offers for the location$")
    public void johnShouldSeeTheOffersForTheLocation() {
    }

    @When("^John filters his preferences$")
    public void johnFiltersHisPreferences() {
    }

    @And("^John picks the first (\\d+)-star-place$")
    public void johnPicksTheFirstStarPlace(int arg0) {
    }

    @Then("^John should see the summary of the reservation$")
    public void johnShouldSeeTheSummaryOfTheReservation() {
    }
}
