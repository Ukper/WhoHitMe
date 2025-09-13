package com.ukper.whohitme.mixin;

import com.ukper.whohitme.hud.DamageMessage;
import com.ukper.whohitme.hud.DamageMessageRenderer;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mixin(LivingEntity.class)
public class PlayerHitMixin {
    @Unique
    private static final Map<UUID, Map<String, Long>> whoHitMe$lastDamageTimes = new HashMap<>();
    @Unique
    private static final Map<String, Integer> whoHitMe$damageWithRecharging = new HashMap<>();

    @Inject(method = "hurt", at = @At("HEAD"))
    private void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            long now = System.currentTimeMillis();

            // Беремо внутрішню мапу для гравця
            Map<String, Long> playerDamageMap = whoHitMe$lastDamageTimes.computeIfAbsent(player.getUUID(), id -> new HashMap<>());

            long lastTime = playerDamageMap.getOrDefault(source.getMsgId(), 0L);
            int cooldown = whoHitMe$damageWithRecharging.getOrDefault(Objects.requireNonNull(source.typeHolder().getKey()).location().getPath(), 0);

            float armorValue = player.getArmorValue();
            float armorToughness = (float) player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

            float damage = CombatRules.getDamageAfterAbsorb(player, amount, source, armorValue, armorToughness);

            // Наприклад: не частіше ніж раз на 500 мс
            if(now - lastTime > cooldown) {
                DamageMessageRenderer.addDamageMessage(new DamageMessage(source, Math.round(damage * 10f) / 10f, now));
                playerDamageMap.put(source.getMsgId(), now);
            }
        }
    }

    static {
        whoHitMe$damageWithRecharging.put("in_fire", 500);
        whoHitMe$damageWithRecharging.put("on_fire", 500);
        whoHitMe$damageWithRecharging.put("lava", 500);
        whoHitMe$damageWithRecharging.put("hot_floor", 500);
        whoHitMe$damageWithRecharging.put("campfire", 500);
        whoHitMe$damageWithRecharging.put("starve", 4000);
        whoHitMe$damageWithRecharging.put("magic", 1250);
        whoHitMe$damageWithRecharging.put("wither", 1000);
        whoHitMe$damageWithRecharging.put("cactus", 500);
        whoHitMe$damageWithRecharging.put("sweet_berry_bush", 500);
        whoHitMe$damageWithRecharging.put("mob_attack", 125);
        whoHitMe$damageWithRecharging.put("in_wall", 1500);
        whoHitMe$damageWithRecharging.put("out_of_world", 1500);
    }
}