package com.qualityhouse.serenity.steps.definitions;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.steps.libraries.BaseActions;
import com.qualityhouse.serenity.steps.libraries.BookingActions;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingStepDefinitions {

    private HomePage homePage;
    private OffersPage offersPage;

    @Steps
    private BookingActions yakim;
    @Steps
    private BaseActions bobi;

    @Given("^John is on the Home page$")
    public void johnIsOnTheHomePage() {

        homePage.open();
    }

    @When("^John submits booking options:$")
    public void johnSubmitsBookingOptions(DataTable inputData) throws InterruptedException {

        List<Map<String, String>> data = inputData.asMaps(String.class, String.class);
        String location = data.get(0).get("where");
        String adults = data.get(0).get("adults");
        String kids = data.get(0).get("kids");
        String babies = data.get(0).get("babies");

        yakim.entersReservationLocation(location);
        yakim.picksCheckInCheckOutDates();
        yakim.picksGuestsOptions(adults, kids, babies);
        bobi.clicksOn(homePage.bookingSearchButton);
    }

    @When("^John selects a currency \"([^\"]*)\"$")
    public void johnSelectsACurrency(String expectedCurrency) throws InterruptedException {

        yakim.picksCurrency(expectedCurrency);
    }

    @When("^John filters his preferences:$")
    public void johnFiltersHisPreferences(DataTable inputTable) throws InterruptedException {

        List<Map<String, String>> data = inputTable.asMaps(String.class, String.class);
        String priceMin = data.get(0).get("price_min");
        String priceMax = data.get(0).get("price_max");
        String numberOfBathrooms = data.get(0).get("bathroom");
        String airConditionerYesNo = data.get(0).get("air_conditioner");
        String hotTubeYesNo = data.get(0).get("hot_tube");

        yakim.entersPriceRange(priceMin, priceMax);
        yakim.choosesMoreFilterOptions(numberOfBathrooms, airConditionerYesNo, hotTubeYesNo);
    }

    @When("^John picks the first x-star-place: \"([^\"]*)\"$")
    public void johnPicksTheFirstXStarPlace(String stars) throws InterruptedException {

        String loopElement = "";
        List<WebElementFacade> listOfOfferStars = offersPage.findAll(OFFERS_STAR_VALUE_LOCATOR);
        List<WebElementFacade> listOfOffers = offersPage.findAll(OFFERS_COUNT_LIST_LOCATOR);
        for(int i = 0; i < listOfOfferStars.size(); i++) {

            loopElement = listOfOfferStars.get(i).getText().substring(0,4);
            System.out.println("looping through: " +loopElement);
            if(loopElement.equals(stars) ) {
                  bobi.clicksOn(listOfOffers.get(i));
                  break;
            }
        }
    }
    

    @Then("^John should see the summary of the reservation$")
    public void johnShouldSeeTheSummaryOfTheReservation() {
    }


    @Then("^John should see the offers for the location: \"([^\"]*)\"$")
    public void johnShouldSeeTheOffersForTheLocation(String expectedPlace) {

        String offersHeading = offersPage.offersHeadingInfoText.getText();
        assertThat(offersHeading).isEqualTo("Stays in " +expectedPlace);
        System.out.println(offersHeading);
    }



}
