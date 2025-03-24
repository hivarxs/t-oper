// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class EntityManager {
    private final TOper plugin;
    private final List<EntityType> excludedTypes = Arrays.asList(
        EntityType.PLAYER,
        EntityType.ITEM_FRAME,
        EntityType.PAINTING
    );

    public EntityManager(TOper plugin) {
        this.plugin = plugin;
    }

    public boolean canSpawnEntity(Chunk chunk) {
        int maxEntities = plugin.getConfigManager().getMaxEntitiesPerChunk();
        int currentEntities = countEntities(chunk);
        return currentEntities < maxEntities;
    }

    private int countEntities(Chunk chunk) {
        return (int) Arrays.stream(chunk.getEntities())
            .filter(entity -> !excludedTypes.contains(entity.getType()))
            .count();
    }

    public void limitEntities(Chunk chunk) {
        int maxEntities = plugin.getConfigManager().getMaxEntitiesPerChunk();
        Entity[] entities = chunk.getEntities();
        
        if (entities.length <= maxEntities) {
            return;
        }

        Arrays.stream(entities)
            .filter(entity -> !excludedTypes.contains(entity.getType()))
            .skip(maxEntities)
            .forEach(Entity::remove);
    }
}