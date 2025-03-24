// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.managers;

import com.toper.TOper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PerformanceManager {
    private final TOper plugin;
    @Getter
    private double tps;
    private long lastCheck;
    private int tickCount;

    public PerformanceManager(TOper plugin) {
        this.plugin = plugin;
        this.lastCheck = System.currentTimeMillis();
        this.tickCount = 0;
        startMonitoring();
    }

    private void startMonitoring() {
        new BukkitRunnable() {
            @Override
            public void run() {
                calculateTPS();
                checkPerformance();
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    private void calculateTPS() {
        long now = System.currentTimeMillis();
        long diff = now - lastCheck;
        tps = tickCount * 1000.0 / diff;
        tps = Math.min(20.0, tps);
        lastCheck = now;
        tickCount = 0;
    }

    private void checkPerformance() {
        double threshold = plugin.getConfigManager().getTpsAlertThreshold();
        if (tps < threshold) {
            alertAdmins(String.format("§c[tOper] Server TPS dropped to %.1f!", tps));
        }
    }

    private void alertAdmins(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("toper.admin")) {
                player.sendMessage(message);
            }
        }
    }

    public void incrementTickCount() {
        tickCount++;
    }
}