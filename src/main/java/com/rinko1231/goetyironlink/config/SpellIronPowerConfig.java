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

    private static Map<String, Double> spellMultipliers = new HashMap<>();

    static {
        // 默认配置，可自行扩充
        spellMultipliers.put("IceSpikeSpell", 9.0);
        spellMultipliers.put("SpellFrostNova", 0.5);
        spellMultipliers.put("SpellNetherBolt", 0.7);
        spellMultipliers.put("SpellBloodSurge", 0.6);
    }

    public static void load() {
        try {
            File file = CONFIG_PATH.toFile();
            if (!file.exists()) {
                saveDefaults();
                return;
            }
            Type type = new TypeToken<Map<String, Double>>() {}.getType();
            spellMultipliers = GSON.fromJson(new FileReader(file), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveDefaults() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(spellMultipliers, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getMultiplier(String spellName) {
        return spellMultipliers.getOrDefault(spellName, 0.0);
    }

    public static void reload() {
        load();
    }
}
