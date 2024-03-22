package com.baizhi.springbootselenium.hardDesk;

import java.io.IOException;

public class ExecuteService {


    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:/Driver/chromedriver.exe");

        HTMLInfoCollectionService service = new HTMLInfoCollectionService();
        service.execute();
    }
}
