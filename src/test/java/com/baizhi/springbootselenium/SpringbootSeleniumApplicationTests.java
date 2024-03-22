package com.baizhi.springbootselenium;

import com.baizhi.springbootselenium.service.ETHService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringbootSeleniumApplicationTests {

    @Autowired
    private ETHService ethService;

    @Test
    public void test1() {
        ethService.execute();
    }

    @Test
    public void contextLoads() {
        Properties properties = System.getProperties();
        properties.getProperty("APP_HOME");
        System.out.println(properties);
        String property = System.getProperty("user.dir");
        System.out.println(property);
        String appHome = System.getProperties().getProperty("APP_HOME", System.getProperty("user.dir"));
        System.out.println(appHome);

        System.setProperty("webdriver.chrome.driver", "D:/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            driver.get("https://www.bilibili.com/");
            String homePageWindowHandle = driver.getWindowHandle();
            System.out.println(homePageWindowHandle);

            driver.findElement(By.xpath("//*[@id='nav_searchform']/input")).sendKeys("Selenium");
            driver.findElement(By.xpath("//*[@id='nav_searchform']/div/button")).click();

            Set<String> windowHandles = driver.getWindowHandles();
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(homePageWindowHandle)) {
                    driver.switchTo().window(windowHandle);
                    System.out.println("switch to window \"" + windowHandle + "\"");

                }
            }

            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id='all-list']/div[1]/div[2]/ul/li/a"));
            for (WebElement webElement : webElements) {
                webElement.click();
            }


//            driver.findElement(By.linkText("登录")).click();
//            driver.findElement(By.linkText("用户名登录")).click();
//            driver.findElement(By.id("TANGRAM__PSP_11__userName")).sendKeys("17320257962");
//            driver.findElement(By.id("TANGRAM__PSP_11__password")).sendKeys("wz201405001148");

//            driver.findElement(By.id("kw")).sendKeys("薇科技");
//            driver.findElement(By.id("su")).click();
//            try {
//                Thread.sleep(3 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

//            webDriverWait.until(new ExpectedCondition<WebElement>() {
//                @Nullable
//                @Override
//                public WebElement apply(@Nullable WebDriver input) {
//                    return driver.findElement(By.xpath("//div[@id='content_left']/div/h3/a"));
//                }
//            });
//            System.out.println("title:" + driver.getTitle());
//            System.out.println("url:" + driver.getCurrentUrl());

//            List<WebElement> webElementList = driver.findElements(By.xpath("//div[@id='content_left']/div/h3/a"));

//            for (WebElement webElement : webElementList) {
//                System.out.println(webElement.getText());
//            }

//            WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3")));
//            System.out.println(firstResult.getAttribute("textContent"));
        } finally {
//            driver.quit();
        }
    }

}
