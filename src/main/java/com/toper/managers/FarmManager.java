// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.Crops;

import java.util.HashSet;
import java.util.Set;

public class FarmManager {
    private final TOper plugin;
    private final Set<Material> farmBlocks = new HashSet<>();

    public FarmManager(TOper plugin) {
        this.plugin = plugin;
        initFarmBlocks();
    }

    private void initFarmBlocks() {
        farmBlocks.add(Material.CROPS);
        farmBlocks.add(Material.CARROT);
        farmBlocks.add(Material.POTATO);
        farmBlocks.add(Material.NETHER_WARTS);
        farmBlocks.add(Material.SUGAR_CANE_BLOCK);
        farmBlocks.add(Material.CACTUS);
        farmBlocks.add(Material.MELON_BLOCK);
        farmBlocks.add(Material.PUMPKIN);
    }

    public boolean isFarmBlock(Block block) {
        return farmBlocks.contains(block.getType());
    }

    public boolean shouldOptimizeGrowth(Block block) {
        if (!isFarmBlock(block)) return false;

        BlockState state = block.getState();
        if (state.getData() instanceof Crops) {
            Crops crops = (Crops) state.getData();
            return !crops.isRipe();
        }
        return true;
    }

    public boolean isOptimalGrowthConditions(Block block) {
        Material type = block.getType();
        Block below = block.getLocation().subtract(0, 1, 0).getBlock();

        switch (type) {
            case CROPS:
            case CARROT:
            case POTATO:
                return below.getType() == Material.SOIL && below.getData() > 0;
            case SUGAR_CANE_BLOCK:
                return below.getType() == Material.SUGAR_CANE_BLOCK || 
                       (below.getType() == Material.GRASS || below.getType() == Material.DIRT) && 
                       hasWaterNearby(block);
            case CACTUS:
                return below.getType() == Material.SAND;
            default:
                return true;
        }
    }

    private boolean hasWaterNearby(Block block) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                Block relative = block.getLocation().add(x, 0, z).getBlock();
                if (relative.getType() == Material.WATER || relative.getType() == Material.STATIONARY_WATER) {
                    return true;
                }
            }
        }
        return false;
    }
}