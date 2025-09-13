package com.ukper.whohitme.hud;

import net.minecraft.world.damagesource.DamageSource;

public class DamageMessage {
    public final DamageSource source;
    public float amount;
    public long creationTime;
    public int id;
    public int count = 1;

    public DamageMessage(DamageSource source, float amount, long creationTime) {
        this.source = source;
        this.amount = amount;
        this.creationTime = creationTime;
    }

    public long getTime() {
        return creationTime;
    }
}
