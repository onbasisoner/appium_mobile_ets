package utils;

import io.appium.java_client.AppiumBy;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.openqa.selenium.By;

public class ElementReader {

    private static JSONObject elements;
    private static final Map<String, By> cache = new ConcurrentHashMap<>();

    static {
        try {
            String path = "src/test/resources/elements/elements.json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            elements = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("elements.json dosyası okunamadı.");
        }
    }

    public static By getLocator(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (!elements.has(key)) {
            throw new RuntimeException("Element bulunamadı: " + key);
        }

        JSONObject element = elements.getJSONObject(key);
        String type = element.getString("type");
        String value = element.getString("value");

        By locator;
        switch (type.toLowerCase()) {
            case "id":
                locator = By.id(value);
                break;
            case "xpath":
                locator = By.xpath(value);
                break;
            case "accessibilityid":
                locator = new AppiumBy.ByAccessibilityId(value);
                break;
            case "classname":
                locator = By.className(value);
                break;
            default:
                throw new RuntimeException("Desteklenmeyen locator türü: " + type);
        }

        cache.put(key, locator);
        return locator;
    }

    public static String getValueOfElement(String key){
        return elements.getJSONObject(key).getString("value");
    }

}
