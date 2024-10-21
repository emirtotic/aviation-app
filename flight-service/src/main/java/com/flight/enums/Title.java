package com.flight.enums;

public enum Title {

    MR("Mr."),
    MRS("Mrs."),
    MS("Ms."),
    DR("Dr."),
    PROF("Prof."),
    MISS("Miss");

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
