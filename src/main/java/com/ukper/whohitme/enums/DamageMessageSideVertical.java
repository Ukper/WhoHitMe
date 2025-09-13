package com.ukper.whohitme.enums;

public enum DamageMessageSideVertical {
    TOP("whohitme:top"),
    BOTTOM("whohitme:bottom");

    private final String key;

    DamageMessageSideVertical(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public DamageMessageSideVertical next() {
        DamageMessageSideVertical[] values = values();
        int nextOrdinal = (this.ordinal() + 1) % values.length;
        return values[nextOrdinal];
    }
}
