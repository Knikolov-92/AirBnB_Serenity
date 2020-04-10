package com.qualityhouse.serenity.steps.definitions;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.steps.libraries.BaseActions;
import com.qualityhouse.serenity.steps.libraries.BookingActions;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;

import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingStepDefinitions {

    private HomePage homePage;
    private OffersPage offersPage;
    private int sumTotalGuests;
    private String offerPrice;
    private WebDriver driver = getDriver();
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
        String checkIn = data.get(0).get("check-In_in");
        String checkOut = data.get(0).get("check-Out_in");
        String adults = data.get(0).get("adults");
        String kids = data.get(0).get("kids");
        String babies = data.get(0).get("babies");

        this.sumTotalGuests = Integer.parseInt(adults) + Integer.parseInt(kids) + Integer.parseInt(babies);

        yakim.entersReservationLocation(location);
        yakim.picksCheckInCheckOutDates(checkIn, checkOut);
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

        Actions actions = new Actions(driver);
        String loopElement = "";
        List<WebElementFacade> listOfStarsPrices = offersPage.findAll(OFFERS_STAR_PRICE_LOCATOR);

        for(int i = 0; i < listOfStarsPrices.size(); i++) {

            loopElement = listOfStarsPrices.get(i).getText().trim().substring(0,3);
            System.out.println("Element(" +i +") =" +loopElement);

                if (loopElement.equals(stars.trim()) ) {

                    bobi.movesPointerToElement(listOfStarsPrices.get(i + 2));
                    this.offerPrice = listOfStarsPrices.get(i + 1).getText().substring(0,5).trim();
                    System.out.println(this.offerPrice);
                    bobi.movesPointerToElement(listOfStarsPrices.get(i));
                    actions.click().perform();
                    break;
                }
        }
    }

    @Then("^John should see the summary of the reservation$")
    public void johnShouldSeeTheSummaryOfTheReservation() throws InterruptedException {

        yakim.checksSummaryDates();
        yakim.checksSummaryNumberOfGuests(this.sumTotalGuests);
        yakim.checksSummaryTotalPrice(this.offerPrice);
    }


    @Then("^John should see the offers for the location: \"([^\"]*)\"$")
    public void johnShouldSeeTheOffersForTheLocation(String expectedPlace) {

        String offersHeading = offersPage.offersHeadingInfoText.getText();
        assertThat(offersHeading).isEqualTo("Stays in " +expectedPlace);
        System.out.println(offersHeading);
    }



}
