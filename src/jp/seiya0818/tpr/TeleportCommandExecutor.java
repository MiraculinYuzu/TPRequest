package jp.seiya0818.tpr;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

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
				}
				else if(!sender.hasPermission("teleport.tpa"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
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
				}
				else if(!sender.hasPermission("teleport.tpd"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						TeleportMaterial.DenyTeleport(player);
						break;
					}
					else if(player.hasMetadata(TeleportMaterial.Tmeta))
					{
						TeleportMaterial.DenyTeleport(player);
						break;
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
				}
				else if(!sender.hasPermission("teleport.tpall"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasPermission("teleport.admin"))
					{
						TeleportMaterial.TeleportAllPlayerForcibly(player);
						break;
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
					return true;
				}
			}


			else if(cmd.getName().equalsIgnoreCase("tpr") || cmd.getName().equalsIgnoreCase("tphere"))
			{
				TeleportMessages.NotargMsg(sender);
				break;
			}

			else
			{
				TeleportMessages.NoCmdMessage(sender);
			}

		case 1:
			if(cmd.getName().equalsIgnoreCase("tpr"))
			{
				Player target = TeleportMaterial.getPlayer(args[0]);
				if(!(sender instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(sender);
				}
				else if(!sender.hasPermission("teleport.tpr"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
				}
				else
				{
					Player player = (Player) sender;
					if(TeleportMaterial.checkList(target) == true)
					{
						player.teleport(target);
						TeleportMessages.TeleportedMsg(player, target);
						break;
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
				}
				else if(!sender.hasPermission("teleport.tpa"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
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
						break;
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
				}
				else if(!sender.hasPermission("teleport.tpd"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
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
						break;
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
				}
				else if(!sender.hasPermission("teleport.tpall"))
				{
					TeleportMessages.HasnotPermissionMsg(sender);
				}
				else if(target == null)
				{
					TeleportMessages.NoPlayerMsg(sender, args[0]);
				}
				else
				{
					Player player = (Player) sender;
					if(player.hasPermission("teleport.admin"))
					{
						TeleportMaterial.TeleportHereForcibly(player, target);
						break;
					}
					else if(player.hasPermission("teleport.moderator"))
					{
						if(!target.hasPermission("teleport.admin") || !target.hasPermission("teleport.moderator"))
						TeleportMaterial.TeleportHereForcibly(player, target);
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
			}

			else
			{
				TeleportMessages.NoCmdMessage(sender);
			}
		default:
			TeleportMessages.NoCmdMessage(sender);
		}
		return true;
	}
}
