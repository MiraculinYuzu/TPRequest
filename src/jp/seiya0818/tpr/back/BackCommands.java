package jp.seiya0818.tpr.back;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.MoneyUnit;
import jp.seiya0818.tpr.Process;
import jp.seiya0818.tpr.Warp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommands implements CommandExecutor
{
	public final Main plugin;
	public static boolean back = false;

	public BackCommands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length == 0)
		{
			if(cmd.getName().equalsIgnoreCase("back"))
			{
				if(!(sender instanceof Player))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("PlayerOnlyCmd"));
					return true;
				}
				else if(!sender.hasPermission("Teleport.back"))
				{
					sender.sendMessage(Main.PlayerPrefix + Config.getString("NoPerms"));
					return true;
				}
				else
				{
					Player player = (Player) sender;
					Location loc = BackProcess.getLocation(player);
					Double money = Config.getDefaultDouble("Valut.back-price");
					if(loc == null)
					{
						player.sendMessage(Main.PlayerPrefix + Config.getString("NoDeathLocation"));
						return true;
					}
					if(Process.vault == true && money != 0 && !player.hasPermission("teleport.bypass.back"))
					{
						if(MoneyUnit.withdraw(player, money) == false)
						{
							return true;
						}
					}
					Warp.warp(player, loc);
					if(back == true)
					{
						BackProcess.removePlayer(player);
						return true;
					}
				}
			}
		}
		return false;
	}
}
