package org.lambdaTestCertification.pages.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.*;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.lambdaTestCertification.pages.utils.LambdaTestCapabilities.PASSWORD;
import static org.lambdaTestCertification.pages.utils.LambdaTestCapabilities.USER_NAME;

@Log4j2
public class LambdaTestAPI {
    public static void setTestStatus(String status, String remark, Page page) {
        if (Boolean.parseBoolean(System.getProperty("isLambdaTest"))) {
            page.evaluate("_ => {}", "lambdatest_action: { \"action\": \"setTestStatus\", " +
                    "\"arguments\": { \"status\": \"" + status + "\", \"remark\": \"" + remark + "\"}}");
        }
    }

    public static String generateBase64AuthToken() {
        String s = USER_NAME + ":" + PASSWORD;
        String encodedString = Base64.getEncoder().encodeToString(s.getBytes());
        log.info("Encoded token: {}", encodedString);
        return encodedString;
    }

    public static String[] getRunInfo(Playwright playwright) {
        APIRequest.NewContextOptions options = new APIRequest.NewContextOptions().setBaseURL("https://api.lambdatest.com/");
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Basic " + LambdaTestAPI.generateBase64AuthToken());
        options.setExtraHTTPHeaders(headers);
        APIRequestContext apiRequestContext = playwright.request().newContext(options);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);

        String requestParams = "automation/api/v1/sessions?username=" + USER_NAME + "&limit=1&fromdate=" + formattedDate;
        log.info("{}{}", options.baseURL, requestParams);

        APIResponse response = apiRequestContext.get(requestParams);
        if (response.ok()) {
            String responseBody = response.text();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray data = jsonObject.getAsJsonArray("data");
            for (JsonElement element : data) {
                JsonObject session = element.getAsJsonObject();
                String testId = session.get("test_id").getAsString();
                String buildId = session.get("build_id").getAsString();
                log.info("Test ID: {}", testId);
                log.info("Build ID: {}", buildId);
                return new String[]{testId, buildId};
            }
        } else {
            log.error("Request failed: {}", response.status() + " - " + response.statusText());
            log.error(response.text());
        }
        return new String[]{};
    }
}
