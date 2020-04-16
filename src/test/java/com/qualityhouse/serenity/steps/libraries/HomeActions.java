package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.List;

import static com.qualityhouse.serenity.page_objects.HomePage.*;

public class HomeActions {

    private HomePage homePage;
    @Steps
    private BaseActions rumi;

    public void selectsCheckInOutDate(String day, String month, String year) {

        String expectedMonthYearInCalendar = month + " " + year;
        String actualCurrentMonthYearInCalendar;
        List<WebElementFacade> checkInOutDays = null;
        List<WebElementFacade> monthYearTextList;
        boolean monthInCalendarFound = false;
        System.out.println("month+year expected is: " + expectedMonthYearInCalendar);

        while(monthInCalendarFound == false) {

            monthYearTextList = homePage.findAll(CALENDAR_MONTH_YEAR_TEXT_LOCATOR);
            actualCurrentMonthYearInCalendar = monthYearTextList.get(1).getText().toLowerCase();

            System.out.println("month+year actual is: " + actualCurrentMonthYearInCalendar);

            if ((actualCurrentMonthYearInCalendar).equals(month + " " + year)) {

                checkInOutDays = homePage.findAll(CALENDAR_DAY_CURRENT_MONTH_LOCATOR);
                monthInCalendarFound = true;
                break;
            }
            else {
                rumi.clicksOn(homePage.bookingNextMonthButton);
                monthYearTextList.clear();
            }
        }

        for (WebElementFacade checkInDay : checkInOutDays) {

            if (checkInDay.getText().trim().equals(day)) {

                rumi.clicksOn(checkInDay);
                break;
            }
        }
    }

    @Step
    public void entersReservationLocation(String location) {

        //Actions actions = new Actions(driver);
        rumi.entersTextInField(homePage.whereInputField, location);
        //actions.sendKeys(Keys.ENTER).perform();
    }


}
