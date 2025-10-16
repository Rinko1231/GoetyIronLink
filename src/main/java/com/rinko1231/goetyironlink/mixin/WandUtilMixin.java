package com.rinko1231.goetyironlink.mixin;


import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.utils.WandUtil;
import com.rinko1231.goetyironlink.config.SpellIronPowerConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.rinko1231.goetyironlink.GoetyIronLink.*;



@Mixin(value = WandUtil.class, remap = false)
public class WandUtilMixin {

    @Inject(method = "getLevels", at = @At("HEAD"), cancellable = true)
    private static void injectIronSpellbookSynergyAtHead(Enchantment enchantment, LivingEntity livingEntity, CallbackInfoReturnable<Integer> cir) {
        // 先执行原逻辑
        int baseLevel = WandUtil.enchantedFocus(livingEntity)
                ? WandUtil.findFocus(livingEntity).getEnchantmentLevel(enchantment)
                : 0;

        // 仅对 POTENCY 附魔生效
        if (enchantment == ModEnchantments.POTENCY.get()) {
            if(livingEntity instanceof ServerPlayer sp) sp.displayClientMessage(Component.literal("Potency detected"), false);

            ISpell spell = WandUtil.getSpell(livingEntity);
            if (spell != null) {
                if(livingEntity instanceof ServerPlayer sp) sp.displayClientMessage(Component.literal("Spell not null"), false);

                String spellName = spell.getClass().getSimpleName();
                if(livingEntity instanceof ServerPlayer sp) sp.displayClientMessage(Component.literal(spellName), false);

                double multiplier = SpellIronPowerConfig.getMultiplier(spellName);
                if(livingEntity instanceof ServerPlayer sp) sp.displayClientMessage(Component.literal("Multiplier: "+multiplier), false);

                // 若配置文件中未定义此法术系数，则无加成
                if (multiplier > 0 && livingEntity.hasEffect(MobEffects.GLOWING)) {
                    double ironPower = switch (spell.getSpellType()) {
                        case FROST -> getIronIcePower(livingEntity);
                        case NETHER -> getIronFirePower(livingEntity);
                        case NECROMANCY -> getIronBloodPower(livingEntity);
                        default -> 0;
                    };

                    int bonus = (int) Math.round(ironPower * multiplier);
                    if(livingEntity instanceof ServerPlayer sp) sp.displayClientMessage(Component.literal("Bonus: " + bonus), false);

                    baseLevel += bonus;
                }
            }
        }

        // 设置最终返回值
        cir.setReturnValue(baseLevel);
    }

}
