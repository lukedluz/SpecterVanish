package com.lucas.spectervanish.Eventos;

import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.lucas.spectervanish.Main;
import com.lucas.spectervanish.Comandos.*;

import org.bukkit.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class Eventos implements Listener {

	public static HashMap<String, Integer> clickd = new HashMap<String, Integer>();
	public static HashMap<String, Integer> clicke = new HashMap<String, Integer>();
	public static HashMap<String, Integer> clicki = new HashMap<String, Integer>();
	public static HashMap<String, Integer> timeclick = new HashMap<String, Integer>();
	public static ArrayList<String> autoclick = new ArrayList<>();

	@EventHandler
	public void entrar(PlayerJoinEvent e) {
		for (Player ps : Bukkit.getOnlinePlayers()) {
			if (ComandoVanish.admin.contains(ps.getName())) {
				if (!e.getPlayer().hasPermission("specter.vanish")) {
					e.getPlayer().hidePlayer(ps);
				}
			}
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		if (event.getRightClicked() instanceof Player && ComandoVanish.admin.contains(player.getName())) {
			if (player.getItemInHand().getType().equals((Object) Material.AIR)) {
				event.setCancelled(true);
				Player open = (Player) event.getRightClicked();
				player.openInventory((Inventory) open.getInventory());
				player.sendMessage(" ");
				player.sendMessage(" §a\u2022 §aVoc\u00ea abriu o invent\u00e1rio de §f" + open.getName() + "§a.");
				player.sendMessage(" ");
			} else if (player.getItemInHand().getType().equals((Object) Material.BLAZE_ROD)) {
				event.setCancelled(true);
				Player open = (Player) event.getRightClicked();
				player.sendMessage(" ");
				player.sendMessage(" §a\u2022 Testando KillAura em: §f" + open.getName() + "§a.");
				player.sendMessage(" ");
				player.chat("/auracheck " + open.getName());
			} else if (player.getItemInHand().getType().equals((Object) Material.DIAMOND_SWORD)) {
				event.setCancelled(true);
				Player open = (Player) event.getRightClicked();
				if (!autoclick.contains(open.getName())) {
					autoclick.add(open.getName());
					clickd.put(open.getName(), 0);
					clicke.put(open.getName(), 0);
					clicki.put(open.getName(), 0);
					player.sendMessage(" ");
					player.sendMessage(
							" §a\u2022 Testando AutoClick em: §f" + open.getName() + "§a. §c(Aguarde 10 segundos)");
					player.sendMessage(" ");
					timeclick.put(open.getName(), 10);
					new BukkitRunnable() {
						@SuppressWarnings("deprecation")
						public void run() {
							if (timeclick.get(open.getName()) < 0) {
								player.sendTitle("§aTestando AutoClick", "§f");
								player.resetTitle();
								this.cancel();
							} else {
								player.sendTitle("§aTestando AutoClick", "§f" + timeclick.get(open.getName()) + "...");
								Integer time = timeclick.get(open.getName());
								timeclick.remove(open.getName());
								timeclick.put(open.getName(), time - 1);
							}
						}
					}.runTaskTimer((Plugin) Main.instance, 0L, 20L);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
						public void run() {
							if (autoclick.contains(open.getName())) {
								player.sendMessage("");
								player.sendMessage(
										" §a\u2022 CPS§7(bot§o esquerdo)§a: §f" + clicke.get(open.getName()) / 10);
								player.sendMessage(
										" §a\u2022 CPS§7(bot§o direito)§a: §f" + clickd.get(open.getName()) / 10);
								player.sendMessage(
										" §a\u2022 CPS§7(em invent§rio)§a: §f" + clicki.get(open.getName()) / 10);
								player.sendMessage("");

								autoclick.remove(open.getName());
								clickd.remove(open.getName());
								clicke.remove(open.getName());
								clicki.remove(open.getName());
							}
						}
					}, 200L);
				}
			} else if (player.getItemInHand().getType().equals((Object) Material.BARRIER)) {
				event.setCancelled(true);
				Player open = (Player) event.getRightClicked();
				open.closeInventory();
				player.sendMessage("");
				player.sendMessage(" §a\u2022 Invent§rio fechado");
				player.sendMessage("");
			} else if (player.getItemInHand().getType().equals((Object) Material.IRON_BARDING)) {
				event.setCancelled(true);
				Player open = (Player) event.getRightClicked();
				player.chat("/ss " + open.getName());
			} else {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	private void onPlayerDropItemAdmin(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		if (ComandoVanish.admin.contains(player.getName())) {
			player.sendMessage(" ");
			player.sendMessage("§c\u2022 §cVoc\u00ea n\u00e3o pode dropar itens no modo admin.");
			player.sendMessage(" ");
			e.setCancelled(true);
		}
	}

	@EventHandler
	private void onPlayerPickUpAdmin(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		if (ComandoVanish.admin.contains(player.getName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public static void adminSair(PlayerQuitEvent e) {
		if (ComandoVanish.admin.contains(e.getPlayer().getName())) {
			ComandoVanish.admin.remove(e.getPlayer().getName());
			e.getPlayer().getInventory().clear();
		}
	}

	@EventHandler
	public static void adminDeath(PlayerDeathEvent e) {
		if (ComandoVanish.admin.contains(e.getEntity().getName())) {
			ComandoVanish.admin.remove(e.getEntity().getName());
			e.getDrops().clear();
		}
	}

	@EventHandler
	public static void adminGameMode(PlayerGameModeChangeEvent e) {
		if (ComandoVanish.admin.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			;
		}
	}

	public static ItemStack buildItem(Material mat, String display) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display.replace("&", "§"));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack buildItem(Material mat, String display, int ItemID) {
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display.replace("&", "§"));
		item.setItemMeta(meta);
		item.setDurability((short) ItemID);
		return item;
	}

	public void fastadmin(Player p) {
		p.getInventory().clear();
		p.sendMessage("");
		p.sendMessage("§cCuidado! Você está visível agora.");
		p.sendMessage("");
		p.getInventory().setItem(2, Eventos.buildItem(Material.IRON_BARDING, "&fPuxar para SS §8- §a(Clique direito)"));
		p.getInventory().setItem(3, Eventos.buildItem(Material.FIREBALL, "&fVANISH §8- §cOFF"));
		p.getInventory().setItem(4,
				Eventos.buildItem(Material.DIAMOND_SWORD, "&fTestar AutoClick §8- §a(Clique direito)"));
		p.getInventory().setItem(5, Eventos.buildItem(Material.BLAZE_ROD, "&fTestar KillAura §8- §a(Clique direito)"));
		p.getInventory().setItem(6, Eventos.buildItem(Material.BARRIER, "&fFechar invent§rio §8- §a(Clique direito)"));
		for (Player ps : Bukkit.getOnlinePlayers()) {
			ps.showPlayer(p);
			p.setHealth(20.0);
			p.setFoodLevel(20);
		}
	}

	public void fastadmin2(Player p) {
		p.getInventory().clear();
		p.sendMessage("");
		p.sendMessage("§aVocê está invisível agora.");
		p.sendMessage("");
		p.getInventory().setItem(2, Eventos.buildItem(Material.IRON_BARDING, "&fPuxar para SS §8- §a(Clique direito)"));
		p.getInventory().setItem(3, Eventos.buildItem(Material.SLIME_BALL, "&fVANISH §8- §aON"));
		p.getInventory().setItem(4,
				Eventos.buildItem(Material.DIAMOND_SWORD, "&fTestar AutoClick §8- §a(Clique direito)"));
		p.getInventory().setItem(5, Eventos.buildItem(Material.BLAZE_ROD, "&fTestar KillAura §8- §a(Clique direito)"));
		p.getInventory().setItem(6, Eventos.buildItem(Material.BARRIER, "&fFechar invent§rio §8- §a(Clique direito)"));
		for (Player ps2 : Bukkit.getOnlinePlayers()) {
			if (!ps2.hasPermission("specter.vanish")) {
				ps2.hidePlayer(p);
			}
		}
		p.setGameMode(GameMode.CREATIVE);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Material m = p.getItemInHand().getType();
		if (ComandoVanish.admin.contains(e.getPlayer().getName())
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			if (m == Material.SLIME_BALL) {
				e.setCancelled(true);
				this.fastadmin(p);
			} else if (m == Material.FIREBALL) {
				e.setCancelled(true);
				this.fastadmin2(p);
			}
		}
	}

	@EventHandler
	public void clicar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((autoclick.contains(p.getName()))) {
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (clicke.containsKey(p.getName())) {
					Integer i = clicke.get(p.getName());
					clicke.remove(p.getName());
					clicke.put(p.getName(), i + 1);
				}
			}
		}
	}

	@EventHandler
	public void clicar3(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if ((autoclick.contains(p.getName()))) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (clickd.containsKey(p.getName())) {
					Integer i = clickd.get(p.getName());
					clickd.remove(p.getName());
					clickd.put(p.getName(), i + 1);
				}
			}
		}
	}

	@EventHandler
	public void clicar2(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((autoclick.contains(p.getName()))) {
			if (clicki.containsKey(p.getName())) {
				Integer i = clicki.get(p.getName());
				clicki.remove(p.getName());
				clicki.put(p.getName(), i + 1);
			}
		}
	}
}
