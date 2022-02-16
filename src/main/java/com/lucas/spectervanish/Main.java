package com.lucas.spectervanish;

import org.bukkit.plugin.java.*;
import org.bukkit.plugin.*;
import org.bukkit.scheduler.*;
import org.bukkit.*;
import org.bukkit.entity.*;

import org.bukkit.command.*;

import com.lucas.spectervanish.ActionBarAPI.*;
import com.lucas.spectervanish.Comandos.*;
import com.lucas.spectervanish.Eventos.*;

import org.bukkit.event.*;

public class Main extends JavaPlugin
{
    public static String sem_perm;
    PluginManager evento;
    public static Main instance;
    
    static {
        Main.sem_perm = "§cVocê não tem permissão para usar esse comando.";
    }
    
    public Main() {
        this.evento = this.getServer().getPluginManager();
    }
    
    public static Plugin getPlugin() {
        return (Plugin)Main.instance;
    }
    
    public void onEnable() {
        Main.instance = this;
        new BukkitRunnable() {
            public void run() {
                for (final Player player : Bukkit.getOnlinePlayers()) {
                    if (ComandoVanish.admin.contains(player.getName())) {
                        if (ComandoVanish.admin.size() < 2) {
                            ActionBar.enviar(player, "§aSomente você esta no modo Vanish.");
                        }
                        else {
                            ActionBar.enviar(player, "§aVocê e mais §f" + (ComandoVanish.admin.size() - 1) + " §astaff(s) estão no §fModo Vanish§a!");
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin)Main.instance, 1L, 20L);
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§7==========================");
        Bukkit.getConsoleSender().sendMessage("§7| §bSpecterVanish          §7|");
        Bukkit.getConsoleSender().sendMessage("§7| §bVersão 1.0             §7|");
        Bukkit.getConsoleSender().sendMessage("§7| §fStatus: §aLigado         §7|");
        Bukkit.getConsoleSender().sendMessage("§7==========================");
        Bukkit.getConsoleSender().sendMessage("");
        this.getCommand("v").setExecutor((CommandExecutor)new ComandoVanish());
        this.evento.registerEvents((Listener)new Eventos(), (Plugin)this);
    }
    
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§7==========================");
        Bukkit.getConsoleSender().sendMessage("§7| §bSpecterVanish          §7|");
        Bukkit.getConsoleSender().sendMessage("§7| §bVersão 1.0             §7|");
        Bukkit.getConsoleSender().sendMessage("§7| §fStatus: §cDesligado      §7|");
        Bukkit.getConsoleSender().sendMessage("§7==========================");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
