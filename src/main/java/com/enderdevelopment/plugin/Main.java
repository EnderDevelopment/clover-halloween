package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main extends JavaPlugin implements Listener {

    private final Map<Player, Integer> candyCount = new HashMap<>();
    private final Random random = new Random();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("halloween").setExecutor(this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.WHEAT || event.getBlock().getType() == Material.CARROTS || event.getBlock().getType() == Material.POTATOES || event.getBlock().getType() == Material.BEETROOTS) {
            if (random.nextInt(100) < 1) { // 1% chance
                Player player = event.getPlayer();
                candyCount.put(player, candyCount.getOrDefault(player, 0) + 1);
                player.sendMessage("You found a Halloween candy!");
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("halloween")) {
            if (args.length == 0) {
                player.sendMessage("Usage: /halloween shop");
                return true;
            }

            if (args[0].equalsIgnoreCase("shop")) {
                openHalloweenShop(player);
                return true;
            }
        }
        return false;
    }

    private void openHalloweenShop(Player player) {
        // Implement shop logic here
        player.sendMessage("Halloween shop opened!");
    }

    private ItemStack createHalloweenScythe() {
        ItemStack scythe = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = scythe.getItemMeta();
        meta.setDisplayName("Halloween Scythe");
        scythe.setItemMeta(meta);
        return scythe;
    }

    private ItemStack createHalloweenTreat() {
        ItemStack treat = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta meta = treat.getItemMeta();
        meta.setDisplayName("Halloween Treat");
        treat.setItemMeta(meta);
        return treat;
    }

    private void summonBat(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    player.getWorld().spawnEntity(player.getLocation(), org.bukkit.entity.EntityType.BAT);
                }
            }
        }.runTaskTimer(this, 0L, 20L * 30); // 30 seconds
    }
}