package jp.seiya0818.tpr.warp;

import java.util.List;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.MoneyUnit;
import jp.seiya0818.tpr.Process;
import jp.seiya0818.tpr.TeleportMaterial;
import jp.seiya0818.tpr.Warp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommands implements CommandExecutor
{
	public final Main plugin;

	public WarpCommands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(cmd.getName().equalsIgnoreCase("warp"))
		{
			if(args.length == 1)
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
				}
				else
				{
					Player player = (Player) sender;
					if(Config.checkContains("Warp."+ args[0]) == true)
					{
						String path = "Warp." + args[0] + ".";
						if(Config.getDataString(path + "Type").equals("PRIVATE")
							&& !Config.getDataList(path + "Members").contains(player.getUniqueId().toString())
							&& !Config.getDataString(path + "Owner").equals(player.getUniqueId().toString())
							&& !player.hasPermission("warp.override.use"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else if(!player.hasPermission("warp.use"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else
						{
							Double money = Config.getDataDouble(path + "Price");
							if(Process.vault == true && money != 0 && !player.hasPermission("warp.bypass.use"))
							{
								if(MoneyUnit.withdraw(player, money) == false)
								{
									return true;
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
								player.sendMessage(Main.PlayerPrefix + Config.getString("TeleportWarp").replaceAll("%warp", args[0]));
							}
						}
						return true;
					}
					else
					{
						sender.sendMessage(Main.PlayerPrefix + Config.getString("NoWarp"));
					}
				}
				return true;
			}

			if(args.length == 2)
			{
				if(!(sender instanceof Player))
				{
					//Add List Command
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
				}
				else
				{
					Player player = (Player) sender;
					if(args[0].equalsIgnoreCase("create"))
					{
						if(!sender.hasPermission("warp.create.public"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						if(Config.checkContains("Warp."+ args[1]) == true)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("ExistWarp"));
							return true;
						}
						else
						{
							World world = player.getWorld();
							String path = "Warp." + args[1] + ".";
							Location loc = player.getLocation();
							Config.setString(path + "world", world.getName());
							Config.setDouble(path + "x", loc.getX());
							Config.setDouble(path + "y", loc.getY());
							Config.setDouble(path + "z", loc.getZ());
							Config.setFloat(path + "yaw", loc.getYaw());
							Config.setFloat(path + "pitch", loc.getPitch());
							Config.setInt(path + "Price", 0);
							Config.setString(path + "Type", "PUBLIC");
							Config.savedata();
							player.sendMessage(Main.PlayerPrefix + Config.getString("SetWarp").replaceAll("%warp", args[1]));
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("delete"))
					{
						if(Config.checkContains("Warp."+ args[1]) == false)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoWarp"));
							return true;
						}
						else if(Config.getDataString("Warp."+ args[1] + ".Type").equals("PRIVATE")
								&& !Config.getDataList("Warp."+ args[1] + ".Members").contains(player.getUniqueId().toString())
								&& !player.hasPermission("warp.override.delete"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else if(!player.hasPermission("warp.delete"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else
						{
							Config.setString("Warp." + args[1], null);
							Config.savedata();
							sender.sendMessage(Main.PlayerPrefix + Config.getString("DeleteWarp").replaceAll("%warp", args[1]));
							return true;
						}
					}
				}
			}
			else if(args.length == 3)
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(args[0].equalsIgnoreCase("create") && args[2].equalsIgnoreCase("private"))
					{
						if(!sender.hasPermission("warp.create.private"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else if(Config.checkContains("Warp."+ args[1]) == true)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("ExistWarp"));
							return true;
						}
						else
						{
							World world = player.getWorld();
							String path = "Warp." + args[1] + ".";
							Location loc = player.getLocation();

							Config.setString(path + "world", world.getName());
							Config.setDouble(path + "x", loc.getX());
							Config.setDouble(path + "y", loc.getY());
							Config.setDouble(path + "z", loc.getZ());
							Config.setFloat(path + "yaw", loc.getYaw());
							Config.setFloat(path + "pitch", loc.getPitch());
							Config.setInt(path + "Price", 0);
							Config.setString(path + "Type", "PRIVATE");
							Config.setString(path + "Owner", player.getUniqueId().toString());
							Config.savedata();
							player.sendMessage(Main.PlayerPrefix + Config.getString("SetWarp").replaceAll("%warp", args[1]));
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("create") && args[2].equalsIgnoreCase("public"))
					{
						if(!sender.hasPermission("warp.create.public"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else if(Config.checkContains("Warp."+ args[1]) == true)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("ExistWarp"));
							return true;
						}
						else
						{
							World world = player.getWorld();
							String path = "Warp." + args[1] + ".";
							Location loc = player.getLocation();
							Config.setString(path + "world", world.getName());
							Config.setDouble(path + "x", loc.getX());
							Config.setDouble(path + "y", loc.getY());
							Config.setDouble(path + "z", loc.getZ());
							Config.setFloat(path + "yaw", loc.getYaw());
							Config.setFloat(path + "pitch", loc.getPitch());
							Config.setInt(path + "Price", 0);
							Config.setString(path + "Type", "PUBLIC");
							Config.savedata();
							player.sendMessage(Main.PlayerPrefix + Config.getString("SetWarp").replaceAll("%warp", args[1]));
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("setprice"))
					{
						if(Config.checkContains("Warp."+ args[1]) == false)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoWarp"));
							return true;
						}
						else if(Config.getDataString("Warp."+ args[1] + ".Type").equals("PRIVATE"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("PrivateOnly"));
							return true;
						}
						else if(!Config.getDataList("Warp."+ args[1] + ".Members").contains(player.getUniqueId().toString())
								&& !Config.getDataString("Warp."+ args[1] + ".Owner").equals(player.getUniqueId().toString())
								&& !player.hasPermission("warp.override.setprice"))
							{
								sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
								return true;
							}
						else if(!player.hasPermission("warp.setprice"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else
						{
							Config.setInt("Warp." + args[1] + ".Price", Integer.parseInt(args[2]));
							player.sendMessage(Main.PlayerPrefix + Config.getString("SetPrice")
									.replaceAll("%warp", args[1]).replaceAll("%price", args[2])
									.replaceAll("%unit", Process.getUnit()));
							return true;
						}
					}
					else if(args[0].equalsIgnoreCase("addmember"))
					{
						if(Config.checkContains("Warp."+ args[1]) == false)
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoWarp"));
							return true;
						}
						else if(!Config.getDataString("Warp." + args[1] + ".Type").equals("PRIVATE"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("PrivateOnly"));
							return true;
						}
						else if(!Config.getDataList("Warp."+ args[1] + ".Members").contains(player.getUniqueId().toString())
								&& !Config.getDataString("Warp." + args[1] + ".Owner").equals(player.getUniqueId().toString())
								&& !player.hasPermission("warp.override.addmember"))
						{
							sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
							return true;
						}
						else
						{
							List<String> list = Config.getDataList("Warp." + args[1] + ".Members");
							if(list.contains(player.getUniqueId().toString()))
							{
								if(TeleportMaterial.getPlayer(args[2]) != null)
								{
									String uuid = player.getUniqueId().toString();
									if(list.contains(uuid))
									{
										sender.sendMessage(Main.PlayerPrefix + Config.getString("HaveAlreadyBeenMember"));
										return true;
									}
									list.add(uuid);
									Config.setList("Warp." + args[1] + ".Members", list);
									sender.sendMessage(Main.PlayerPrefix + Config.getString("AddMember")
											.replaceAll("%warp", args[1]).replaceAll("%member", args[2]));
									return true;
								}
								for(OfflinePlayer op: Bukkit.getOfflinePlayers())
								{
									if(op.getName().equals(args[2]))
									{
										String uuid = op.getUniqueId().toString();
										if(list.contains(uuid))
										{
											sender.sendMessage(Main.PlayerPrefix + Config.getString("HaveAlreadyBeenMember"));
											return true;
										}
										list.add(uuid);
										Config.setList("Warp." + args[1] + ".Members", list);
										sender.sendMessage(Main.PlayerPrefix + Config.getString("AddMember")
												.replaceAll("%warp", args[1]).replaceAll("%member", args[2]));
										return true;
									}
								}
								sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerHasnotPlayedBefore"));
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
