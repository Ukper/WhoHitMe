package com.ukper.whohitme.hud;

import com.ukper.whohitme.WhoHitMeConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

public class DamageMessageFormatter {

    public static String getDamageMessageString(DamageMessage message) {
        DamageSource source = message.source;
        float damageGained = message.amount;
        // Отримання ID типу шкоди
        String damageMessageId;
        try {
            damageMessageId = Objects.requireNonNull(source.typeHolder().getKey()).location().getPath();
        }
        catch (Exception ex) {
            damageMessageId = "unknown";
        }

        boolean directEntityExist = source.getDirectEntity() != null;

        MutableComponent textMessage = Component.empty();
        switch (WhoHitMeConfig.style.get()) {
            case PVP -> {
                if(directEntityExist) {
                    textMessage = Component.translatable("pvp.whohitme:basic",
                            source.getDirectEntity().getDisplayName(),
                            Component.translatable("basic.whohitme:" + damageMessageId));
                }
                else {
                    textMessage = Component.translatable("pvp.whohitme:basic",
                            Component.translatable("entity.minecraft.player"),
                            Component.translatable("basic.whohitme:" + damageMessageId));
                }
            }
            case MINIMAL -> {
                if(directEntityExist) {
                    textMessage = Component.translatable("minimal.whohitme:basic",
                            source.getDirectEntity().getDisplayName());
                }
                else {
                    textMessage = Component.translatable("minimal.whohitme:basic",
                            Component.translatable("basic.whohitme:" + damageMessageId));
                }
            }
            case GOOD -> {
                textMessage = Component.translatable("good.whohitme:" + damageMessageId,
                        directEntityExist ? source.getDirectEntity().getDisplayName() : "");
            }
        }

        String finalMessage = textMessage.getString();
        if(WhoHitMeConfig.showDamage.get()) {
            finalMessage += " " + damageGained;
        }
        if(message.count > 1) {
            finalMessage += " (" + message.count + "x)";
        }

        return finalMessage;
    }

    public static boolean areMessagesSimilar(DamageMessage a, DamageMessage b) {
        String idA = Objects.requireNonNull(a.source.typeHolder().getKey()).location().getPath();
        String idB = Objects.requireNonNull(b.source.typeHolder().getKey()).location().getPath();
        if (!idA.equals(idB)) return false;
        // Якщо є directEntity → перевіряємо, що воно таке саме
        Entity entA = a.source.getDirectEntity();
        Entity entB = b.source.getDirectEntity();
        if (entA != null || entB != null) {
            return entA != null && entB != null && entA.getUUID().equals(entB.getUUID());
        }
        return true;
    }
}
