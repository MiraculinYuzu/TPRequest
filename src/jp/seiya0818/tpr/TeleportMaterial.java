package jp.seiya0818.tpr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class TeleportMaterial
{
	public final static String Tmeta = "TeleportMeta";
	private static Main main;

	public static Main getMain()
	{
		return main;
	}

	public static void SendRequest(Player player, Player target)
	{
		target.setMetadata(Tmeta, new FixedMetadataValue(Main.instance, player));
		player.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + target.getName() + ChatColor.GREEN
				+ " にテレポート申請を送りました。");
		target.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN
				+ " からテレポート申請が送られてきました。");
		target.sendMessage(ChatColor.GREEN + "/tpa " + player.getName() + " - テレポートを許可");
		target.sendMessage(ChatColor.GREEN + "/tpd " + player.getName() + " - テレポートを拒否");
	}

	public static void AcceptTeleport(Player target)
	{
		for(MetadataValue meta : target.getMetadata(Tmeta))
		{
			Player player = (Player) meta.value();
			Warp.teleport(player, target);
			target.removeMetadata(Tmeta, Main.instance);
			target.sendMessage(Main.PlayerPrefix + " " + ChatColor.GOLD + player.getName() + ChatColor.GREEN
					+ " のテレポート申請を許可しました。");
			player.sendMessage(Main.PlayerPrefix + " " +  ChatColor.GOLD + target.getName() + ChatColor.GREEN
					+ " があなたのテレポート申請を許可しました。");
		}
	}

	public static void AcceptTeleport(Player player, Player target)
	{
		Warp.teleport(target, player);
		target.removeMetadata(Tmeta, Main.instance);
		target.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + target.getName() + ChatColor.GREEN
				+ " のテレポート申請を許可しました。");
		player.sendMessage(Main.PlayerPrefix + " " +  ChatColor.GOLD + target.getName() + ChatColor.GREEN
				+ " があなたのテレポート申請を許可しました。");
	}

	public static void DenyTeleport(Player target)
	{
		for(MetadataValue meta : target.getMetadata(Tmeta))
		{
			Player player = (Player) meta.value();
			target.removeMetadata(Tmeta, Main.instance);
			player.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + target.getName() + ChatColor.RED
					+ " があなたのテレポート申請を拒否しました。");
			target.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN
					+ " のテレポート申請を拒否しました。");
		}
	}

	public static void DenyTeleport(Player player, Player target)
	{
		target.removeMetadata(Tmeta, Main.instance);
		player.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + target.getName() + ChatColor.RED
				+ " があなたのテレポート申請を拒否しました。");
		target.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN
				+ " のテレポート申請を拒否しました。");
	}

	public static void TeleportHereForcibly(Player player, Player target)
	{
		Warp.teleport(target, player);
	    player.sendMessage(Main.PlayerPrefix + ChatColor.GREEN + "テレポートに成功しました。");
		target.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN
				+ " にテレポートされました。");
	}

	public static void TeleportAllPlayer(Player player)
	{
		for(Player all : Bukkit.getServer().getOnlinePlayers())
		{
			if(!all.getName().equals(player))
			{
				SendRequest(all, player);
			}
		}
	}

	public static void TeleportAllPlayerForcibly(Player player)
	{
		for(Player all : Bukkit.getServer().getOnlinePlayers())
		{
			if(!all.getName().equals(player))
			{
				Warp.teleport(all, player);
				all.sendMessage(Main.PlayerPrefix + ChatColor.GOLD + player.getName() + ChatColor.GREEN
						+ " に一斉テレポートされました。");
			}
		}
		player.sendMessage(Main.PlayerPrefix + ChatColor.GREEN + "一斉テレポートに成功しました。");
	}

	public static void setList(Player player)
	{
		Boolean ed = true;
		String puuid = player.getUniqueId().toString();
		for(int i = 0; i < Main.list.size(); i++)
		{
			String uuid = Main.list.get(i);
			if(puuid.equals(uuid));
			{
				Main.list.remove(i);
				Config.updateList(puuid);
				ed = false;
				player.sendMessage(Main.PlayerPrefix + ChatColor.GREEN + "自動承認を無効化しました。");
			}
		}
		if(ed == true)
		{
			Main.list.add(puuid);
			Config.updateList(puuid);
			player.sendMessage(Main.PlayerPrefix + ChatColor.GREEN + "自動承認を有効化しました。");
		}
	}

	public static boolean checkList(Player player)
	{
		String puuid = player.getUniqueId().toString();
		for(int i = 0; i < Main.list.size(); i++)
		{
			String uuid = Main.list.get(i);
			if(puuid.equals(uuid))
			{
				return true;
			}
		}
		return false;
	}

	public static Player getPlayer(String playername)
	{
		return Bukkit.getPlayerExact(playername);
	}
}
