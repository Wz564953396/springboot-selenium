package com.baizhi.springbootselenium.hardDesk;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HTMLInfoCollectionService {

    private static final String folderPath = "C:\\Users\\Zz_Wang\\Desktop\\第四季度自查报告";
    private static final String[] titles = {"文件路径", "检查人", "受检人", "硬盘序列号"};
    private static final String ZHANKAI_BUTTON_LABEL = "//*[@id=\"expandtable1\"]";
    private static final String INSPECTOR_LABEL = "//*[@id=\"userInfo\"]/tr[2]/td[2]/span[2]";
    private static final String TESTED_LABEL = "//*[@id=\"userInfo\"]/tr[2]/td[1]/span[2]";
    private static final String HARD_DESK_LABEL = "//*[@id=\"table1\"]/tbody/tr/td[13]";

    private XSSFWorkbook workbook;
    private Sheet sheet;
    private int row = 1;
    private WebDriver driver;
    private int total = 0;
    private int errorNum = 0;

    {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Sheet1");
        Row row0 = sheet.createRow(0);
        Cell cell = row0.createCell(0);
        cell.setCellValue("硬盘序列号");

        Row row1 = sheet.createRow(row++);
        for (int i = 0; i < titles.length; i++) {
            row1.createCell(i).setCellValue(titles[i]);
        }

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
    }

    public void execute() throws IOException {
        File folder = new File(folderPath); // 创建文件夹对象
        File[] files = folder.listFiles(); // 获取文件夹中的所有文件

        if (files.length == 0) {
            System.out.println("没有读取到文件");
        }

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(2, TimeUnit.SECONDS);

        try {
            for (File file : files) {
                read(file);
            }
            System.out.println("读取完成");
        } catch (Exception e) {
            System.out.println("异常结束");
            e.printStackTrace();
        } finally {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Zz_Wang\\Desktop\\硬盘序列号统计.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            driver.close();
            System.out.println(String.format("输出完成：总数%d, 异常%d", total, errorNum));
        }

    }

    private void read(File file) {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles.length == 0) {
                return;
            }
            for (File subFile : subFiles) {
                read(subFile);
            }
        }

        if (file.isFile() && file.getName().endsWith(".html") && file.getName().contains("详细报告")) {

            String inspector = null;

            String name = file.getName().replaceAll(".html", "")
                    .replaceAll("详细报告", "")
                    .replaceAll("-", "")
                    .replaceAll("\\d", "");
            if (!StringUtils.hasLength(name)) {
                name = file.getParentFile().getName();
            }
            String hardDiskSerialNumber;

            try {
                driver.get(file.getAbsolutePath());

                WebElement element1 = driver.findElement(By.xpath(TESTED_LABEL));
                if (StringUtils.hasLength(element1.getText())) {
                    if (!"1".equals(element1.getText()) && element1.getText().matches(".*[\\u4e00-\\u9fa5].*")) {
                        name = element1.getText();
                    }
                }

                WebElement element3 = driver.findElement(By.xpath(INSPECTOR_LABEL));
                inspector = element3.getText();

                WebElement zhankai = driver.findElement(By.xpath(ZHANKAI_BUTTON_LABEL));
                zhankai.click();
                WebElement element2;

                try {
                    element2 = driver.findElement(By.xpath(HARD_DESK_LABEL));
                    hardDiskSerialNumber = element2.getText();
                } catch (NoSuchElementException e) {
                    hardDiskSerialNumber = "未找到硬盘信息";
                    System.out.println(name + "没找到硬盘");
                }

            } catch (Exception e) {
                hardDiskSerialNumber = String.format("[%s]文件读取异常", file.getName());
                errorNum++;
                System.out.println("【" + file.getAbsolutePath() + "】文件读取异常");
                e.printStackTrace();
            }


            Row rowOne = sheet.createRow(row++);
            rowOne.createCell(0).setCellValue(file.getAbsolutePath());
            rowOne.createCell(1).setCellValue(inspector);
            rowOne.createCell(2).setCellValue(name);
            rowOne.createCell(3).setCellValue(hardDiskSerialNumber);
            total++;
        }
    }
}
