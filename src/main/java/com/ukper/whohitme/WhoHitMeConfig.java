package com.ukper.whohitme;

import com.ukper.whohitme.enums.DamageMessageSideHorizontal;
import com.ukper.whohitme.enums.DamageMessageSideVertical;
import com.ukper.whohitme.enums.MessageStyle;
import net.neoforged.neoforge.common.ModConfigSpec;

public class WhoHitMeConfig {
    public static final ModConfigSpec CLIENT;
    public static final ModConfigSpec.EnumValue<MessageStyle> style;
    public static final ModConfigSpec.BooleanValue showDamage;
    public static final ModConfigSpec.EnumValue<DamageMessageSideHorizontal> sideHorizontal;
    public static final ModConfigSpec.EnumValue<DamageMessageSideVertical> sideVertical;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        style = builder
                .comment("Стиль повідомлень")
                .defineEnum("style", MessageStyle.GOOD);

        showDamage = builder
                .comment("Показувати урон")
                .define("showDamage", true);

        sideHorizontal = builder
                .comment("Сторона (Ліво/Право)")
                .defineEnum("sideHorizontal", DamageMessageSideHorizontal.LEFT);

        sideVertical = builder
                .comment("Сторона (Верх/Низ)")
                .defineEnum("sideVertical", DamageMessageSideVertical.BOTTOM);

        CLIENT = builder.build();
    }
}
