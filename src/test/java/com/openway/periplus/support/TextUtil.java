package com.openway.periplus.support;

import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtil {
    private static final Pattern FIRST_INTEGER = Pattern.compile("\\d+");

    private TextUtil() {
    }

    public static String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
    }

    public static int firstInteger(String text) {
        Matcher matcher = FIRST_INTEGER.matcher(normalize(text));
        if (!matcher.find()) {
            return 0;
        }
        return Integer.parseInt(matcher.group());
    }

    public static boolean containsMeaningfulTitle(String bodyText, String productTitle) {
        String body = normalize(bodyText).toLowerCase(Locale.ROOT);
        String title = normalize(productTitle).toLowerCase(Locale.ROOT);
        if (title.isBlank()) {
            return false;
        }
        if (body.contains(title)) {
            return true;
        }

        String firstSegment = title.split("[:|,-]", 2)[0].trim();
        if (firstSegment.length() >= 4 && body.contains(firstSegment)) {
            return true;
        }

        String prefix = Arrays.stream(title.split("\\s+"))
                .filter(word -> word.length() > 2)
                .limit(3)
                .reduce((left, right) -> left + " " + right)
                .orElse("");
        return prefix.length() >= 4 && body.contains(prefix);
    }

    public static String truncate(String text, int maxLength) {
        String normalized = normalize(text);
        if (normalized.length() <= maxLength) {
            return normalized;
        }
        return normalized.substring(0, maxLength) + "...";
    }
}
