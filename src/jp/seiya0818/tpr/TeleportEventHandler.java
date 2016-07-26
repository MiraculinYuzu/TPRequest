package jp.seiya0818.tpr;

import java.util.List;

import jp.seiya0818.tpr.back.BackProcess;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeleportEventHandler implements Listener
{
	public static boolean kick = false;
	public static boolean quit = false;

	@EventHandler
	public void onTypeTPCmd(PlayerCommandPreprocessEvent e)
	{
		if (e.getMessage().startsWith("/tp "))
		{
			e.setCancelled(true);

			Player player = e.getPlayer();
			String cmd = e.getMessage();
			String[] args = cmd.split(" ", 0);

			if(args.length == 2)
			{
				Player target = TeleportMaterial.getPlayer(args[1]);
				if(!(player instanceof Player))
				{
					TeleportMessages.isnotPlayerMsg(player);
					return;
				}
				else if(target != null)
				{
					if(!player.hasPermission("teleport.tpr"))
					{
						TeleportMessages.HasnotPermissionMsg(player);
					}
					else
					{
						if(TeleportMaterial.checkList(target) == true)
						{
							Warp.teleport(player, target);
							TeleportMessages.TeleportedMsg(player, target);
							return;
						}
						else if(player.hasPermission("teleport.admin"))
						{
							Warp.teleport(player, target);
							TeleportMessages.TeleportedMsg(player, target);
							return;
						}
						else if(player.hasPermission("teleport.moderator"))
						{
							if(target.hasPermission("teleport.admin") || target.hasPermission("teleort.moderator"))
							{
								TeleportMaterial.SendRequest(player, target);
							}
							else
							{
								Warp.teleport(player, target);
								TeleportMessages.TeleportedMsg(player, target);
							}
							return;
						}
						else
						{
							TeleportMaterial.SendRequest(player, target);
						}
						return;
					}
					return;
				}
				World world = Bukkit.getWorld(args[1]);
				if(world != null)
				{
					if(!player.hasPermission("teleport.world"))
					{
						TeleportMessages.HasnotPermissionMsg(player);
					}
					else
					{
						Location spawn = world.getSpawnLocation();
						Warp.warp(player, spawn);
						TeleportMessages.TeleportWorldMsg(player);
					}
					return;
				}
				else
				{
					TeleportMessages.CouldnotFind(player, args[1]);
				}
			}
			else if(args.length == 3)
			{
				Player tpplayer = TeleportMaterial.getPlayer(args[1]);
				if(tpplayer != null)
				{
					Player target = TeleportMaterial.getPlayer(args[2]);
					if(target != null)
					{
						TeleportMessages.NoPlayerMsg(tpplayer, args[2]);
						TeleportMessages.NoPlayerMsg(player, args[2]);
						if(!tpplayer.hasPermission("teleport.tpr"))
						{
							TeleportMessages.HasnotPermissionMsg(tpplayer);
							TeleportMessages.HasnotPermissionMsg(player);
							return;
						}
						else if(!player.hasPermission("teleport.others"))
						{
							TeleportMessages.HasnotPermissionMsg(player);
							return;
						}
						else
						{
							if(TeleportMaterial.checkList(target) == true)
							{
								Warp.teleport(tpplayer, target);
								TeleportMessages.TeleportedMsg(player, target);
								return;
							}
							else if(tpplayer.hasPermission("teleport.admin"))
							{
								Warp.teleport(tpplayer, target);
								TeleportMessages.TeleportedMsg(tpplayer, target);
								TeleportMessages.HaveTeleportedMsg(player, target.getName());
								return;
							}
							else if(target.hasPermission("teleport.moderator") && !target.hasPermission("teleport.admin"))
							{
								if(target.hasPermission("teleport.admin") || target.hasPermission("teleport.moderator"))
								{
									TeleportMaterial.SendRequest(player, target);
									TeleportMessages.HaveSentRequestdMsg(player, target);
								}
								else
								{
									Warp.teleport(tpplayer, target);
									TeleportMessages.TeleportedMsg(tpplayer, target);
									TeleportMessages.HaveTeleportedMsg(player, target.getName());
								}
								return;
							}
							else
							{
								TeleportMaterial.SendRequest(player, target);
								TeleportMessages.HaveSentRequestdMsg(player, target);
							}
						}
						return;
					}
					World world = Bukkit.getWorld(args[2]);
					if(world != null)
					{
						if(!tpplayer.hasPermission("teleport.world"))
						{
							TeleportMessages.HasnotPermissionMsg(tpplayer);
							TeleportMessages.HasnotPermissionMsg(player);
						}
						else if(!player.hasPermission("teleport.others"))
						{
							TeleportMessages.HasnotPermissionMsg(player);
						}
						else
						{
							Location spawn = world.getSpawnLocation();
							Warp.warp(tpplayer, spawn);
							TeleportMessages.TeleportWorldMsg(tpplayer);
							tpplayer.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + args[1] + " をワールド " + args[2]
									+ " にテレポートされました。");
							TeleportMessages.HaveTeleportedMsg(player, args[2]);
						}
						return;
					}
					else
					{
						TeleportMessages.CouldnotFind(tpplayer, args[2]);
						return;
					}
				}
				else
				{
					TeleportMessages.CouldnotFind(tpplayer, args[1]);
					return;
				}
			}
			else
			{
				TeleportMessages.NoCmdMessage(player);
			}
			return;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e)
	{
		List<String> list = Main.list;
		for(int i =0; i < list.size(); i++)
		{
			if(list.get(i).equals(e.getPlayer().getUniqueId().toString()))
			{
				Player player = e.getPlayer();
				if(TeleportMaterial.checkList(player) == false)
				{
					TeleportMaterial.setList(player);
				}
				return;
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Player player = e.getEntity().getPlayer();
		BackProcess.addPlayer(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		if(quit == true)
		{
			BackProcess.removePlayer(e.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e)
	{
		if(kick == true)
		{
			BackProcess.removePlayer(e.getPlayer());
		}
	}
}
