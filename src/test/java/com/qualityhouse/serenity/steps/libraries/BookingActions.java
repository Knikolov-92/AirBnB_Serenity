package com.qualityhouse.serenity.steps.libraries;

import com.qualityhouse.serenity.page_objects.HomePage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.qualityhouse.serenity.page_objects.HomePage.CALENDAR_DAY_PICK_LOCATOR;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;

public class BookingActions {

    private HomePage homePage;
    private WebDriver driver = getDriver();
    @Steps
    private BaseActions ilio;

    public void entersReservationLocation(String location) throws InterruptedException {

        Actions actions = new Actions(driver);
        homePage.whereInputField.waitUntilVisible().type(location);
        actions.sendKeys(Keys.ENTER).perform();
    }

    public void picksCheckInCheckOutDate() throws InterruptedException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        String[] dateArray = currentDate.split("/");
        String day = dateArray[0];
        String month = dateArray[1];
        String year = dateArray[2];
        int startDayInt = Integer.parseInt(day) + 5;
        String startDay = Integer.toString(startDayInt);
        String endDay = Integer.toString(startDayInt + 7);

        System.out.println(currentDate);
        System.out.println("day: " + day + ", month: " + month + ", year: " + year);
        System.out.println("Day to start: " + startDay);
        System.out.println("Day to end: " + endDay);

        ilio.clicksOn(homePage.checkInDate);
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


}
