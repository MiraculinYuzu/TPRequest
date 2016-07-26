package jp.seiya0818.tpr.back;

import jp.seiya0818.tpr.Config;
import jp.seiya0818.tpr.Main;
import jp.seiya0818.tpr.Process;
import jp.seiya0818.tpr.Warp;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommands implements CommandExecutor
{
	public final Main plugin;

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
					if(loc == null)
					{
						player.sendMessage(Main.PlayerPrefix + Config.getString("NoDeathLocation"));
						return true;
					}
					if(Process.vault == true)
					{
						try
						{
							Double money = Config.getDefaultDouble("Vault.price");
							EconomyResponse r = Process.getEconomy().withdrawPlayer(player, money);
							if(r.transactionSuccess())
							{
								player.sendMessage(Main.PlayerPrefix + Config.getString("PaidBackPrice").replaceAll("%price", money.toString())
										.replaceAll("%unit", Config.getString("Vault.unit")));
							}
							else
							{
								player.sendMessage(Main.PlayerPrefix + Config.getString("NoMoney").replaceAll("%price", money.toString())
										.replaceAll("%unit", Config.getString("Vault.unit")));
							}
						}
						catch(Exception e)
						{
							player.sendMessage(Main.PlayerPrefix + Config.getString("Error"));
							return true;
						}
					}
					Warp.warp(player, loc);
					if(Config.getBoolean("deleteBackLocation.onBackCommand") == true)
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
