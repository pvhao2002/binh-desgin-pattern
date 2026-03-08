package com.db.service.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton: single shared configuration holder for the application.
 */
public final class ApplicationConfig {

    private static volatile ApplicationConfig instance;
    private final Map<String, String> settings = new HashMap<>();

    private ApplicationConfig() {
        settings.put("app.name", "student-management-service");
        settings.put("api.version", "1");
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            synchronized (ApplicationConfig.class) {
                if (instance == null) {
                    instance = new ApplicationConfig();
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        return settings.get(key);
    }

    public void set(String key, String value) {
        settings.put(key, value);
    }
}
