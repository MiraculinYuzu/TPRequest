package jp.seiya0818.tpr;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;

public class Config
{
	static Main plugin;
	private static File file;
	private static String name;
	private static YamlConfiguration conf;
	private static FileConfiguration dconf = getConfig();

	private static final String os_name = System.getProperty("os.name").toLowerCase();


	public Config(Main plugin, String code)
	{
		Config.plugin = plugin;
		Config.file = new File(plugin.getDataFolder(), "config.yml");
		Config.name = "config_" + code + ".yml";
		if(!Config.file.exists())
		{
			Config.file = new File(plugin.getDataFolder(), name);
			Reader defConfigStream = new InputStreamReader(plugin.getResource(name));
			if (defConfigStream != null)
			{
				plugin.saveResource(name, false);
				Config.file.renameTo(new File(file.getParentFile(), "config.yml"));
				Config.file = new File(plugin.getDataFolder(), "config.yml");
			}
		}
		Config.conf = YamlConfiguration.loadConfiguration(Config.file);
	}

	public static void createFile()
	{
		if ((isLinux()) || (isMac()))
		{
			new Config(Main.instance, "UTF-8");
		}
		else if (isWindows())
		{
			if(isVersion("1.9"))
			{
				new Config(Main.instance, "UTF-8");
			}
			else
			{
				new Config(Main.instance, "Shift-JIS");
			}
		}
	}

	public static void reload()
	{
		if(!Config.file.exists())
		{
			createFile();
		}
		conf = YamlConfiguration.loadConfiguration(file);
		InputStream defConfigStream = (plugin).getResource(name);
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig;
			if(isVersion("1.9"))
			{
				defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
			}
			else
			{
				defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			}
			conf.setDefaults(defConfig);
		}
	}

	private static boolean isVersion(String targetversion)
	{
		int version = versionInt(getVersion().split("\\."));
		int target = versionInt(targetversion.split("\\."));
		if (version >= target)
		{
			return true;
		}
		return false;
	}

	public static String getVersion()
	{
		String version = Bukkit.getServer().getVersion();
		version = version.split("\\(")[1];
		version = version.substring(4, version.length() - 1);
		return version;
	}

	private static int versionInt(String[] version)
	{
		if (version.length < 3)
		{
			version = new String[]{version[0], version[1], "0"};
		}
		if (version[1].length() == 1)
		{
			version[1] = "0" + version[1];
		}
		if (version[2].length() == 1)
		{
			version[2] = "0" + version[2];
		}
		version[2] = "0" + version[2];
		return Integer.parseInt(version[0]) * 100000 + Integer.parseInt(version[1]) * 1000 + Integer.parseInt(version[2]);
	}

	private static boolean isLinux()
	{
		return os_name.startsWith("linux");
	}

	private static boolean isMac()
	{
		return os_name.startsWith("mac");
	}

	private static boolean isWindows()
	{
		return os_name.startsWith("windows");
	}

	private static FileConfiguration getConfig()
	{
		return YamlConfiguration.loadConfiguration(getFile());
	}

	private static File getFile()
	{
		File dafl = Main.instance.getDataFolder();
		if (!dafl.exists())
		{
			dafl.mkdir();
		}
		return new File(dafl, "data.yml");
	}

	public static YamlConfiguration getDefaultConfig()
	{
		return conf;
	}

	public static FileConfiguration DataConfig()
	{
		return dconf;
	}

	public static String getString(String path)
	{
		String message = Config.conf.getString(path);
		if(message == null)
		{
			return ChatColor.RED + "メッセージの読み込みに失敗しました。";
		}
		return message.replaceAll("(&([a-f0-9]))", "\u00A7$2");
	}

	public static boolean getBoolean(String path)
	{
		return conf.getBoolean(path);
	}

	public static World getWorld(String path)
	{
		return Bukkit.getServer().getWorld(Config.dconf.getString(path));
	}

	public static int getDataInt(String path)
	{
		return dconf.getInt(path);
	}

	public static Double getDataDouble(String path)
	{
		return dconf.getDouble(path);
	}

	public static double getDefaultDouble(String path)
	{
		return conf.getDouble(path);
	}

	public static List<String> getUUID(String path)
	{
		return dconf.getStringList(path);
	}

	public static void setString(String path, String s)
	{
		dconf.set(path, s);
	}

	public static void setDouble(String path, double d)
	{
		dconf.set(path, d);
	}

	public static void setFloat(String path, float f)
	{
		dconf.set(path, f);
	}

	public static void updateList(String uuid)
	{
		String path = "UUID";
		List<String> conflist = conf.getStringList(path);
		conflist = Main.list;
		if(conflist.isEmpty())
		{
			dconf.set(path, null);
		}
		else
		{
			dconf.set(path, conflist);
		}
		savedata();
		return;
	}

	public static void savedata()
	{
		try
		{
			dconf.save(getFile());
		}
		catch(IOException e)
		{
			Bukkit.getLogger().warning(Main.LoggerPrefix + "データコンフィグの保存に失敗しました。");
		}
	}
}
