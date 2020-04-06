package com.qualityhouse.serenity.steps.libraries;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

public class BaseActions {

    @Step
    public void clicksOn( WebElementFacade buttonOrLink ) throws InterruptedException {
        buttonOrLink.waitUntilClickable()
                .click();
        Thread.sleep(3000);
    }
}
