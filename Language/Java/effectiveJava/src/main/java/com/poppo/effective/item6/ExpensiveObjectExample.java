package com.poppo.effective.item6;

import java.util.regex.Pattern;

public class ExpensiveObjectExample {

    private static final Pattern EXAMPLE = Pattern.compile("원하는 정규표현식");

    static boolean isExample(String s) {
        return EXAMPLE.matcher(s).matches();
    }
}
