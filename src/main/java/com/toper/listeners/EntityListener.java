// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.listeners;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

@RequiredArgsConstructor
public class EntityListener implements Listener {
    private final TOper plugin;

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!plugin.getEntityManager().canSpawnEntity(event.getLocation().getChunk())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!plugin.getEntityManager().canSpawnEntity(event.getLocation().getChunk())) {
            event.setCancelled(true);
        }
    }
}