package com.baizhi.springbootselenium.service;

import com.baizhi.springbootselenium.util.DateUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author Zz_Wang
 * @time ---
 * @function
 */
@Service
public class ETHService {

    public void execute() {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        driver.get("https://www.qkl123.com/data/mining_profitability/eth");

        WebElement dateInputElement = driver.findElement(By.xpath("//*[@id='__layout']/div/div[3]/div[1]/div[2]/div[1]/span/div/div[1]/input[1]"));
        dateInputElement.click();
        dateInputElement.sendKeys(Keys.CONTROL, "a");
        dateInputElement.sendKeys("2018-07-11", Keys.ENTER);

        dateInputElement = driver.findElement(By.xpath("//*[@id='__layout']/div/div[3]/div[1]/div[2]/div[1]/span/div/div[1]/input[2]"));
        dateInputElement.click();
        dateInputElement.sendKeys(Keys.CONTROL, "a");
        dateInputElement.sendKeys("2021-04-28", Keys.ENTER);

        WebElement echartsElement = driver.findElement(By.xpath("//*[@id='data-chart']/div/div"));
        Dimension dimension = echartsElement.getSize();
        System.out.println(dimension);

        Actions action = new Actions(driver);
        action.moveToElement(echartsElement, 1 - (dimension.getWidth() / 2), 0).perform();

        String dateStr = null;
        String currencyPriceStr = null;
        String unitCalculationIncomeStr = null;
        while (true) {
            WebElement dataElement = null;
            try {
                dataElement = driver.findElement(By.xpath("//*[@id='data-chart']/div/div/div[2]"));
            } catch (NoSuchElementException e) {
                break;
            }
            String dataString = dataElement.getText();
            String[] split = dataString.split("\n");
            if (split.length > 0 && StringUtils.hasLength(split[0]) && !split[0].equals(dateStr)) {
                dateStr = split[0];
                currencyPriceStr = split[2];
                unitCalculationIncomeStr = split[4];

                System.out.print(dateStr + "\t");
                System.out.print(currencyPriceStr.replace("$", "") + "\t");
                System.out.println(unitCalculationIncomeStr.replace("$", ""));
            }
            try {
                action.moveByOffset(1, 0).perform();
            } catch (MoveTargetOutOfBoundsException e) {
                System.out.println("当前页面抓取完成。");
                break;
            }
        }
    }
}
