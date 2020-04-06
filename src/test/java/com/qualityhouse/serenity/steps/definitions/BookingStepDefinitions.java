package com.qualityhouse.serenity.steps.definitions;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.steps.libraries.BookingActions;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

public class BookingStepDefinitions {

    HomePage homePage;

    @Steps
    private BookingActions yakim;

    @Given("^John is on the Home page$")
    public void johnIsOnTheHomePage() {

        homePage.open();
    }

    @And("^John submits booking options:$")
    public void johnSubmitsBookingOptions(DataTable inputData) throws InterruptedException {

        List<Map<String, String>> data = inputData.asMaps(String.class, String.class);
        String location = data.get(0).get("where");

        yakim.entersReservationLocation(location);
        yakim.picksCheckInCheckOutDate();
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
