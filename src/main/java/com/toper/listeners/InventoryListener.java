// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.listeners;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

@RequiredArgsConstructor
public class InventoryListener implements Listener {
    private final TOper plugin;

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.HOPPER) {
            if (!plugin.getInventoryManager().canPlaceHopper(event.getBlock().getChunk())) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§c[tOper] Maximum number of hoppers reached in this chunk!");
            }
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (event.getSource().getHolder() instanceof org.bukkit.block.Hopper) {
            plugin.getInventoryManager().optimizeHopperTransfer((org.bukkit.block.Hopper) event.getSource().getHolder());
        }
    }
}