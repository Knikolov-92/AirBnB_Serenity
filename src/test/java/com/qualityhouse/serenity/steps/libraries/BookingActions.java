package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.page_objects.SummaryPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDate;
import java.util.*;

import static com.qualityhouse.serenity.page_objects.HomePage.*;
import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static com.qualityhouse.serenity.page_objects.SummaryPage.*;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingActions {

    private HomePage homePage;
    private OffersPage offersPage;
    private SummaryPage summaryPage;
    private WebDriver driver = getDriver();
    private SoftAssertions softly = new SoftAssertions();
    private String vacationStartDate;
    private String vacationEndDate;
    private String offerPrice;

    @Steps
    private BaseActions rumi;
    @Steps
    private HomepageActions vasi;
    @Steps
    private OfferspageActions didi;

    @Step
    public void picksCheckInCheckOutDates(String checkIn, String checkOut) throws InterruptedException {

        String patternDefault = "d/MMMM/yyyy";
        String patternSummary = "M/d/yyyy";
        Locale localeDefault = Locale.ENGLISH;
//get current date ----------------------------------------------------------------------------------------
        LocalDate currentDate = rumi.getsCurrentDatePlus(0);
        String currentDateFormatted = rumi.getsDateFormatted(currentDate, patternDefault, localeDefault);

//get start date ------------------------------------------------------------------------------------------
        LocalDate bookStart = rumi.getsCurrentDatePlus(Integer.parseInt(checkIn));
        String startDateFormatted = rumi.getsDateFormatted(bookStart, patternDefault, localeDefault);
        String[] startDateArray = startDateFormatted.split("/");
        String dayStart = startDateArray[0];
        String monthStart = startDateArray[1].toLowerCase();
        String yearStart = startDateArray[2];

//get end date --------------------------------------------------------------------------------------------
        LocalDate bookEnd = rumi.getsCurrentDatePlus(Integer.parseInt(checkOut));
        String endDateFormatted = rumi.getsDateFormatted(bookEnd, patternDefault, localeDefault);
        String[] endDateArray = endDateFormatted.split("/");
        String dayEnd = endDateArray[0];
        String monthEnd = endDateArray[1].toLowerCase();
        String yearEnd = endDateArray[2];

//print dates ---------------------------------------------------------------------------------------------
        System.out.println("current date: " +currentDateFormatted
                +", start: " +startDateFormatted+ ", end: " +endDateFormatted );

//set start/end date to check in summary page--------------------------------------------------------------
        this.vacationStartDate = rumi.getsDateFormatted(bookStart, patternSummary, null);
        this.vacationEndDate = rumi.getsDateFormatted(bookEnd, patternSummary, null);

//click on CheckInOut button ------------------------------------------------------------------------------
        rumi.clicksOn(homePage.checkInOutDateButton);

//select start date ---------------------------------------------------------------------------------------
        vasi.selectsCheckInOutDate(dayStart, monthStart, yearStart);

//select end date -----------------------------------------------------------------------------------------
        vasi.selectsCheckInOutDate(dayEnd, monthEnd, yearEnd);
    }

    @Step
    public void picksGuestsOptions(String adultsNumber, String kidsNumber, String babiesNumber)
            throws InterruptedException {

        rumi.clicksOn(homePage.guestsPickButton);
        List<WebElementFacade> guestsAddButtons = homePage.findAll(GUESTS_ADD_BUTTON_LOCATOR);

        for (int n = 1; n <= Integer.parseInt(adultsNumber); n++) {

            rumi.clicksOn(guestsAddButtons.get(0));
        }

        for (int n = 1; n <= Integer.parseInt(kidsNumber); n++) {

            rumi.clicksOn(guestsAddButtons.get(1));
        }

        for (int n = 1; n <= Integer.parseInt(babiesNumber); n++) {

            rumi.clicksOn(guestsAddButtons.get(2));
        }

        //ilio.clicksOn(homePage.guestsSaveButton);
    }

    @Step
    public void picksCurrency(String targetCurrency) throws InterruptedException {

        String loopElement = "";
        rumi.movesPointerToElement(offersPage.currencyPickButton);
        rumi.clicksOn(offersPage.currencyPickButton);
        List<WebElementFacade> currencyPickList = offersPage.findAll(CURRENCY_PICK_LIST_LOCATOR);

        for(int i = 0; i < currencyPickList.size(); i++) {

            loopElement = currencyPickList.get(i).getText().toUpperCase().substring(0, 3);
            System.out.println("current currency is: " +loopElement);
            if(loopElement.equals(targetCurrency) ) {
                rumi.clicksOn(currencyPickList.get(i) );
                break;
            }
        }
    }

    @Step
    public void entersPriceRange(String minPrice, String maxPrice) throws InterruptedException {

        rumi.clicksOn(offersPage.filterPriceButton);
        rumi.entersTextInField(offersPage.inputFieldPriceMin, minPrice);
        rumi.entersTextInField(offersPage.inputFieldPriceMax, maxPrice);
        rumi.clicksOn(offersPage.filterPriceSaveButton);
    }

    public void choosesMoreFilterOptions(String numBaths, String airConYesNo, String hotTubeYesNo)
            throws InterruptedException {

        rumi.clicksOn(offersPage.filterMoreOptionsButton);
        WebElementFacade airConBox = offersPage.airConditionerCheckbox;
        WebElementFacade hotTubeBox = offersPage.hotTubeCheckbox;

        for(int i = 1; i <= Integer.parseInt(numBaths); i++) {

            rumi.clicksOn(offersPage.bathroomsAddButton);
        }
        assertThat(offersPage.bathroomCurrentNumber.getText() ).isEqualTo(numBaths);

        rumi.movesPointerToElement(airConBox);
        if(airConYesNo.equalsIgnoreCase("yes") ) {
            rumi.clicksOn(airConBox);
        }

        rumi.movesPointerToElement(hotTubeBox);
        if(hotTubeYesNo.equalsIgnoreCase("yes") ) {
            rumi.clicksOn(hotTubeBox);
        }

        rumi.clicksOn(offersPage.filterMoreOptionsSaveButton);
    }

    @Step
    public void checksSummaryDates() throws InterruptedException {

        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        Thread.sleep(2000);
        rumi.movesPointerToElement(summaryPage.totalPrice);

        String actualStartDate = summaryPage.checkInDate.getText().trim();
        String actualEndDate = summaryPage.checkOutDate.getText().trim();

        System.out.println("Expected start_date:" +this.vacationStartDate);
        System.out.println("Actual start_date:" +actualStartDate);
        softly.assertThat(actualStartDate).isEqualTo(this.vacationStartDate);

        System.out.println("End_day:" +this.vacationEndDate);
        System.out.println("Actual end_date:" +actualEndDate);
        softly.assertThat(actualEndDate).isEqualTo(this.vacationEndDate);

        softly.assertAll();
    }

    @Step
    public void checksSummaryNumberOfGuests(int expectedNumberOfGuests, int expectedNumberOfBabies) {

        if(expectedNumberOfGuests > 0) {
            String actualNumberOfGuests = summaryPage.numberOfGuests.getText().substring(0, 1);
            System.out.println("Actual number of guests: " + actualNumberOfGuests);
            assertThat(actualNumberOfGuests).isEqualTo(Integer.toString(expectedNumberOfGuests));
        }

        if(expectedNumberOfBabies > 0) {
            String actualNumberOfBabies = summaryPage.numberOfBabies.getText().substring(0, 1);
            System.out.println("Actual number of babies: " + actualNumberOfBabies);
            assertThat(actualNumberOfBabies).isEqualTo(Integer.toString(expectedNumberOfBabies));
        }
    }

    @Step
    public void checksSummaryTotalPrice() {

        Currency euro = Currency.getInstance("EUR");
        String euroSymbol = euro.getSymbol();
        List<WebElementFacade> costsList = summaryPage.findAll(SUMMARY_TAXES_LOCATOR);
        List<WebElementFacade> discountsList = summaryPage.findAll(SUMMARY_DISCOUNTS_LOCATOR);
        String actualTaxText = "";
        String actualDiscountText = "0";
        int sumTotal = 0;

        for(WebElementFacade cost : costsList) {

            String[] costArr = cost.getText().trim().substring(1).split(" ");
                for(String value : costArr) {
                    actualTaxText += value;
                }
            sumTotal += Integer.parseInt(actualTaxText);
            System.out.println("+" +actualTaxText +"; current sum = " +sumTotal);
            actualTaxText = "";

        }

        if(discountsList.size() != 0) {
            for(WebElementFacade discount : discountsList) {
                actualDiscountText = discount.getText().trim().substring(7);
            }
             sumTotal -= Integer.parseInt(actualDiscountText);
            System.out.println("-" +actualDiscountText +", currernt sum =" +sumTotal);
        }

        String[] totalPriceArr = summaryPage.totalPrice.getText().trim().split(" ");
        String totalPriceText = "";
            for(String value : totalPriceArr) {
                totalPriceText += value.trim();
            }

        System.out.println("expected: " +euroSymbol +sumTotal +", actual: " +totalPriceText);

        softly.assertThat(Integer.parseInt(totalPriceText.substring(1)) ).isEqualTo(sumTotal);
        softly.assertThat(totalPriceText).isEqualTo(euroSymbol + (Integer.toString(sumTotal)));
        softly.assertThat(totalPriceText).isEqualTo(this.offerPrice);
        softly.assertAll();
    }

    @Step
    public void searchesForSuitablePlace(String starRating) throws InterruptedException {

        Actions actions = new Actions(driver);
        String loopElement = "";
        boolean correctOfferFound = false;
        String lastPage = didi.getsLastPageNumberWithOffers();
        String currentPage = "";
        int strLen = 0;

        while( !(correctOfferFound) ) {

            List<WebElementFacade> listOfStarsPrices = offersPage.findAll(OFFERS_STAR_PRICE_LOCATOR);
            currentPage = didi.getsCurrentPageNumberWithOffers();

            for (int i = 0; i < listOfStarsPrices.size(); i++) {

                loopElement = listOfStarsPrices.get(i).getText().trim().substring(0, 3);
                System.out.println("Element(" + i + ") = " + loopElement);

                if (loopElement.equals(starRating.trim())) {

                    correctOfferFound = true;
                    rumi.movesPointerToElement(listOfStarsPrices.get(i + 1));
                    strLen = listOfStarsPrices.get(i + 1).getText().length();
                    this.offerPrice = listOfStarsPrices.get(i + 1).getText().substring(0, strLen - 4).trim();
                    System.out.println("expected booking price: " +this.offerPrice);
                    rumi.movesPointerToElement(listOfStarsPrices.get(i));
                    actions.click().perform();
                    break;
                }
            }
            if(!(currentPage.equals(lastPage)) && !(correctOfferFound) ) {

                didi.clicksOnNextOffersResultPage();
                listOfStarsPrices.clear();

            } else if(currentPage.equals(lastPage) && !(correctOfferFound) )  {

                correctOfferFound = true;
                System.out.println("No offers with current stars-criteria found.");
            }
        }
    }

}
