// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.listeners;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;

@RequiredArgsConstructor
public class FarmListener implements Listener {
    private final TOper plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockGrow(BlockGrowEvent event) {
        if (!plugin.getConfigManager().isOptimizeFarmsEnabled()) return;

        Block block = event.getBlock();
        if (plugin.getFarmManager().shouldOptimizeGrowth(block) && 
            plugin.getFarmManager().isOptimalGrowthConditions(block)) {
            int multiplier = plugin.getConfigManager().getFarmGrowthMultiplier();
            for (int i = 1; i < multiplier; i++) {
                event.getBlock().getState().update(true, true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockSpread(BlockSpreadEvent event) {
        if (!plugin.getConfigManager().isOptimizeFarmsEnabled()) return;

        Block source = event.getSource();
        if (plugin.getFarmManager().isFarmBlock(source) && 
            plugin.getFarmManager().isOptimalGrowthConditions(source)) {
            int multiplier = plugin.getConfigManager().getFarmGrowthMultiplier();
            for (int i = 1; i < multiplier; i++) {
                event.getBlock().getState().update(true, true);
            }
        }
    }
}