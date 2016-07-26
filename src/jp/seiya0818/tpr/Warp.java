package jp.seiya0818.tpr;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class Warp
{
	public static boolean tpriding = false;

	public static void warp(Player player, Location loc)
	{
		Chunk c = loc.getChunk();
		Entity riding = player.getVehicle();

		if(!c.isLoaded())
		{
			c.load();
		}
		if(riding != null && tpriding == true)
		{
			riding.eject();
			riding.teleport(loc, TeleportCause.PLUGIN);
			player.teleport(loc, TeleportCause.PLUGIN);
			riding.setPassenger(player);
		}
		else
		{
			player.teleport(loc, TeleportCause.PLUGIN);
		}
	}

	public static void teleport(Player player, Player target)
	{
		Entity riding = player.getVehicle();
		if(riding != null && tpriding == true)
		{
			riding.eject();
			riding.teleport(target, TeleportCause.PLUGIN);
			player.teleport(target, TeleportCause.PLUGIN);
			riding.setPassenger(player);
		}
		else
		{
			player.teleport(target, TeleportCause.PLUGIN);
		}
	}
}
