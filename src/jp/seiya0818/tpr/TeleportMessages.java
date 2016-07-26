package jp.seiya0818.tpr;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportMessages
{
	public static void TeleportedMsg(Player player, Player target)
	{
		player.sendMessage(Main.PlayerPrefix + " " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + "にテレポートしました。");
	}
	public static void HaveTeleportedMsg(Player player, String string)
	{
		player.sendMessage(Main.PlayerPrefix + " " + ChatColor.GOLD + string + ChatColor.GREEN + "にテレポートさせました。");
	}
	public static void HaveSentRequestdMsg(Player player, Player target)
	{
		player.sendMessage(Main.PlayerPrefix + " " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + "にリクエストを送らさせました。");
	}
	public static void TeleportWorldMsg(Player player)
	{
		player.sendMessage(Main.PlayerPrefix + ChatColor.GREEN + "別のワールドにテレポートしました。");
	}

	public static void isnotPlayerMsg(CommandSender sender)
	{
		sender.sendMessage(Main.PlayerPrefix + ChatColor.RED + "このコマンドはコンソールからは実行できません。");
	}
	public static void HasnotPermissionMsg(CommandSender sender)
	{
		sender.sendMessage(Main.PlayerPrefix + ChatColor.RED + "このコマンドを実行する権限がありません。");
	}
	public static void NoPlayerMsg(CommandSender sender, String target)
	{
		sender.sendMessage(Main.PlayerPrefix + ChatColor.RED + "プレイヤー " + ChatColor.GOLD + target
				+ ChatColor.RED + " は見つかりませんでした。");
	}
	public static void CouldnotFind(Player player, String string)
	{
		player.sendMessage(Main.PlayerPrefix + " " + ChatColor.RED + string + " は見つかりませんでした。");
	}
	public static void NoCmdMessage(CommandSender sender)
	{
		sender.sendMessage(Main.PlayerPrefix + ChatColor.RED + "コマンドが見つかりませんでした。");
	}
	public static void HasnotMetadataMsg(Player player)
	{
		player.sendMessage(Main.PlayerPrefix + ChatColor.RED + "テレポート申請は届いていません。");
	}
	public static void HasnotMetadataMsg(Player player, String target)
	{
		player.sendMessage(Main.PlayerPrefix + " " + ChatColor.GOLD + target + ChatColor.RED + " からのテレポート申請は届いていません。");
	}
	public static void NotargMsg(CommandSender sender)
	{
		sender.sendMessage(Main.PlayerPrefix + ChatColor.RED + "このコマンドの後にプレイヤー名を指定する必要があります。");
	}
}
