package jp.seiya0818.tpr;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

public class Process
{
	private static Main main;
	private static Economy econ = null;
	private static String unit;
	public static boolean vault = false;

	public static void Init()
	{
		main = Main.instance;
		if (!setupEconomy() && Config.getBoolean("Vault.use") == true)
		{
			main.getLogger().warning("Vault has not installed.");
			main.getLogger().warning("(Vaultがインストールされていません。)");
		}
		else
		{
			unit = Config.getString("Vault.unit");
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
		vault = true;
		return econ != null;
	}

	public static Economy getEconomy()
	{
		return econ;
	}

	public static String getUnit()
	{
		return unit;
	}
}
