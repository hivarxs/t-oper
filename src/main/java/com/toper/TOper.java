// Mashed together by NotJunar
// terrible quality code ikr

package com.toper;

import com.toper.managers.*;
import com.toper.listeners.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class TOper extends JavaPlugin {
    @Getter
    private static TOper instance;

    @Getter
    private ConfigManager configManager;
    @Getter
    private PerformanceManager performanceManager;
    @Getter
    private EntityManager entityManager;
    @Getter
    private RedstoneManager redstoneManager;
    @Getter
    private InventoryManager inventoryManager;
    @Getter
    private FarmManager farmManager;
    @Getter
    private SetupManager setupManager;

    @Override
    public void onEnable() {
        instance = this;
        this.configManager = new ConfigManager(this);
        this.performanceManager = new PerformanceManager(this);
        this.entityManager = new EntityManager(this);
        this.redstoneManager = new RedstoneManager(this);
        this.inventoryManager = new InventoryManager(this);
        this.farmManager = new FarmManager(this);
        this.setupManager = new SetupManager(this);

        getServer().getPluginManager().registerEvents(new EntityListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new RedstoneListener(this), this);
        getServer().getPluginManager().registerEvents(new FarmListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        getCommand("toper").setExecutor(new TOperCommand(this));

        getLogger().info("tOper has been enabled!");
    }

    @Override
    public void onDisable() {
        if (setupManager != null) {
            setupManager.saveData();
        }
        getLogger().info("tOper has been disabled!");
    }
}