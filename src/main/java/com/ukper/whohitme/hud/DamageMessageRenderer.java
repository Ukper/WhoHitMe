package com.ukper.whohitme.hud;

import com.ukper.whohitme.WhoHitMeConfig;
import com.ukper.whohitme.enums.DamageMessageSideHorizontal;
import com.ukper.whohitme.enums.DamageMessageSideVertical;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import java.util.*;

public class DamageMessageRenderer {
    public static final int SCREEN_HEIGHT = Minecraft.getInstance().getWindow().getGuiScaledHeight();
    public static final int MESSAGE_HEIGHT = 16;
    private static final List<DamageMessage> damageMessages = new ArrayList<>();
    private static final Minecraft mc = Minecraft.getInstance();
    private static final int lifetimeMS = 3000;
    private static final int slideMS = 300;

    public static void render(GuiGraphics graphics) {
        long now = System.currentTimeMillis();
        deleteOldMessages();
        boolean isTop = WhoHitMeConfig.sideVertical.get() == DamageMessageSideVertical.TOP;
        int yOffset = isTop ? 5 : SCREEN_HEIGHT - 15 - MESSAGE_HEIGHT;

        // It`s to avoid crash by ConcurrentModificationException
        List<DamageMessage> finalMessages = new ArrayList<>(damageMessages);

        // Sort messages to better view
        finalMessages.sort(Comparator.comparingLong(DamageMessage::getTime).reversed());

        for (DamageMessage message : finalMessages) {
            String text = DamageMessageFormatter.getDamageMessageString(message);

            // Calculate slide animation
            float slideProgress = calculateSlideProgress(now, message.creationTime);
            float alpha = calculateAlpha(now, message.creationTime);

            // Position calculation
            int width = mc.font.width(text) + 16;
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            boolean isLeft = WhoHitMeConfig.sideHorizontal.get() == DamageMessageSideHorizontal.LEFT;

            // Calculate slide position
            int baseX = isLeft ? -width : screenWidth;
            int targetX = isLeft ? 0 : screenWidth - width;
            int x = (int)Mth.lerp(slideProgress, baseX, targetX);
            int y = yOffset;

            // Draw background
            int bgColor = (((int)(alpha * 180)) << 24) | 0x101010;
            graphics.fill(x, y, x + width, y + MESSAGE_HEIGHT, bgColor);

            // Draw text
            int textColor = (((int)(alpha * 255)) << 24) | 0xFFFFFF;
            graphics.drawString(mc.font, text, x + 8, y + 4, textColor, true);

            if (isTop) {
                yOffset += MESSAGE_HEIGHT + 2;
            }
            else {
                yOffset -= MESSAGE_HEIGHT + 2;
            }
        }
    }


    private static float calculateSlideProgress(long now, long creationTime) {
        long age = now - creationTime;
        if (age < slideMS) {
            return (float) age / slideMS;
        } else if (age > lifetimeMS - slideMS) {
            return 1.0f - (float)(age - (lifetimeMS - slideMS)) / slideMS;
        }
        return 1.0f;
    }

    private static float calculateAlpha(long now, long creationTime) {
        long age = now - creationTime;
        if (age < slideMS) {
            return (float) age / slideMS;
        } else if (age > lifetimeMS - slideMS) {
            return 1.0f - (float)(age - (lifetimeMS - slideMS)) / slideMS;
        }
        return 1.0f;
    }

    public static void addDamageMessage(DamageMessage message) {
        for (DamageMessage existing : damageMessages) {
            if (DamageMessageFormatter.areMessagesSimilar(existing, message)) {
                existing.creationTime = System.currentTimeMillis() - 300;
                existing.amount += message.amount;
                existing.count++;
                return;
            }
        }

        damageMessages.forEach(msg -> msg.id++);
        message.id = 0;
        damageMessages.add(message);
    }

    public static void deleteOldMessages() {
        long now = System.currentTimeMillis();
        damageMessages.removeIf(msg -> msg.creationTime + lifetimeMS <= now);

        // If there are many messages it can crash
        Map<Long, DamageMessage> messageWithTime = new HashMap<>();
        while (damageMessages.size() > 5) {
            for(DamageMessage message : damageMessages) {
                messageWithTime.put(System.currentTimeMillis() - message.creationTime, message);
            }
            long oldestTime = messageWithTime.keySet().stream().min(Long::compareTo).orElse(0L);
            damageMessages.remove(messageWithTime.get(oldestTime));
            messageWithTime.clear();
        }
    }
}