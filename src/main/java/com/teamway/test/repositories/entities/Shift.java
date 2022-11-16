package com.teamway.test.repositories.entities;

public enum Shift {
    FIRST_SHIFT(8, 16),
    SECOND_SHIFT(16, 24),
    THIRD_SHIFT(0, 8);

    private int startHour;
    private int endHour;

    private Shift(int startHour, int endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
