package com.ej.hgj.utils;

import org.apache.commons.lang3.StringUtils;

public abstract class ValidateUtils {
    public ValidateUtils() {
    }

    public static boolean notNullAndLessEqualThanMaxLength(String str, int maxLength) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return str.getBytes().length <= maxLength;
        }
    }

    public static boolean notNullAndEqualThanRequiredLength(String str, int requiredLength) {
        if (StringUtils.isBlank(str)) {
            return false;
        } else {
            return str.getBytes().length == requiredLength;
        }
    }

    public static boolean lessEqualThanMaxLengthIfNotNull(String str, int maxLength) {
        if (StringUtils.isBlank(str)) {
            return true;
        } else {
            return str.getBytes().length <= maxLength;
        }
    }

    public static boolean isLegalLengthIfNotNull(String str, int minLength, int maxLength) {
        if (StringUtils.isBlank(str)) {
            return true;
        } else {
            return str.getBytes().length >= minLength && str.getBytes().length <= maxLength;
        }
    }

    public static boolean equalThanRequiredLengthIfNotNull(String str, int maxLength) {
        if (StringUtils.isBlank(str)) {
            return true;
        } else {
            return str.getBytes().length == maxLength;
        }
    }

    public static boolean isStrValueMatched(String target, String str) {
        return StringUtils.isBlank(target) && StringUtils.isBlank(str) ? true : StringUtils.equals(target, str);
    }

    public static void main(String[] args) {
        System.out.println(isLegalLengthIfNotNull("夏丽勇下", 12, 36));
    }
}
