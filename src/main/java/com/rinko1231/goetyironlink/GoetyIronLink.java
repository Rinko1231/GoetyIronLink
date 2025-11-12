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
        //final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
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

            double ironPower = switch (type.toLowerCase()) {
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
                case "abyssal" -> getCataclysmAbyssalPower(player);
                case "technomancy" -> getCataclysmTechnomancyPower(player);
                default -> getCustomSpellPower(player, type.toLowerCase());
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

    public static double getIronFirePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.FIRE_SPELL_POWER.get());
        double firePower =0;
        if(attr != null)
            firePower = attr.getValue();
        return firePower;
    }
    public static double getIronIcePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ICE_SPELL_POWER.get());
        double icePower =0;
        if(attr != null)
            icePower = attr.getValue();
        return icePower;
    }
    public static double getIronLightningPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.LIGHTNING_SPELL_POWER.get());
        double lightningPower =0;
        if(attr != null)
            lightningPower =  attr.getValue();
        return lightningPower;
    }
    public static double getIronBloodPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.BLOOD_SPELL_POWER.get());
        double bloodPower =0;
        if(attr != null)
            bloodPower =  attr.getValue();
        return bloodPower;
    }
    public static double getIronNaturePower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.NATURE_SPELL_POWER.get());
        double naturePower =0;
        if(attr != null)
            naturePower =  attr.getValue();
        return naturePower;
    }
    public static double getIronEnderPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ENDER_SPELL_POWER.get());
        double enderPower =0;
        if(attr != null)
            enderPower = attr.getValue();
        return enderPower;
    }
    public static double getIronEvocationPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.EVOCATION_SPELL_POWER.get());
        double evocationPower =0;
        if(attr != null)
            evocationPower = attr.getValue();
        return evocationPower;
    }
    public static double getIronEldritchPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.ELDRITCH_SPELL_POWER.get());
        double EldritchPower =0;
        if(attr != null)
            EldritchPower = attr.getValue();
        return EldritchPower;
    }
    public static double getIronHolyPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.HOLY_SPELL_POWER.get());
        double HolyPower =0;
        if(attr != null)
            HolyPower =  attr.getValue();
        return HolyPower;
    }
    public static double getTOAquaPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("traveloptics")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("traveloptics", "aqua_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static double getGTBCGeomancyPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("gtbcs_geomancy_plus")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("gtbcs_geomancy_plus", "geo_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static double getFantasyPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("fantasy_ending")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("fantasy_ending", "fantasy_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static double getCataclysmAbyssalPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("cataclysm_spellbooks")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("cataclysm_spellbooks", "abyssal_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static double getCataclysmTechnomancyPower(LivingEntity caster) {
        if (!ModList.get().isLoaded("cataclysm_spellbooks")) {
            return 0;
        }
        try {
            //从注册表动态获取
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("cataclysm_spellbooks", "technomancy_spell_power"));
            if (attr == null) return 0;

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) return 0;

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }
    public static double getCustomSpellPower(LivingEntity caster, String type) {
        // 假定都在kubejs命名空间算了
        ResourceLocation attrLoc = new ResourceLocation("kubejs", type.toLowerCase() + "_spell_power");

        try {
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(attrLoc);
            if (attr == null) {
                return 0;
            }

            AttributeInstance instance = caster.getAttribute(attr);
            if (instance == null) {
                return 0;
            }

            return instance.getValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
