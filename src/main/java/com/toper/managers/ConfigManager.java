// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private final TOper plugin;
    @Getter
    private FileConfiguration config;

    public ConfigManager(TOper plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public int getMaxEntitiesPerChunk() {
        return config.getInt("entities.max-per-chunk", 50);
    }

    public int getMaxHoppersPerChunk() {
        return config.getInt("inventory.max-hoppers-per-chunk", 8);
    }

    public int getRedstoneUpdateLimit() {
        return config.getInt("redstone.update-limit", 10);
    }

    public int getFarmRedstoneLimit() {
        return config.getInt("farm.redstone-limit", 20);
    }

    public boolean isClockCircuitsDisabled() {
        return config.getBoolean("redstone.disable-clock-circuits", true);
    }

    public int getClearItemsInterval() {
        return config.getInt("anti-lag.clear-items-interval", 300);
    }

    public double getTpsAlertThreshold() {
        return config.getDouble("monitoring.alert-threshold-tps", 17.0);
    }

    public int getFarmGrowthMultiplier() {
        return config.getInt("farm.growth-multiplier", 2);
    }

    public boolean isOptimizeFarmsEnabled() {
        return config.getBoolean("farm.optimize-enabled", true);
    }
}