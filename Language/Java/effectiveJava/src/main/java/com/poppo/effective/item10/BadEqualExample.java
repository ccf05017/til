package com.poppo.effective.item10;

import java.util.Objects;

public class BadEqualExample {

    // Test 코드 참고
    private final String s;

    public BadEqualExample(String s) {
        this.s = Objects.requireNonNull(s);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BadEqualExample)
            return s.equalsIgnoreCase(((BadEqualExample) obj).s);
        if (obj instanceof String)
            return s.equalsIgnoreCase((String) obj);
        return false;
    }
}
