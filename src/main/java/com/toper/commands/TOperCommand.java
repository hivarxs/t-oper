// Mashed together by NotJunar
// terrible quality code ikr

package com.toper.commands;

import com.toper.TOper;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TOperCommand implements CommandExecutor, TabCompleter {
    private final TOper plugin;
    private final List<String> SETTINGS = Arrays.asList(
        "entities-per-chunk",
        "hoppers-per-chunk",
        "redstone-limit",
        "farm-redstone-limit",
        "growth-multiplier"
    );
    private final List<String> TOGGLES = Arrays.asList(
        "farm-optimization",
        "clock-circuits",
        "mob-ai-limit"
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("toper.admin")) {
            sender.sendMessage(ChatColor.RED + "[tOper] You don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            showHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.getConfigManager().loadConfig();
                sender.sendMessage(ChatColor.GREEN + "[tOper] Configuration reloaded!");
                break;
            case "status":
                showStatus(sender);
                break;
            case "setup":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[tOper] This command can only be used by players!");
                    return true;
                }
                plugin.getSetupManager().startSetup((Player) sender);
                break;
            case "ignore-setup":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "[tOper] This command can only be used by players!");
                    return true;
                }
                plugin.getSetupManager().ignoreSetup((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "[tOper] Setup wizard will no longer show on join.");
                break;
            case "set":
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "[tOper] Usage: /toper set <setting> <value>");
                    return true;
                }
                handleSetCommand(sender, args[1], args[2]);
                break;
            case "toggle":
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "[tOper] Usage: /toper toggle <setting>");
                    return true;
                }
                handleToggleCommand(sender, args[1]);
                break;
            default:
                showHelp(sender);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission("toper.admin")) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            return Arrays.asList("reload", "status", "setup", "ignore-setup", "set", "toggle")
                .stream()
                .filter(s -> s.startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "set":
                    return SETTINGS.stream()
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
                case "toggle":
                    return TOGGLES.stream()
                        .filter(s -> s.startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        return new ArrayList<>();
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "=== tOper Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/toper reload " + ChatColor.GRAY + "- Reload the configuration");
        sender.sendMessage(ChatColor.YELLOW + "/toper status " + ChatColor.GRAY + "- Show current performance status");
        sender.sendMessage(ChatColor.YELLOW + "/toper setup " + ChatColor.GRAY + "- Start the setup wizard");
        sender.sendMessage(ChatColor.YELLOW + "/toper set <setting> <value> " + ChatColor.GRAY + "- Change a setting");
        sender.sendMessage(ChatColor.YELLOW + "/toper toggle <setting> " + ChatColor.GRAY + "- Toggle a feature");
        sender.sendMessage(ChatColor.YELLOW + "/toper ignore-setup " + ChatColor.GRAY + "- Disable setup wizard");
    }

    private void showStatus(CommandSender sender) {
        double tps = plugin.getPerformanceManager().getTps();
        sender.sendMessage(ChatColor.GREEN + "=== tOper Status ===");
        sender.sendMessage(String.format(ChatColor.YELLOW + "TPS: %.1f", tps));
        sender.sendMessage(String.format(ChatColor.YELLOW + "Memory: %d/%d MB", 
            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576,
            Runtime.getRuntime().maxMemory() / 1048576));
        sender.sendMessage(ChatColor.YELLOW + "Farm Optimization: " + 
            (plugin.getConfigManager().isOptimizeFarmsEnabled() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
        sender.sendMessage(ChatColor.YELLOW + "Growth Multiplier: " + ChatColor.WHITE + 
            plugin.getConfigManager().getFarmGrowthMultiplier());
    }

    private void handleSetCommand(CommandSender sender, String setting, String value) {
        try {
            switch (setting.toLowerCase()) {
                case "entities-per-chunk":
                    int entityLimit = Integer.parseInt(value);
                    plugin.getConfigManager().getConfig().set("entities.max-per-chunk", entityLimit);
                    break;
                case "hoppers-per-chunk":
                    int hopperLimit = Integer.parseInt(value);
                    plugin.getConfigManager().getConfig().set("inventory.max-hoppers-per-chunk", hopperLimit);
                    break;
                case "redstone-limit":
                    int redstoneLimit = Integer.parseInt(value);
                    plugin.getConfigManager().getConfig().set("redstone.update-limit", redstoneLimit);
                    break;
                case "farm-redstone-limit":
                    int farmRedstoneLimit = Integer.parseInt(value);
                    plugin.getConfigManager().getConfig().set("farm.redstone-limit", farmRedstoneLimit);
                    break;
                case "growth-multiplier":
                    int growthMultiplier = Integer.parseInt(value);
                    plugin.getConfigManager().getConfig().set("farm.growth-multiplier", growthMultiplier);
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "[tOper] Unknown setting: " + setting);
                    return;
            }
            plugin.getConfigManager().loadConfig();
            sender.sendMessage(ChatColor.GREEN + "[tOper] Setting updated successfully!");
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "[tOper] Invalid value! Please enter a number.");
        }
    }

    private void handleToggleCommand(CommandSender sender, String setting) {
        switch (setting.toLowerCase()) {
            case "farm-optimization":
                boolean farmOptEnabled = !plugin.getConfigManager().isOptimizeFarmsEnabled();
                plugin.getConfigManager().getConfig().set("farm.optimize-enabled", farmOptEnabled);
                break;
            case "clock-circuits":
                boolean clockCircuits = !plugin.getConfigManager().isClockCircuitsDisabled();
                plugin.getConfigManager().getConfig().set("redstone.disable-clock-circuits", clockCircuits);
                break;
            case "mob-ai-limit":
                boolean mobAiLimit = !plugin.getConfigManager().getConfig().getBoolean("anti-lag.limit-mob-ai", true);
                plugin.getConfigManager().getConfig().set("anti-lag.limit-mob-ai", mobAiLimit);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "[tOper] Unknown setting: " + setting);
                return;
        }
        plugin.getConfigManager().loadConfig();
        sender.sendMessage(ChatColor.GREEN + "[tOper] Setting toggled successfully!");
    }
}