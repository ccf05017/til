package com.poppo.effective.item13;

import java.util.Comparator;

import static java.util.Comparator.comparingInt;

public final class PhoneNumber implements Cloneable, Comparable<PhoneNumber> {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "지역코드");
        this.prefix = rangeCheck(prefix, 999, "프리픽스");
        this.lineNum = rangeCheck(lineNum, 9999, "가입자 번호");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber) o;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);

        return result;
    }

    // 간단하지만 느린 hashCode 구현 방법
//    @Override
//    public int hashCode() {
//        return Objects.hash(lineNum, prefix, areaCode);
//    }

    // 지연 초기화를 통한 hashCode 구현 방안 (성능 높이기)
//    private int hashCode; // 자동으로 0으로 초기화된다.
//
//    @Override
//    public int hashCode() {
//        int result = hashCode;
//        if (result == 0) {
//            result = Short.hashCode(areaCode);
//            result = 31 * result + Short.hashCode(prefix);
//            result = 31 * result + Short.hashCode(lineNum);
//            hashCode = result;
//        }
//        return result;
//    }


    @Override
    public PhoneNumber clone() {
        try {
            return (PhoneNumber) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public int compareTo(PhoneNumber phoneNumber) {
//        int result = Short.compare(areaCode, phoneNumber.areaCode);
//        if (result == 0) {
//            result = Short.compare(prefix, phoneNumber.prefix);
//            if (result == 0) {
//                result = Short.compare(lineNum, phoneNumber.lineNum);
//            }
//        }
//        return result;
//    }

    private static final Comparator<PhoneNumber> COMPARATOR =
            comparingInt((PhoneNumber phoneNumber) -> phoneNumber.areaCode)
            .thenComparingInt(phoneNumber -> phoneNumber.prefix)
            .thenComparingInt(phoneNumber -> phoneNumber.lineNum);

    @Override
    public int compareTo(PhoneNumber phoneNumber) {
        return COMPARATOR.compare(this, phoneNumber);
    }
}
