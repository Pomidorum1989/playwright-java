package org.lambdaTestCertification.pages.utils;

import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LambdaTestCapabilities {
    private static final String USER_NAME = "pomidorum1989";
    private static final String PASSWORD = "XYpvDY1Wz5YsBTzyikfrkn5RW0hXEePbz6w8eDL71OgI0VDZJY";
    public static final String CDP_URL = "wss://cdp.lambdatest.com/playwright?capabilities=";
    private static final JsonObject CAPABILITIES = new JsonObject();
    private static final JsonObject LAMBDA_CAPABILITIES = new JsonObject();

    public static String generateCapabilities() {
        // Browsers allowed: `Chrome`, `MicrosoftEdge`, `pw-chromium`, `pw-firefox` and `pw-webkit`
        CAPABILITIES.addProperty("browserName", "Chrome");
        CAPABILITIES.addProperty("browserVersion", "latest");
        LAMBDA_CAPABILITIES.addProperty("platform", "Windows 10");
        LAMBDA_CAPABILITIES.addProperty("name", "Playwright Test");
        LAMBDA_CAPABILITIES.addProperty("build", "Playwright Java Build");
        LAMBDA_CAPABILITIES.addProperty("user", USER_NAME);
        LAMBDA_CAPABILITIES.addProperty("accessKey", PASSWORD);
        LAMBDA_CAPABILITIES.addProperty("console", true);
        LAMBDA_CAPABILITIES.addProperty("network", true);
        CAPABILITIES.add("LT:Options", LAMBDA_CAPABILITIES);
        return URLEncoder.encode(CAPABILITIES.toString(), StandardCharsets.UTF_8);
    }
}
