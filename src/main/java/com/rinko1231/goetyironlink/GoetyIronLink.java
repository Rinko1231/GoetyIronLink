package com.rinko1231.goetyironlink;

import com.mojang.logging.LogUtils;

import com.rinko1231.goetyironlink.config.SpellIronPowerConfig;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


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
    public static int getIronBloodPower(LivingEntity caster)
    {
        AttributeInstance attr = caster.getAttribute(AttributeRegistry.BLOOD_SPELL_POWER.get());
        int bloodPower =0;
        if(attr != null)
            bloodPower = (int) attr.getValue();
        return bloodPower;
    }
}
