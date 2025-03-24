// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.InventoryHolder;

public class InventoryManager {
    private final TOper plugin;

    public InventoryManager(TOper plugin) {
        this.plugin = plugin;
    }

    public boolean canPlaceHopper(Chunk chunk) {
        int maxHoppers = plugin.getConfigManager().getMaxHoppersPerChunk();
        return countHoppers(chunk) < maxHoppers;
    }

    private int countHoppers(Chunk chunk) {
        int count = 0;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < chunk.getWorld().getMaxHeight(); y++) {
                    if (chunk.getBlock(x, y, z).getType() == Material.HOPPER) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void optimizeHopperTransfer(Hopper hopper) {
    }
}