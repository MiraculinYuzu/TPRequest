package jp.seiya0818.tpr.spawn;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.TeleportMessages;
import jp.seiya0818.tpr.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommands implements CommandExecutor
{
	public final Main plugin;

	public SpawnCommands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length == 0)
		{
			if(cmd.getName().equalsIgnoreCase("spawn"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("teleport.spawn"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					World world = player.getWorld();
					String worldname = world.getName();
					if(Config.getString("Spawn."+ worldname) == null)
					{
						player.sendMessage(Main.PlayerPrefix + Config.getString("NoSpawn"));
					}
					else
					{
						String path = "Spawn." + worldname + ".";
						double x = Config.getDataDouble(path + "x");
						double y = Config.getDataDouble(path + "y");
						double z = Config.getDataDouble(path + "z");
						float yaw = Config.getDataInt(path + "yaw");
						float pitch = Config.getDataInt(path + "pitch");

						Warp.warp(player, new Location(world, x, y, z, yaw, pitch));
						player.sendMessage(Main.PlayerPrefix + Config.getString("TeleportSpawn"));
					}
				}
			}
			else if(cmd.getName().equalsIgnoreCase("setspawn"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("teleport.setspawn"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					World world = player.getWorld();
					String worldname = world.getName();
					String path = "Spawn." + worldname + ".";
					Location loc = player.getLocation();
					Config.setDouble(path + "x", loc.getX());
					Config.setDouble(path + "y", loc.getY());
					Config.setDouble(path + "z", loc.getZ());
					Config.setFloat(path + "yaw", loc.getYaw());
					Config.setFloat(path + "pitch", loc.getPitch());
					Config.savedata();
					return true;
				}
			}
			else if(cmd.getName().equalsIgnoreCase("delspawn"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("teleport.delspawn"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					World world = player.getWorld();
					String worldname = world.getName();
					String path = "Spawn." + worldname + ".";
					Config.setString(path + "x", null);
					Config.setString(path + "y", null);
					Config.setString(path + "z", null);
					Config.setString(path + "yaw", null);
					Config.setString(path + "pitch", null);
					Config.savedata();
					return true;
				}
			}
		}
		if(args.length == 1)
		{
			if(cmd.getName().equalsIgnoreCase("spawn"))
			{
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.spawn.others"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					World world = Bukkit.getWorld(args[0]);
					String worldname = world.getName();
					if(Config.getString("Spawn."+ worldname) == null)
					{
						player.sendMessage(Main.PlayerPrefix + Config.getString("NoSpawn"));
					}
					else
					{
						String path = "Spawn." + worldname + ".";
						double x = Config.getDataDouble(path + "x");
						double y = Config.getDataDouble(path + "y");
						double z = Config.getDataDouble(path + "z");
						float yaw = Config.getDataInt(path + "yaw");
						float pitch = Config.getDataInt(path + "pitch");

						Warp.warp(player, new Location(world, x, y, z, yaw, pitch));
						player.sendMessage(Main.PlayerPrefix + Config.getString("TeleportOtherSpawn").replaceAll("%world", worldname));
					}
				}
			}
			else if(cmd.getName().equalsIgnoreCase("delspawn"))
			{

				if(!sender.hasPermission("teleport.delspawn"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
				}
				else
				{
					World world = Bukkit.getWorld(args[0]);
					if(world == null)
					{
						sender.sendMessage(Main.PlayerPrefix + Config.getString("WorldFailed"));
					}
					String worldname = world.getName();
					String path = "Spawn." + worldname + ".";
					Config.setString(path + "x", null);
					Config.setString(path + "y", null);
					Config.setString(path + "z", null);
					Config.setString(path + "yaw", null);
					Config.setString(path + "pitch", null);
					Config.savedata();
					return true;
				}
			}
		}
		return true;
	}
}
