package jp.seiya0818.tpr.hub;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.MoneyUnit;
import jp.seiya0818.tpr.Process;
import jp.seiya0818.tpr.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommands implements CommandExecutor
{
	public final Main plugin;

	public HubCommands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length == 0)
		{
			if(cmd.getName().equalsIgnoreCase("hub"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("teleport.hub"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					Double money = Config.getDefaultDouble("Vault.hub-price");
					if(Config.checkContains("Hub") == false)
					{
						player.sendMessage(Main.PlayerPrefix + Config.getString("NoHub"));
						return true;
					}
					else if(Process.vault == true && money != 0 && !player.hasPermission("teleport.bypass.hub"))
					{
						if(MoneyUnit.withdraw(player, money) == false)
						{
							return true;
						}
					}
					else
					{
						String path = "Hub.";
						World world = Config.getWorld(path + "world");
						double x = Config.getDataDouble(path + "x");
						double y = Config.getDataDouble(path + "y");
						double z = Config.getDataDouble(path + "z");
						float yaw = Config.getDataInt(path + "yaw");
						float pitch = Config.getDataInt(path + "pitch");

						Warp.warp(player, new Location(world, x, y, z, yaw, pitch));
						player.sendMessage(Main.PlayerPrefix + Config.getString("TeleportHub"));
					}
				}
			}
			else if(cmd.getName().equalsIgnoreCase("sethub"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("teleport.sethub"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					World world = player.getWorld();
					String path = "Hub.";
					Location loc = player.getLocation();
					Config.setString(path + "world", world.getName());
					Config.setDouble(path + "x", loc.getX());
					Config.setDouble(path + "y", loc.getY());
					Config.setDouble(path + "z", loc.getZ());
					Config.setFloat(path + "yaw", loc.getYaw());
					Config.setFloat(path + "pitch", loc.getPitch());
					Config.savedata();
					player.sendMessage(Main.PlayerPrefix + Config.getString("SetHub"));
					return true;
				}
			}
			else if(cmd.getName().equalsIgnoreCase("delhub"))
			{
				if(!sender.hasPermission("teleport.delhub"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else if(Config.checkContains("Hub") == false)
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoHub"));
					return true;
				}
				else
				{
					Config.setString("Hub", null);
					Config.savedata();
					sender.sendMessage(Main.PlayerPrefix + Config.getString("DeleteHub"));
					return true;
				}
			}
			else if(cmd.getName().equalsIgnoreCase("chatclear"))
			{
				if(!sender.hasPermission("teleport.hub.chatclear"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
				}
				else
				{
					String name = null;
					if(!(sender instanceof Player))
					{
						name = "Console";
					}
					else
					{
						Player player = (Player) sender;
						name = player.getDisplayName();
					}
					for(int i = 0; i < 26; i++)
					{
						Bukkit.broadcastMessage("");
					}
					Bukkit.broadcastMessage(Config.getString("ChatClear")
							.replaceAll("%player", name));
				}
			}
		}
		return false;
	}
}
