package com.openway.periplus.config;

import java.time.Duration;

public final class TestConfig {
    private static final String DEFAULT_BASE_URL = "https://www.periplus.com";
    private static final String DEFAULT_SEARCH_TERM = "ikigai";
    private static final long DEFAULT_TIMEOUT_SECONDS = 20;

    private TestConfig() {
    }

    public static String requiredEnvironment(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable " + name
                            + ". Set PERIPLUS_EMAIL and PERIPLUS_PASSWORD before running mvn test.");
        }
        return value;
    }

    public static String baseUrl() {
        String value = System.getProperty("baseUrl", DEFAULT_BASE_URL).trim();
        if (value.endsWith("/")) {
            return value.substring(0, value.length() - 1);
        }
        return value;
    }

    public static String searchTerm() {
        return System.getProperty("periplus.searchTerm", DEFAULT_SEARCH_TERM).trim();
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"));
    }

    public static Duration timeout() {
        return Duration.ofSeconds(longProperty("periplus.timeoutSeconds", DEFAULT_TIMEOUT_SECONDS));
    }

    private static long longProperty(String name, long defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(name + " must be a whole number of seconds.", exception);
        }
    }
}
