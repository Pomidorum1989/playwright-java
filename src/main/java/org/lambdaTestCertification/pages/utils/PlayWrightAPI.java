package org.lambdaTestCertification.pages.utils;

import com.microsoft.playwright.Page;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PlayWrightAPI {
    public static void setTestStatus(String status, String remark, Page page) {
        if (Boolean.parseBoolean(System.getProperty("isLambdaTest"))) {
            Object result = page.evaluate("_ => {}", "lambdatest_action: { \"action\": \"setTestStatus\", " +
                    "\"arguments\": { \"status\": \"" + status + "\", \"remark\": \"" + remark + "\"}}");
        }
    }
}
