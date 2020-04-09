package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import com.qualityhouse.serenity.page_objects.OffersPage;
import com.qualityhouse.serenity.page_objects.SummaryPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Steps
    private BaseActions ilio;

    @Step
    public void entersReservationLocation(String location) {

        //Actions actions = new Actions(driver);
        ilio.entersStringInField(homePage.whereInputField, location);
        //actions.sendKeys(Keys.ENTER).perform();
    }

    @Step
    public void picksCheckInCheckOutDates(String checkIn, String checkOut) throws InterruptedException {

//get current date --------------------------------------------------------------------------
        LocalDate currentDate = LocalDate.now();
        String currentDateFormatted = currentDate.format
                (DateTimeFormatter.ofPattern("d/MMMM/yyyy", Locale.ENGLISH));
        String[] currentDateArray = currentDateFormatted.split("/");
        String monthCurrent = currentDateArray[1].toLowerCase();
        String yearCurrent = currentDateArray[2];
//get start date ----------------------------------------------------------------------------
        LocalDate bookStart = currentDate.plusDays(Integer.parseInt(checkIn));
        String startDateFormatted = bookStart.format
                (DateTimeFormatter.ofPattern("d/MMMM/yyyy", Locale.ENGLISH));
        String[] startDateArray = startDateFormatted.split("/");
        String dayStart = startDateArray[0];
        String monthStart = startDateArray[1].toLowerCase();
        String yearStart = startDateArray[2];
//get end date ------------------------------------------------------------------------------
        LocalDate bookEnd = bookStart.plusDays(Integer.parseInt(checkOut));
        String endDateFormatted = bookEnd.format
                (DateTimeFormatter.ofPattern("d/MMMM/yyyy", Locale.ENGLISH));
        String[] endDateArray = endDateFormatted.split("/");
        String dayEnd = endDateArray[0];
        String monthEnd = endDateArray[1].toLowerCase();
        String yearEnd = endDateArray[2];
//print dates ------------------------------------------------------------------------------
        System.out.println("current date: " +currentDateFormatted +",start: " +startDateFormatted+ ",end: " +endDateFormatted );
//set start/end date to check in summary page
        this.vacationStartDate = bookStart.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        this.vacationEndDate = bookEnd.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//check current calendar header------------------------------------------------------------------------------
        String expectedCurrentMonthYearInCalendar = monthCurrent + " " + yearCurrent;
        String actualCurrentMonthYearInCalendar = "";

        ilio.clicksOn(homePage.checkInOutDate);
        List<WebElementFacade> checkOutDays;
        List<WebElementFacade> checkInDays;
        List<WebElementFacade> monthYearTextList = homePage.findAll(CALENDAR_MONTH_YEAR_TEXT_LOCATOR);
        actualCurrentMonthYearInCalendar = monthYearTextList.get(1).getText().toLowerCase();
        System.out.println("month+year expected is: " + expectedCurrentMonthYearInCalendar
                + ", actual is: " + actualCurrentMonthYearInCalendar);
        assertThat(actualCurrentMonthYearInCalendar).isEqualTo(expectedCurrentMonthYearInCalendar);
//select start date -----------------------------------------------------------------------------------------

        if((monthStart +" " +yearStart).equals(actualCurrentMonthYearInCalendar) ) {

           checkInDays = homePage.findAll(CALENDAR_DAY_CURRENT_MONTH_LOCATOR);
        }else
            {
                checkInDays = homePage.findAll(CALENDAR_DAY_NEXT_MONTH_LOCATOR);
        }
            for (WebElementFacade checkInDay : checkInDays) {

                if (checkInDay.getText().trim().equals(dayStart)) {

                    ilio.clicksOn(checkInDay);
                    break;
                }
            }
//select end date -----------------------------------------------------------------------------------------
        if((monthEnd +" " +yearEnd).equals(actualCurrentMonthYearInCalendar) ) {

            checkOutDays = homePage.findAll(CALENDAR_DAY_CURRENT_MONTH_LOCATOR);
        }else
            {
                checkOutDays = homePage.findAll(CALENDAR_DAY_NEXT_MONTH_LOCATOR);
        }
            for (WebElementFacade checkOutDay : checkOutDays) {

                if (checkOutDay.getText().trim().equals(dayEnd)) {

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

        //ilio.clicksOn(homePage.guestsSaveButton);
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
        ilio.movesPointerToElement(summaryPage.totalPrice);

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
            System.out.println("+" +actualTaxText +"current sum = " +sumTotal);
            actualTaxText = "";

        }

        if(discountsList.size() != 0) {
            for(WebElementFacade discount : discountsList) {
                actualDiscountText = discount.getText().trim().substring(2);
            }
             sumTotal -= Integer.parseInt(actualDiscountText);
            System.out.println("-" +actualDiscountText +", currernt sum =" +sumTotal);
        }

        String[] totalPriceArr = summaryPage.totalPrice.getText().trim().substring(1).split(" ");
        String totalPriceText = "";
            for(String value : totalPriceArr) {
                totalPriceText += value;
            }

        System.out.println("expected: "+sumTotal +", actual: " +totalPriceText);

        softly.assertThat(Integer.parseInt(totalPriceText) ).isEqualTo(sumTotal);
        softly.assertThat(euroSymbol + totalPriceText).isEqualTo(euroSymbol + (Integer.toString(sumTotal)));
        softly.assertAll();
    }
}
