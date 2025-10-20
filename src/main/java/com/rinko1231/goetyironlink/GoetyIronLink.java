package com.rinko1231.goetyironlink;

import com.Polarice3.Goety.api.items.magic.IFocus;
import com.Polarice3.Goety.api.magic.ISpell;
import com.mojang.logging.LogUtils;

import com.rinko1231.goetyironlink.config.SpellIronPowerConfig;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;


import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;


@Mod("goetyironlink")
public class GoetyIronLink {

    private static final Logger LOGGER = LogUtils.getLogger();


    public GoetyIronLink() {
        // 注册事件总线 (Event Bus)
        SpellIronPowerConfig.load();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAddReloadListener(AddReloadListenerEvent event) {
        // 当数据包/资源包刷新时重新加载配置
        SpellIronPowerConfig.reload();
        System.out.println("[GoetyAddon] SpellIronPowerConfig reloaded!");
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        if(player==null) return;

        // 只处理 MagicFocus/IFocus
        if (stack.getItem() instanceof IFocus focus) {
            ISpell spell = focus.getSpell();
            if (spell == null) return;

            String spellName = spell.getClass().getSimpleName();
            String type = SpellIronPowerConfig.getType(spellName);
            double multiplier = SpellIronPowerConfig.getMultiplier(spellName);

            if (type == null || multiplier <= 0) return; // 未配置，跳过

            int ironPower = switch (type.toLowerCase()) {
                case "fire" -> getIronFirePower(player);
                case "ice" -> getIronIcePower(player);
                case "lightning" -> getIronLightningPower(player);
                case "blood" -> getIronBloodPower(player);
                case "nature" -> getIronNaturePower(player);
                case "ender" -> getIronEnderPower(player);
                case "evocation" -> getIronEvocationPower(player);
                case "eldritch" -> getIronEldritchPower(player);
                case "holy" -> getIronHolyPower(player);
                case "aqua" -> getTOAquaPower(player);
                case "geo" -> getGTBCGeomancyPower(player);
                case "fantasy" -> getFantasyPower(player);
                default -> 0;
            };

            int bonus = (int) Math.round(ironPower * multiplier);

            boolean altPressed = Screen.hasAltDown();

            // 本地化流派名
            if (altPressed) {
                // 显示完整信息
                Component typeName = Component.translatable("goetyironlink.spelltype." + type.toLowerCase());
                // 添加本地化 tooltip
                event.getToolTip().add(Component.translatable("tooltip.goetyironlink.spell_bonus", typeName.getString()));
                event.getToolTip().add(Component.translatable("tooltip.goetyironlink.multiplier", String.format("%.2f", multiplier)));
                event.getToolTip().add(Component.translatable("tooltip.goetyironlink.potency_bonus", bonus));
            }
            else {
                event.getToolTip().add(Component.translatable("tooltip.goetyironlink.hold_alt"));
            }
        }
    }

    public static int getIronFirePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.FIRE_SPELL_POWER.get());
        int firePower =0;
        if(attr != null)
            firePower = (int) attr.getValue();
        return firePower;
    }
    public static int getIronIcePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ICE_SPELL_POWER.get());
        int icePower =0;
        if(attr != null)
            icePower = (int) attr.getValue();
        return icePower;
    }
    public static int getIronLightningPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.LIGHTNING_SPELL_POWER.get());
        int lightningPower =0;
        if(attr != null)
            lightningPower = (int) attr.getValue();
        return lightningPower;
    }
    public static int getIronBloodPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.BLOOD_SPELL_POWER.get());
        int bloodPower =0;
        if(attr != null)
            bloodPower = (int) attr.getValue();
        return bloodPower;
    }
    public static int getIronNaturePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.NATURE_SPELL_POWER.get());
        int naturePower =0;
        if(attr != null)
            naturePower = (int) attr.getValue();
        return naturePower;
    }
    public static int getIronEnderPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ENDER_SPELL_POWER.get());
        int enderPower =0;
        if(attr != null)
            enderPower = (int) attr.getValue();
        return enderPower;
    }
    public static int getIronEvocationPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.EVOCATION_SPELL_POWER.get());
        int evocationPower =0;
        if(attr != null)
            evocationPower = (int) attr.getValue();
        return evocationPower;
    }
    public static int getIronEldritchPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ELDRITCH_SPELL_POWER.get());
        int EldritchPower =0;
        if(attr != null)
            EldritchPower = (int) attr.getValue();
        return EldritchPower;
    }
    public static int getIronHolyPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.HOLY_SPELL_POWER.get());
        int HolyPower =0;
        if(attr != null)
            HolyPower = (int) attr.getValue();
        return HolyPower;
    }
    public static int getTOAquaPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("traveloptics")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("traveloptics", "aqua_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return (int) instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static int getGTBCGeomancyPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("gtbcs_geomancy_plus")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("gtbcs_geomancy_plus", "geo_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return (int) instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static int getFantasyPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("fantasy_ending")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("fantasy_ending", "fantasy_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return (int) instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
}
