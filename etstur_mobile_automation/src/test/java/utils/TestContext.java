package utils;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private static final Map<String, String> variables = new HashMap<>();

    public static void put(String key, String value) {
        variables.put(key.toLowerCase(), value);
    }

    public static String get(String key) {
        return variables.get(key.toLowerCase());
    }
}
