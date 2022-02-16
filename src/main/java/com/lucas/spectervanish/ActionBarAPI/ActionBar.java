package com.lucas.spectervanish.ActionBarAPI;

import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

public class ActionBar
{
    @SuppressWarnings("unused")
	private static PacketPlayOutChat packet;
    
    @SuppressWarnings("rawtypes")
	public static void enviar(final Player p, final String texto) {
        final PacketPlayOutChat packets = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + texto + "\"}"), (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packets);
    }
    
    @SuppressWarnings("rawtypes")
	public static void enviarTodos(final String texto) {
        final PacketPlayOutChat packets = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + texto + "\"}"), (byte)2);
        for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet)packets);
        }
    }
}
