package jp.seiya0818.tpr.warp;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.MoneyUnit;
import jp.seiya0818.tpr.Process;
import jp.seiya0818.tpr.Warp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener
{
	private static String Prefix = ChatColor.AQUA + "[Warp]";

	@EventHandler
	public void onSignClick(PlayerInteractEvent e)
	{
		if((e.getAction() != Action.RIGHT_CLICK_BLOCK))
		{
			return;
		}

		Block clickdblock = e.getClickedBlock();
		if ((clickdblock.getType() != Material.SIGN)
				&& (clickdblock.getType() != Material.SIGN_POST)
				&& (clickdblock.getType() != Material.WALL_SIGN))
		{
			return;
		}

		Sign thisSign = (Sign)clickdblock.getState();
		String[] lines = thisSign.getLines();
		if(!lines[0].equalsIgnoreCase(Prefix))
		{
			return;
		}

		if(Config.checkContains("Warp." + lines[1]) == false)
		{
			return;
		}

		Player player = e.getPlayer();
		String path = "Warp." + lines[1] + ".";
		if(Config.getDataString(path + "Type").equals("PRIVATE")
				&& !Config.getDataList(path + "Members").contains(player.getUniqueId().toString())
				&& !Config.getDataString(path + "Owner").equals(player.getUniqueId().toString())
				&& !player.hasPermission("warp.override.use"))
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
			return;
		}

		if(!player.hasPermission("warp.sign.use"))
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
			return;
		}

		Double money = Config.getDataDouble(path + "Price");
		if(Process.vault == true && money != 0 && !player.hasPermission("warp.bypass.use"))
		{
			if(MoneyUnit.withdraw(player, money) == false)
			{
				return;
			}
		}
		else
		{
			World world = Config.getWorld(path + "world");
			double x = Config.getDataDouble(path + "x");
			double y = Config.getDataDouble(path + "y");
			double z = Config.getDataDouble(path + "z");
			float yaw = Config.getDataInt(path + "yaw");
			float pitch = Config.getDataInt(path + "pitch");

			Warp.warp(player, new Location(world, x, y, z, yaw, pitch));
			player.sendMessage(Main.PlayerPrefix + Config.getString("TeleportWarp").replaceAll("%warp", lines[1]));
			return;
		}
	}

	@EventHandler
	public void onSignCreate(SignChangeEvent e)
	{
		if(!"[warp]".equalsIgnoreCase(e.getLine(0)))
		{
			return;
		}

		Player player = e.getPlayer();
		if(!player.hasPermission("warp.sign.create"))
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
			return;
		}

		if(Config.checkContains("Warp." + e.getLine(1)) == false)
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("NoWarp"));
			e.setCancelled(true);
			e.getBlock().breakNaturally();
		}

		e.setLine(0, Prefix);
		player.sendMessage(Main.PlayerPrefix + Config.getString("WarpSignCreate"));
	}

	@EventHandler
	public void onSignBreak(BlockBreakEvent e)
	{
		Block b = e.getBlock();
		if ((b.getType() != Material.SIGN) && (b.getType() != Material.SIGN_POST)
				&& (b.getType() != Material.WALL_SIGN))
		{
			return;
		}

		Sign sign = (Sign)b.getState();
		String[] lines = sign.getLines();
		if(!lines[0].equalsIgnoreCase(Prefix))
		{
			return;
		}

		Player player = e.getPlayer();
		if(!player.hasPermission("warp.sign.break"))
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
			return;
		}

		player.sendMessage(Main.PlayerPrefix + Config.getString("WarpSignBreak"));
	}
}
