package com.ukper.whohitme.enums;

public enum MessageStyle {
    GOOD("whohitme:good"),
    MINIMAL("whohitme:minimal"),
    PVP("whohitme:pvp");

    private final String key;

    MessageStyle(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public MessageStyle next() {
        MessageStyle[] values = values();
        int nextOrdinal = (this.ordinal() + 1) % values.length;
        return values[nextOrdinal];
    }
}
