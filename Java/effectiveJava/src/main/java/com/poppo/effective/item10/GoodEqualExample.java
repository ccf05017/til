package com.poppo.effective.item10;

public class GoodEqualExample {

    private final short areaCode, prefiz, lineNum;

    public GoodEqualExample(short areaCode, short prefiz, short lineNum) {
        this.areaCode = areaCode;
        this.prefiz = prefiz;
        this.lineNum = lineNum;
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return  true;
        if (!(obj instanceof GoodEqualExample)) return false;
        GoodEqualExample example = (GoodEqualExample) obj;

        return example.areaCode == areaCode &&
                example.lineNum == lineNum &&
                example.prefiz == prefiz;
    }
}
