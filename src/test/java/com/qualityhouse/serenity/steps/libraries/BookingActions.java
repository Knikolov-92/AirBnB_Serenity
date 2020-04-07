package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertionError;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.qualityhouse.serenity.page_objects.HomePage.*;
import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingActions {

    private HomePage homePage;
    private OffersPage offersPage;
    private WebDriver driver = getDriver();
    private SoftAssertions softly = new SoftAssertions();
    @Steps
    private BaseActions ilio;

    @Step
    public void entersReservationLocation(String location) {

        Actions actions = new Actions(driver);
        ilio.entersStringInField(homePage.whereInputField, location);
        actions.sendKeys(Keys.ENTER).perform();
    }

    @Step
    public void picksCheckInCheckOutDates() throws InterruptedException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MMMMM/yyyy", Locale.ENGLISH);
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String[] dateArray = currentDate.split("/");
        String day = dateArray[0];
        String month = dateArray[1].toLowerCase();
        String year = dateArray[2];
        int startDayInt = Integer.parseInt(day) + 5;
        String startDay = Integer.toString(startDayInt);
        String endDay = Integer.toString(startDayInt + 7);
        String expectedCurrentDateInCalendar = month + " " + year;
        String actualCurrentDateInCalendar = "";

        System.out.println(currentDate);
        System.out.println("day: " + day + ", month: " + month + ", year: " + year);
        System.out.println("Day to start: " + startDay);
        System.out.println("Day to end: " + endDay);

        ilio.clicksOn(homePage.checkInDate);

        actualCurrentDateInCalendar = homePage.find(CALENDAR_MONTH_YEAR_TEXT_LOCATOR).getText().toLowerCase();
        System.out.println("month+year expected is: " + expectedCurrentDateInCalendar
                + ", actual is: " + actualCurrentDateInCalendar);

        assertThat(actualCurrentDateInCalendar).isEqualTo(expectedCurrentDateInCalendar);

        List<WebElementFacade> checkInDays = homePage.findAll(CALENDAR_DAY_PICK_LOCATOR);

        for (WebElementFacade checkInDay : checkInDays) {

            if (checkInDay.getText().trim().equals(startDay)) {

                ilio.clicksOn(checkInDay);
                break;
            }
        }

        List<WebElementFacade> checkOutDays = homePage.findAll(CALENDAR_DAY_PICK_LOCATOR);

        for (WebElementFacade checkOutDay : checkOutDays) {

            if (checkOutDay.getText().trim().equals(endDay)) {

                ilio.clicksOn(checkOutDay);
                break;
            }
        }
    }

    @Step
    public void picksGuestsOptions(String adultsNumber, String kidsNumber, String babiesNumber)
            throws InterruptedException {

        ilio.clicksOn(homePage.guestsPickButton);
        List<WebElementFacade> guestsAddButtons = homePage.findAll(GUESTS_ADD_BUTTON_LOCATOR);

        for (int n = 1; n <= Integer.parseInt(adultsNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(0));
        }

        for (int n = 1; n <= Integer.parseInt(kidsNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(1));
        }

        for (int n = 1; n <= Integer.parseInt(babiesNumber); n++) {

            ilio.clicksOn(guestsAddButtons.get(2));
        }

        ilio.clicksOn(homePage.guestsSaveButton);
    }

    @Step
    public void picksCurrency(String targetCurrency) throws InterruptedException {

        String loopElement = "";
        ilio.movesPointerToElement(offersPage.currencyPickButton);
        ilio.clicksOn(offersPage.currencyPickButton);
        List<WebElementFacade> currencyPickList = offersPage.findAll(CURRENCY_PICK_LIST_LOCATOR);

        for(int i = 0; i < currencyPickList.size(); i++) {

            loopElement = currencyPickList.get(i).getText().toUpperCase().substring(0, 3);
            System.out.println("current currency is: " +loopElement);
            if(loopElement.equals(targetCurrency) ) {
                ilio.clicksOn(currencyPickList.get(i) );
                break;
            }
        }
    }

    @Step
    public void entersPriceRange(String minPrice, String maxPrice) throws InterruptedException {

        ilio.clicksOn(offersPage.filterPriceButton);
        ilio.entersStringInField(offersPage.inputFieldPriceMin, minPrice);
        ilio.entersStringInField(offersPage.inputFieldPriceMax, maxPrice);
        ilio.clicksOn(offersPage.filterPriceSaveButton);
    }

    public void choosesMoreFilterOptions(String numBaths, String airConYesNo, String hotTubeYesNo) throws InterruptedException {

        ilio.clicksOn(offersPage.filterMoreOptionsButton);
        WebElementFacade airConBox = offersPage.airConditionerCheckbox;
        WebElementFacade hotTubeBox = offersPage.hotTubeCheckbox;

        for(int i = 1; i <= Integer.parseInt(numBaths); i++) {

            ilio.clicksOn(offersPage.bathroomsAddButton);
        }
        assertThat(offersPage.bathroomCurrentNumber.getText() ).isEqualTo(numBaths);

        ilio.movesPointerToElement(airConBox);
        if(airConYesNo.equalsIgnoreCase("yes") ) {
            ilio.clicksOn(airConBox);
        }

        ilio.movesPointerToElement(hotTubeBox);
        if(hotTubeYesNo.equalsIgnoreCase("yes") ) {
            ilio.clicksOn(hotTubeBox);
        }

        ilio.clicksOn(offersPage.filterMoreOptionsSaveButton);
    }

}
