// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.listeners;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerListener implements Listener {
    private final TOper plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("toper.admin") && 
            !plugin.getSetupManager().hasCompletedSetup(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "=== Welcome to tOper ===");
            event.getPlayer().sendMessage(ChatColor.YELLOW + "It looks like this is your first time using tOper!");
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Use " + ChatColor.WHITE + "/toper setup" + 
                ChatColor.YELLOW + " to configure the plugin.");
            event.getPlayer().sendMessage(ChatColor.YELLOW + "Or use " + ChatColor.WHITE + "/toper ignore-setup" + 
                ChatColor.YELLOW + " to hide this message.");
        }
    }
}