package com.ukper.whohitme.enums;

public enum DamageMessageSideHorizontal {
    LEFT("whohitme:left"),
    RIGHT("whohitme:right");

    private final String key;

    DamageMessageSideHorizontal(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


    public DamageMessageSideHorizontal next() {
        DamageMessageSideHorizontal[] values = values();
        int nextOrdinal = (this.ordinal() + 1) % values.length;
        return values[nextOrdinal];
    }
}
