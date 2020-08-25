package ru.instagram;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginTestIT extends WebDriverSetting {
    Integer TIMEOUT = 10;
    String SITE = "https://instagram.com";
    String LOGIN = "***";
    String PASSWORD = "***";

    String LOGININPUT = "//input[@name='username']";
    String PASSWORDINPUT = "//input[@name='password']";
    String LOGINBUTTON = "//button[@type='submit']";
    String SMSCODEINPUT = "//input[@name='verificationCode']";
    String CONFIRMBUTTON = "//button[text()='Подтвердить']";

    @Before
    public void SetUP() {
        System.setProperty("webdriver.chrome.driver", "/home/anton/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        System.out.println("Start");
    }

    @Test
    public void FirstTest() throws IOException, InterruptedException {
        driver.get(SITE);
        driver.findElementByXPath(LOGININPUT).sendKeys(LOGIN);
        driver.findElementByXPath(PASSWORDINPUT).sendKeys(PASSWORD);
        driver.findElementByXPath(LOGINBUTTON).click();
        Thread.sleep(30000);
        String sms = getSMSCode();
        driver.findElementByXPath(SMSCODEINPUT).sendKeys(sms);
        driver.findElementByXPath(CONFIRMBUTTON).click();
        Thread.sleep(30000);
    }

    @After
    public void Quit() {
        System.out.println("Finish");
        driver.quit();
    }
}
