package org.lambdaTestCertification.pages.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.log4j.Log4j2;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Log4j2
public class LambdaTestCapabilities {
    public static final String USER_NAME = "pomidorum1989";
    public static final String PASSWORD = "XYpvDY1Wz5YsBTzyikfrkn5RW0hXEePbz6w8eDL71OgI0VDZJY";
    public static final String CDP_URL = "wss://cdp.lambdatest.com/playwright?capabilities=";
    private static final JsonObject CAPABILITIES = new JsonObject();
    private static final JsonObject LAMBDA_CAPABILITIES = new JsonObject();

    public static String generateCapabilities() {
        // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
        CAPABILITIES.addProperty("browserName", "Chrome");
        CAPABILITIES.addProperty("browserVersion", "latest");
        LAMBDA_CAPABILITIES.addProperty("platform", "Windows 10");
        LAMBDA_CAPABILITIES.addProperty("name", "Playwright 101");
        LAMBDA_CAPABILITIES.addProperty("build", "Playwright Java");
        LAMBDA_CAPABILITIES.addProperty("user", USER_NAME);
        LAMBDA_CAPABILITIES.addProperty("accessKey", PASSWORD);
        LAMBDA_CAPABILITIES.addProperty("console", true);
        LAMBDA_CAPABILITIES.addProperty("network", true);
        LAMBDA_CAPABILITIES.add("tags", arrayToJson(Arrays.asList("Certification", "101")));
        LAMBDA_CAPABILITIES.add("buildTags", arrayToJson(Arrays.asList("Playwright", "Java")));
        LAMBDA_CAPABILITIES.addProperty("resolution", "2048x1536");
        LAMBDA_CAPABILITIES.addProperty("video", true);
        LAMBDA_CAPABILITIES.addProperty("visual", false);
        LAMBDA_CAPABILITIES.addProperty("geoLocation", "US");
        LAMBDA_CAPABILITIES.addProperty("idleTimeout", 500);
        LAMBDA_CAPABILITIES.addProperty("tunnel", false);
//        LAMBDA_CAPABILITIES.addProperty("tunnelName", "Playwright 101");
        CAPABILITIES.add("LT:Options", LAMBDA_CAPABILITIES);
        return URLEncoder.encode(CAPABILITIES.toString(), StandardCharsets.UTF_8);
    }

    private static JsonArray arrayToJson(Iterable<String> array) {
        JsonArray json = new JsonArray();
        for (String item : array) {
            json.add(item);
        }
        return json;
    }
}
