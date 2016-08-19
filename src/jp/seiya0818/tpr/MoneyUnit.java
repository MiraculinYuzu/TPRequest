package jp.seiya0818.tpr;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.entity.Player;

public class MoneyUnit
{
	public static boolean withdraw(Player player, Double money)
	{
		try
		{
			EconomyResponse r = Process.getEconomy().withdrawPlayer(player, money);
			if(r.transactionSuccess())
			{
				player.sendMessage(Main.PlayerPrefix + Config.getString("PaidPrice").replaceAll("%price", money.toString())
						.replaceAll("%unit", Process.getUnit()));
				return true;
			}
			else
			{
				player.sendMessage(Main.PlayerPrefix + Config.getString("NoMoney").replaceAll("%price", money.toString())
						.replaceAll("%unit", Process.getUnit()));
				return false;
			}
		}
		catch(Exception e)
		{
			player.sendMessage(Main.PlayerPrefix + Config.getString("Error"));
			return false;
		}
	}
}
