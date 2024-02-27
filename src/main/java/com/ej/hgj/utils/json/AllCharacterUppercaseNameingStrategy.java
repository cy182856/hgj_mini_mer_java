package com.ej.hgj.utils.json;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class AllCharacterUppercaseNameingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    private static AllCharacterUppercaseNameingStrategy strategy = null;

    private AllCharacterUppercaseNameingStrategy() {
    }

    public static synchronized AllCharacterUppercaseNameingStrategy getInstance() {
        if (strategy == null) {
            strategy = new AllCharacterUppercaseNameingStrategy();
        }

        return strategy;
    }

    public String translate(String propertyName) {
        return propertyName.toUpperCase();
    }
}
