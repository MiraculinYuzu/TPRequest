package jp.seiya0818.tpr.back;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BackProcess
{
	private static Map<Player, Location> locations = new HashMap<>();

	public static void addPlayer(Player player)
	{
		if(locations.containsKey(player))
		{
			locations.remove(player);
		}
		locations.put(player, player.getLocation());
	}

	public static void removePlayer(Player player)
	{
		if (locations.containsKey(player))
		{
			locations.remove(player);
		}
	}

	public static Location getLocation(Player player)
	{
		if (locations.containsKey(player))
		{
			return locations.get(player);
		}
		else
		{
			return null;
		}
	}
}
