package jp.seiya0818.tpr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.PluginDescriptionFile;

public class TeleportCommandExecutor implements CommandExecutor
{
	public final Main plugin;

	public TeleportCommandExecutor(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		switch(args.length)
		{
		case 0:
			if(cmd.getName().equalsIgnoreCase("tpa"))
			{
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpa"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						TeleportMaterial.AcceptTeleport(player);
					}
					else
					{
						TeleportMessages.HasnotMetadataMsg(player);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tpd"))
			{
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpd"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						TeleportMaterial.DenyTeleport(player);
						return true;
					}
					else if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						TeleportMaterial.DenyTeleport(player);
						return true;
					}
					else
					{
						TeleportMessages.HasnotMetadataMsg(player);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tpall"))
			{
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpall"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasPermission("teleport.admin"))
					{
						TeleportMaterial.TeleportAllPlayerForcibly(player);
						return true;
					}
					else
					{
						TeleportMaterial.TeleportAllPlayer(player);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tptoggle"))
			{
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tptoggle"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					TeleportMaterial.setList(player);
				}
				return true;
			}


			else if(cmd.getName().equalsIgnoreCase("tpr") || cmd.getName().equalsIgnoreCase("tphere"))
			{
				TeleportMessages.NotargMsg(sender);
				return true;
			}

			else
			{
				TeleportMessages.NoCmdMessage(sender);
				return true;
			}

		case 1:
			if(cmd.getName().equalsIgnoreCase("tpr"))
			{
				Player target = TeleportMaterial.getPlayer(args[0]);
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpr"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(TeleportMaterial.checkList(target) == true)
					{
						player.teleport(target);
						TeleportMessages.TeleportedMsg(player, target);
					}
					else
					{
						TeleportMaterial.SendRequest(player, target);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tpa"))
			{
				Player target = TeleportMaterial.getPlayer(args[0]);
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpa"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						for(MetadataValue meta : player.getMetadata(TeleportMaterial.Tmeta))
						{
							Player mtarget = (Player) meta.value();
							if(args[0].equals(mtarget.getName()))
							{
								TeleportMaterial.AcceptTeleport(player, target);
							}
							else
							{
								TeleportMessages.HasnotMetadataMsg(player, args[0]);
							}
						}
						return true;
					}
					else
					{
						TeleportMessages.HasnotMetadataMsg(player);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tpd"))
			{
				Player target = TeleportMaterial.getPlayer(args[0]);
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpd"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						for(MetadataValue meta : player.getMetadata(TeleportMaterial.Tmeta))
						{
							Player mtarget = (Player) meta.value();
							if(args[0].equals(mtarget.getName()))
							{
								TeleportMaterial.DenyTeleport(player, target);
							}
							else
							{
								TeleportMessages.HasnotMetadataMsg(player, args[0]);
							}
						}
						return true;
					}
					else
					{
						TeleportMessages.HasnotMetadataMsg(player);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tphere"))
			{
				Player target = TeleportMaterial.getPlayer(args[0]);
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
					return true;
				}
				else if(!sender.hasPermission("teleport.tpall"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
					return true;
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
					return true;
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasPermission("teleport.admin"))
					{
						TeleportMaterial.TeleportHereForcibly(player, target);
						return true;
					}
					else if(player.hasPermission("teleport.moderator"))
					{
						if(!target.hasPermission("teleport.admin") || !target.hasPermission("teleport.moderator"))
						{
							TeleportMaterial.TeleportHereForcibly(player, target);
						}
						else
						{
							TeleportMaterial.SendRequest(player, target);
						}
					}
					else
					{
						TeleportMaterial.SendRequest(player, target);
					}
				}
				return true;
			}

			else if(cmd.getName().equalsIgnoreCase("tprequest"))
			{
				if(args[0].equalsIgnoreCase("reload"))
				{
					if(!sender.hasPermission("teleport.reload"))
					{
						sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					}
					else
					{
						Config.reload();
						sender.sendMessage(Main.PlayerPrefix + Config.getString("Reload"));
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("info"))
				{
					if(!sender.hasPermission("teleport.info"))
					{
						sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					}
					else
					{
						PluginDescriptionFile pdf = Main.instance.getDescription();
						sender.sendMessage(ChatColor.GOLD + "Plugin Name: " + ChatColor.WHITE + pdf.getName());
						sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.WHITE + pdf.getVersion());
						sender.sendMessage(ChatColor.GOLD + "Discription: " + ChatColor.WHITE + pdf.getDescription());
						sender.sendMessage(ChatColor.GOLD + "Website: " + ChatColor.WHITE + pdf.getWebsite());
					}
				}
				else if(args[0].equalsIgnoreCase("changeformat"))
				{
					Config.createFile(true);
				}
				else if(args[0].equals("testversion"))
				{
					sender.sendMessage(Main.instance.getServer().getClass().getPackage().getName());
					sender.sendMessage(Bukkit.getBukkitVersion());
					sender.sendMessage(Bukkit.getServer().getVersion());
				}
				else if(args[0].equals("checkversion"))
				{
					if(Bukkit.getServer().getVersion().contains("1.10"))
					{
						sender.sendMessage("1.10です。");
						return true;
					}
					else if(Bukkit.getServer().getVersion().contains("1.9"))
					{
						sender.sendMessage("1.9です。");
						return true;
					}
					else if(Bukkit.getServer().getVersion().contains("1.8"))
					{
						sender.sendMessage("1.8です。");
						return true;
					}
					else if(Bukkit.getServer().getVersion().contains("1.7"))
					{
						sender.sendMessage("1.7です。");
						return true;
					}
					sender.sendMessage("1.6以下です。");
				}
				return true;
			}

			else
			{
				TeleportMessages.NoCmdMessage(sender);
				return true;
			}
		default:
			TeleportMessages.NoCmdMessage(sender);
			return true;
		}
	}
}
