// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SetupManager {
    private final TOper plugin;
    private final File dataFile;
    private YamlConfiguration data;
    @Getter
    private final Set<UUID> setupIgnored = new HashSet<>();

    public SetupManager(TOper plugin) {
        this.plugin = plugin;
        this.dataFile = new File(plugin.getDataFolder(), "data.yml");
        loadData();
    }

    private void loadData() {
        if (!dataFile.exists()) {
            plugin.saveResource("data.yml", false);
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
        
        if (data.contains("setup-ignored")) {
            data.getStringList("setup-ignored").forEach(uuid -> 
                setupIgnored.add(UUID.fromString(uuid)));
        }
    }

    public void saveData() {
        data.set("setup-ignored", setupIgnored.stream()
            .map(UUID::toString)
            .toList());
        try {
            data.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data file!");
        }
    }

    public boolean hasCompletedSetup(Player player) {
        return setupIgnored.contains(player.getUniqueId());
    }

    public void ignoreSetup(Player player) {
        setupIgnored.add(player.getUniqueId());
        saveData();
    }

    public void startSetup(Player player) {
        player.sendMessage(ChatColor.GREEN + "=== tOper Setup Wizard ===");
        player.sendMessage(ChatColor.YELLOW + "Let's configure your server optimization settings!");
        
        // Entity Settings
        player.sendMessage(ChatColor.AQUA + "\nEntity Settings:");
        player.sendMessage(ChatColor.WHITE + "Current max entities per chunk: " + 
            plugin.getConfigManager().getMaxEntitiesPerChunk());
        
        // Farm Settings
        player.sendMessage(ChatColor.AQUA + "\nFarm Settings:");
        player.sendMessage(ChatColor.WHITE + "Farm optimization: " + 
            (plugin.getConfigManager().isOptimizeFarmsEnabled() ? "Enabled" : "Disabled"));
        player.sendMessage(ChatColor.WHITE + "Growth multiplier: " + 
            plugin.getConfigManager().getFarmGrowthMultiplier());
        
        // Redstone Settings
        player.sendMessage(ChatColor.AQUA + "\nRedstone Settings:");
        player.sendMessage(ChatColor.WHITE + "Update limit: " + 
            plugin.getConfigManager().getRedstoneUpdateLimit());
        player.sendMessage(ChatColor.WHITE + "Farm circuit limit: " + 
            plugin.getConfigManager().getFarmRedstoneLimit());
        
        player.sendMessage(ChatColor.GREEN + "\nUse the following commands to adjust settings:");
        player.sendMessage(ChatColor.YELLOW + "/toper set <setting> <value>");
        player.sendMessage(ChatColor.YELLOW + "/toper toggle <setting>");
        player.sendMessage(ChatColor.YELLOW + "/toper ignore-setup " + ChatColor.GRAY + 
            "- Skip this setup in the future");
    }
}