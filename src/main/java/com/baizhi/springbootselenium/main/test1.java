package com.baizhi.springbootselenium.main;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * @author Zz_Wang
 * @time ---
 * @function
 */
public class test1 {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://www.baidu.com/");
            driver.findElement(By.id("kw")).sendKeys("薇科技");
            driver.findElement(By.id("su")).click();
            System.out.println("title:" + driver.getTitle());
            System.out.println("url" + driver.getCurrentUrl());

            WebElement webElement = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[1]/h3/a"));

            System.out.println(webElement.getText());

//            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3")));
//            System.out.println(firstResult.getAttribute("textContent"));
        } finally {
//            driver.quit();
        }
    }
}
