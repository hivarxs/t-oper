// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.listeners;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

@RequiredArgsConstructor
public class InventoryListener implements Listener {
    private final TOper plugin;

    @EventHandler
    public void onRedstoneUpdate(BlockRedstoneEvent event) {
        if (!plugin.getRedstoneManager().canUpdate(event.getBlock().getLocation())) {
            event.setNewCurrent(event.getOldCurrent());
        }
    }
}