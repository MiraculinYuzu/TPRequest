package jp.seiya0818.tpr;

import java.util.List;

import jp.seiya0818.tpr.back.BackCommands;
import jp.seiya0818.tpr.hub.HubCommands;
import jp.seiya0818.tpr.spawn.SpawnCommands;
import jp.seiya0818.tpr.warp.SignListener;
import jp.seiya0818.tpr.warp.WarpCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public static Main instance;
	private final TeleportEventHandler MainListener = new TeleportEventHandler();
	private final SignListener SignListener = new SignListener();
	public static String LoggerPrefix = ChatColor.WHITE + "[" + ChatColor.RED + "Teleport_Plugin" + ChatColor.WHITE + "]";
	public static String PlayerPrefix = ChatColor.YELLOW+ "[Teleport]";
	public static List<String> list;

	public void onEnable()
	{
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(MainListener, this);
		pm.registerEvents(SignListener, this);

		instance = this;
		Config.createFile(false);
		Process.Init();

		getCommand("tprequest").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tpr").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tpa").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tpd").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tphere").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tpall").setExecutor(new TeleportCommandExecutor(this));
		getCommand("tptoggle").setExecutor(new TeleportCommandExecutor(this));
		getCommand("back").setExecutor(new BackCommands(this));
		getCommand("spawn").setExecutor(new SpawnCommands(this));
		getCommand("setspawn").setExecutor(new SpawnCommands(this));
		getCommand("delspawn").setExecutor(new SpawnCommands(this));
		getCommand("hub").setExecutor(new HubCommands(this));
		getCommand("sethub").setExecutor(new HubCommands(this));
		getCommand("delhub").setExecutor(new HubCommands(this));
		getCommand("chatclear").setExecutor(new HubCommands(this));
		getCommand("warp").setExecutor(new WarpCommands(this));

		Main.list = Config.getUUID("UUID");
		setValue();

		Bukkit.getConsoleSender().sendMessage(LoggerPrefix + ChatColor.AQUA + "プラグインが正常に読み込まれました。");
	}

	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(LoggerPrefix + ChatColor.AQUA + "プラグインが正常に終了しました。");
	}

	private void setValue()
	{
		if(Config.getBoolean("WarpRiddenEntity") == true)
		{
			Warp.tpriding = true;
		}
		if(Config.getBoolean("deleteBackLocation.onPlayerQuit") == true)
		{
			TeleportEventHandler.quit = true;
		}
		if(Config.getBoolean("deleteBackLocation.onPlayerKick") == true)
		{
			TeleportEventHandler.kick = true;
		}
		if(Config.getBoolean("deleteBackLocation.onBackCommand") == true)
		{
			BackCommands.back = true;
		}
		PlayerPrefix = Config.getString("Prefix");
	}
}
