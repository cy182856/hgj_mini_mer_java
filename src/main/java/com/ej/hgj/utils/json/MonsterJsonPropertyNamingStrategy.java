package com.ej.hgj.utils.json;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public interface MonsterJsonPropertyNamingStrategy {
    PropertyNamingStrategy.PropertyNamingStrategyBase FIRST_LETTER_UPPER_CASE_STRATEGY = FirstLetterUpperCaseNamingStrategy.getInstance();
    PropertyNamingStrategy.PropertyNamingStrategyBase ALL_CHARACTER_UPPERCASE_STRATEGY = AllCharacterUppercaseNameingStrategy.getInstance();
}
