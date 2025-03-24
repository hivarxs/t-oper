// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class RedstoneManager {
    private final TOper plugin;
    private final Map<String, Integer> updateCount = new HashMap<>();
    private final Map<String, Long> lastUpdate = new HashMap<>();
    private final Map<String, Boolean> farmCircuits = new HashMap<>();

    public RedstoneManager(TOper plugin) {
        this.plugin = plugin;
    }

    public boolean canUpdate(Location location) {
        String key = locationToString(location);
        long now = System.currentTimeMillis();
        int limit = isFarmCircuit(location) ? 
            plugin.getConfigManager().getFarmRedstoneLimit() : 
            plugin.getConfigManager().getRedstoneUpdateLimit();

        if (!lastUpdate.containsKey(key)) {
            lastUpdate.put(key, now);
            updateCount.put(key, 1);
            return true;
        }

        if (now - lastUpdate.get(key) > 1000) {
            lastUpdate.put(key, now);
            updateCount.put(key, 1);
            return true;
        }

        int count = updateCount.get(key);
        if (count >= limit) {
            return false;
        }

        updateCount.put(key, count + 1);
        return true;
    }

    private boolean isFarmCircuit(Location location) {
        String key = locationToString(location);
        if (!farmCircuits.containsKey(key)) {
            farmCircuits.put(key, checkFarmCircuit(location));
        }
        return farmCircuits.get(key);
    }

    private boolean checkFarmCircuit(Location location) {
        Block block = location.getBlock();
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Block relative = block.getRelative(x, y, z);
                    if (plugin.getFarmManager().isFarmBlock(relative)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String locationToString(Location location) {
        return location.getWorld().getName() + "," +
               location.getBlockX() + "," +
               location.getBlockY() + "," +
               location.getBlockZ();
    }
}