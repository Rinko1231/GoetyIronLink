package com.rinko1231.goetyironlink.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SpellIronPowerConfig {
    private static final Gson GSON = new Gson();
    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("goety_iron_spell_power.json");

    public static class PowerData {
        public String type;
        public double multiplier;

        public PowerData(String type, double multiplier) {
            this.type = type;
            this.multiplier = multiplier;
        }
    }

    private static Map<String, PowerData> spellPowerMap = new HashMap<>();

    static {
        // 默认配置
        spellPowerMap.put("ZombieSpell", new PowerData("blood", 0.6));
        spellPowerMap.put("IceSpikeSpell", new PowerData("ice", 0.6));
        spellPowerMap.put("FrostNovaSpell", new PowerData("ice", 0.6));
        spellPowerMap.put("HuntingSpell", new PowerData("nature", 0.6));
    }

    public static void load() {
        try {
            File file = CONFIG_PATH.toFile();
            if (!file.exists()) {
                saveDefaults();
                return;
            }
            Type type = new TypeToken<Map<String, PowerData>>() {}.getType();
            spellPowerMap = GSON.fromJson(new FileReader(file), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveDefaults() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(spellPowerMap, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getMultiplier(String spellName) {
        PowerData data = spellPowerMap.get(spellName);
        return data != null ? data.multiplier : 0.0;
    }

    public static String getType(String spellName) {
        PowerData data = spellPowerMap.get(spellName);
        return data != null ? data.type : null;
    }

    public static void reload() {
        load();
    }
}
