package com.rinko1231.goetyironlink.mixin;


import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.utils.WandUtil;
import com.rinko1231.goetyironlink.config.SpellIronPowerConfig;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
        int baseLevel = WandUtil.enchantedFocus(livingEntity)
                ? WandUtil.findFocus(livingEntity).getEnchantmentLevel(enchantment)
                : 0;

        if (enchantment == ModEnchantments.POTENCY.get() && baseLevel>=1 && livingEntity instanceof ServerPlayer sp) {

            ISpell spell = WandUtil.getSpell(livingEntity);
            if (spell != null) {
                String spellName = spell.getClass().getSimpleName();
                String type = SpellIronPowerConfig.getType(spellName);
                double multiplier = SpellIronPowerConfig.getMultiplier(spellName);

                if (type == null || multiplier <= 0) {
                    //写错或没定义不加成
                    cir.setReturnValue(baseLevel);
                    return;
                }

                ItemStack spellbook = Utils.getPlayerSpellbookStack(sp);
                if (spellbook == null) {
                    cir.setReturnValue(baseLevel);
                    return;
                }

                double ironPower = switch (type.toLowerCase()) {
                    case "fire" -> getIronFirePower(livingEntity);
                    case "ice" -> getIronIcePower(livingEntity);
                    case "lightning" -> getIronLightningPower(livingEntity);
                    case "blood" -> getIronBloodPower(livingEntity);
                    case "nature" -> getIronNaturePower(livingEntity);
                    case "ender" -> getIronEnderPower(livingEntity);
                    case "evocation" -> getIronEvocationPower(livingEntity);
                    case "eldritch" -> getIronEldritchPower(livingEntity);
                    case "holy" -> getIronHolyPower(livingEntity);
                    case "aqua" -> getTOAquaPower(livingEntity);
                    case "geo" -> getGTBCGeomancyPower(livingEntity);
                    case "fantasy" -> getFantasyPower(livingEntity);
                    case "abyssal" -> getCataclysmAbyssalPower(livingEntity);
                    case "technomancy" -> getCataclysmTechnomancyPower(livingEntity);
                    default -> getCustomSpellPower(livingEntity, type.toLowerCase());
                };

                int bonus = Math.max(0, (int) Math.round(ironPower * multiplier));
                cir.setReturnValue(baseLevel + bonus);
                return;
            }
        }

        cir.setReturnValue(baseLevel);
    }
}
