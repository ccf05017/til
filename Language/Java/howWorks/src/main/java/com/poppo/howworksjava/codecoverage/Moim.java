package com.poppo.howworksjava.codecoverage;

public class Moim {

    // 바이트코드 조작 예시용 class
    int maxNumberOfAttendees;
    int numberOfCurrentEnrolled;

    private Moim(final int max, final int current) {
        this.maxNumberOfAttendees = max;
        this.numberOfCurrentEnrolled = current;
    }

    public static Moim createMoim(final int max, final int current) {
        return new Moim(max, current);
    }

    public boolean isEnrollmentFull() {
        if (maxNumberOfAttendees == 0) {
            return false;
        }
        if (numberOfCurrentEnrolled < maxNumberOfAttendees) {
            return false;
        }
        return true;
    }
}
