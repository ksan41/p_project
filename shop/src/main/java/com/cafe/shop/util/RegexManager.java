package com.cafe.shop.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegexManager {

    private static final Map<String, String> CHOSUNG_MAP = new HashMap<>() {
        {
            put("ㄱ", "[가-깋]");
            put("ㄲ", "[까-낗]");
            put("ㄴ", "[나-닣]");
            put("ㄷ", "[다-딯]");
            put("ㄸ", "[따-띻]");
            put("ㄹ", "[라-맇]");
            put("ㅁ", "[마-밓]");
            put("ㅂ", "[바-빟]");
            put("ㅃ", "[빠-삫]");
            put("ㅅ", "[사-싷]");
            put("ㅆ", "[싸-앃]");
            put("ㅇ", "[아-잏]");
            put("ㅈ", "[자-짛]");
            put("ㅉ", "[짜-찧]");
            put("ㅊ", "[차-칳]");
            put("ㅋ", "[카-킿]");
            put("ㅌ", "[타-팋]");
            put("ㅍ", "[파-핗]");
            put("ㅎ", "[하-힣]");
        }
    };

    public static String makeKeywordToRegex(String keyword) {
        StringBuilder sb = new StringBuilder();
        sb.append("^.*");

        for (char c : keyword.toCharArray()) {
            if (!Pattern.matches("[ㄱ-ㅎ]", Character.toString(c))) {
                sb.append(Character.toString(c));
            } else {
                sb.append(CHOSUNG_MAP.get(Character.toString(c)));
            }
        }

        sb.append(".*");
        return sb.toString();
    }

    public static boolean isKeywordMatchesRegex(String keyword, String regex) {
        return Pattern.matches(regex, keyword);
    }

}
