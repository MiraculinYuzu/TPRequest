package jp.seiya0818.tpr;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

public class Process
{
	private static Main main;
	private static Economy econ = null;
	public static boolean vault = false;

	public static void Init()
	{
		main = Main.instance;
		if (!setupEconomy() && Config.getBoolean("Vault.use") == true)
		{
			main.getLogger().warning(Main.LoggerPrefix + "Vaultや経済プラグインが導入されていません。");
			vault = false;
		}
		else
		{
			vault = true;
		}
	}

	private static boolean setupEconomy()
	{
		if(main.getServer().getPluginManager().getPlugin("Vault") == null)
		{
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = main.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
		{
			return false;
		}
		econ = (Economy)rsp.getProvider();
		return econ != null;
	}

	public static Economy getEconomy()
	{
		return econ;
	}
}
