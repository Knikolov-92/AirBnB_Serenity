package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.page_objects.SummaryPage;
import cucumber.api.java.ca.Cal;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.qualityhouse.serenity.page_objects.HomePage.*;
import static com.qualityhouse.serenity.page_objects.OffersPage.*;
import static com.qualityhouse.serenity.page_objects.SummaryPage.SUMMARY_DISCOUNTS_LOCATOR;
import static com.qualityhouse.serenity.page_objects.SummaryPage.SUMMARY_TAXES_LOCATOR;
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

    @Steps
    private BaseActions ilio;

    @Step
    public void entersReservationLocation(String location) {

        Actions actions = new Actions(driver);
        ilio.entersStringInField(homePage.whereInputField, location);
        actions.sendKeys(Keys.ENTER).perform();
    }

    @Step
    public void picksCheckInCheckOutDates(String checkIn, String checkOut) throws InterruptedException {

        LocalDate currentDate = LocalDate.now();
        String currentDateFormatted = currentDate.format
                (DateTimeFormatter.ofPattern("dd/MMMM/yyyy", Locale.ENGLISH));
        System.out.println("current date: " +currentDateFormatted);
        String[] dateArray = currentDateFormatted.split("/");
        String day = dateArray[0];
        String month = dateArray[1].toLowerCase();
        String year = dateArray[2];
        int startDayInt = Integer.parseInt(day) + Integer.parseInt(checkIn);
        String startDay = Integer.toString(startDayInt);
        String endDay = Integer.toString(startDayInt + Integer.parseInt(checkOut) );
        String expectedCurrentDateInCalendar = month + " " + year;
        String actualCurrentDateInCalendar = "";

        LocalDate bookStart = currentDate.plusDays(5);
        this.vacationStartDate = bookStart.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        System.out.println("start: " +bookStart);
        LocalDate bookEnd = bookStart.plusDays(7);
        this.vacationEndDate = bookEnd.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
        System.out.println("end: " +bookEnd);

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

    public void choosesMoreFilterOptions(String numBaths, String airConYesNo, String hotTubeYesNo)
            throws InterruptedException {

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

    @Step
    public void checksSummaryDates() throws InterruptedException {

        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(1));
        Thread.sleep(2000);
        String actualStartDate = summaryPage.checkInDate.getText().trim();
        String actualEndDate = summaryPage.checkOutDate.getText().trim();

        System.out.println("Expected start_date:" +this.vacationStartDate);
        System.out.println("Actual start_date:" +actualStartDate);
        softly.assertThat(actualStartDate).isEqualTo(this.vacationStartDate);

        System.out.println("End_day:" +this.vacationEndDate);
        System.out.println("Actual start_date:" +actualEndDate);
        softly.assertThat(actualEndDate).isEqualTo(this.vacationEndDate);

        softly.assertAll();
    }

    @Step
    public void checksSummaryNumberOfGuests(int expectedNumberOfGuests) {

        String actualNumberOfGuests = summaryPage.numberOfGuests.getText().substring(0, 1);
        System.out.println("Actual number of guests: " +actualNumberOfGuests);
        assertThat(actualNumberOfGuests).isEqualTo(Integer.toString(expectedNumberOfGuests));
    }

    @Step
    public void checksSummaryTotalPrice() throws InterruptedException {

        ilio.movesPointerToElement(summaryPage.reserveButton);

        Currency euro = Currency.getInstance("EUR");
        String euroSymbol = euro.getSymbol();
        List<WebElementFacade> costsList = summaryPage.findAll(SUMMARY_TAXES_LOCATOR);
        List<WebElementFacade> discountsList = summaryPage.findAll(SUMMARY_DISCOUNTS_LOCATOR);
        String actualTotalNights = costsList.get(0).getText().trim();
        String cleaningTax = costsList.get(1).getText().trim();
        String serviceTax = costsList.get(2).getText().trim();
        String discount1 = discountsList.get(0).getText().trim().substring(6);
        String totalPrice = summaryPage.totalPrice.getText().trim();

        System.out.println("nights " +actualTotalNights +",cleaning "+cleaningTax
                +",service "+serviceTax +",discount " +discount1 +",total "+totalPrice);
        int sum = Integer.parseInt(actualTotalNights.substring(1))
                + Integer.parseInt(cleaningTax.substring(1))
                + Integer.parseInt(serviceTax.substring(1))
                - Integer.parseInt(discount1.substring(1));

        softly.assertThat(Integer.parseInt(totalPrice.substring(1)) ).isEqualTo(sum);
        softly.assertThat(totalPrice).isEqualTo(euroSymbol + (Integer.toString(sum)));
        softly.assertAll();
    }
}
