package com.lucas.spectervanish.Comandos;

import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.command.*;
import org.bukkit.*;

import com.lucas.spectervanish.*;
import com.lucas.spectervanish.Eventos.*;

import java.util.*;

public class ComandoVanish implements CommandExecutor {
	public static ArrayList<String> admin;
	public static ArrayList<Player> adminMode;

	static {
		ComandoVanish.admin = new ArrayList<String>();
		ComandoVanish.adminMode = new ArrayList<Player>();
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(final CommandSender sender, final Command command, final String label,
			final String[] args) {
		if (sender instanceof Player) {
			final Player p = (Player) sender;
			if (args.length >= 0) {
				if (p.hasPermission("specter.vanish")) {
					if (ComandoVanish.admin.contains(p.getName())) {
						p.getInventory().clear();
						p.getInventory().setArmorContents((ItemStack[]) null);
						ComandoVanish.admin.remove(p.getName());
						p.sendTitle("§cModo Vanish", "§fDesativado com sucesso!");
						for (final Player ps : Bukkit.getOnlinePlayers()) {
							ps.showPlayer(p);
						}
					} else {
						p.getInventory().clear();
						p.getInventory().setArmorContents((ItemStack[]) null);
						p.setGameMode(GameMode.CREATIVE);
						ComandoVanish.admin.add(p.getName());
						p.sendTitle("§aModo Vanish", "§fAtivado com sucesso!");
						p.getInventory().setItem(2, Eventos.buildItem(Material.IRON_BARDING, "&fPuxar para SS §8- §a(Clique direito)"));
						p.getInventory().setItem(3, Eventos.buildItem(Material.SLIME_BALL, "&fVANISH §8- §aON"));
						p.getInventory().setItem(4, Eventos.buildItem(Material.DIAMOND_SWORD, "&fTestar AutoClick §8- §a(Clique direito)"));
						p.getInventory().setItem(5, Eventos.buildItem(Material.BLAZE_ROD, "&fTestar KillAura §8- §a(Clique direito)"));
						p.getInventory().setItem(6, Eventos.buildItem(Material.BARRIER, "&fFechar invent§rio §8- §a(Clique direito)"));
						final int EmAdmin = ComandoVanish.admin.size() - 1;
						for (final Player NoAdmin : Bukkit.getOnlinePlayers()) {
							if (ComandoVanish.admin.contains(NoAdmin.getName())) {
								ComandoVanish.admin.contains(NoAdmin.getName());
							}
						}
						p.setAllowFlight(true);
						for (final Player ps2 : Bukkit.getOnlinePlayers()) {
							if (!ps2.hasPermission("specter.vanish")) {
								ps2.hidePlayer(p);
							}
						}
					}
				} else {
					p.sendMessage(Main.sem_perm);
				}
			} else {
				Bukkit.getConsoleSender().sendMessage("§cSomente jogadores podem executar esse comando.");
			}
		}
		return false;
	}
}
