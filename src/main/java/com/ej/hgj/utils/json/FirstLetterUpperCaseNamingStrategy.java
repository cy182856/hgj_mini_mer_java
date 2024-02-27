package com.ej.hgj.utils.json;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class FirstLetterUpperCaseNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    private static FirstLetterUpperCaseNamingStrategy strategy = null;

    private FirstLetterUpperCaseNamingStrategy() {
    }

    public static synchronized FirstLetterUpperCaseNamingStrategy getInstance() {
        if (strategy == null) {
            strategy = new FirstLetterUpperCaseNamingStrategy();
        }

        return strategy;
    }

    public String translate(String propertyName) {
        StringBuffer sb = new StringBuffer(propertyName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }
}
