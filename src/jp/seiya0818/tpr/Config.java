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
	static Main main;
	private static File file;
	private static String name;
	private static YamlConfiguration conf;
	private static FileConfiguration dconf = getConfig();

	public Config(Main main, String code)
	{
		Config.main = main;
		Config.file = new File(main.getDataFolder(), "config.yml");
		Config.name = "config_" + code + ".yml";
		if(!Config.file.exists())
		{
			Config.file = new File(main.getDataFolder(), name);
			Reader defConfigStream = new InputStreamReader(main.getResource(name));
			if(defConfigStream != null)
			{
				main.saveResource(name, false);
				Config.file.renameTo(new File(file.getParentFile(), "config.yml"));
				Config.file = new File(main.getDataFolder(), "config.yml");
			}
		}
		Config.conf = YamlConfiguration.loadConfiguration(Config.file);
	}

	public static void createFile(boolean delete)
	{
		String os = checkOS();
		if(os.equals("Linux") || os.equals("Mac"))
		{
			if(delete == true)
			{
				file.delete();
				new Config(Main.instance, "Shift-JIS");
				Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + getString("ConvertFile").replaceAll("encode", "Shift-JIS"));
				return;
			}
			new Config(Main.instance, "UTF-8");
		}
		else if(os.equals("Windows"))
		{
			if(checkVersion() == true)
			{
				if(delete == true)
				{
					file.delete();
					new Config(Main.instance, "Shift-JIS");
					Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + getString("ConvertFile").replaceAll("encode", "Shift-JIS"));
					return;
				}
				new Config(Main.instance, "UTF-8");
			}
			else
			{
				if(delete == true)
				{
					file.delete();
					new Config(Main.instance, "UTF-8");
					Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + getString("ConvertFile").replaceAll("encode", "UTF-8"));
					return;
				}
				new Config(Main.instance, "Shift-JIS");
			}
		}
		else
		{
			Bukkit.getServer().getPluginManager().disablePlugin(Main.instance);
			Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + ChatColor.RED + "This plugin does not correspond to the OS, which you are using.");
			Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + ChatColor.RED + "Please check if you are using Windows, Mac or Linux.");
			Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + ChatColor.RED + "(このプラグインはあなたの使用しているOSに対応していません。)");
			Bukkit.getConsoleSender().sendMessage(Main.LoggerPrefix + ChatColor.RED + "(WindowsかMacかLinuxを使用しているか確認して下さい。)");
		}
	}

	public static void reload()
	{
		if(!Config.file.exists())
		{
			createFile(false);
		}
		conf = YamlConfiguration.loadConfiguration(file);
		InputStream defConfigStream = (main).getResource(name);
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig;
			if(checkVersion() == true)
			{
				defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
			}
			else
			{
				defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
			}
			conf.setDefaults(defConfig);
		}

		File datafile= new File(main.getDataFolder(), "data.yml");
		if(datafile.exists())
		{
			Config.dconf = YamlConfiguration.loadConfiguration(datafile);
		}
		else{}
	}

	private static boolean checkVersion()
	{
		String ver = Bukkit.getServer().getVersion();
		if(ver.contains("1.10") || ver.contains("1.9"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private static String checkOS()
	{
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("linux"))
		{
			return "Linux";
		}
		if(os.toLowerCase().startsWith("mac"))
		{
			return "Mac";
		}
		if(os.toLowerCase().startsWith("windows"))
		{
			return "Windows";
		}
		return null;
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

	public static String getDataString(String path)
	{
		return dconf.getString(path);
	}

	public static boolean checkContains(String path)
	{
		if(dconf.contains(path))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static boolean getBoolean(String path)
	{
		return conf.getBoolean(path);
	}

	public static List<String> getDataList(String path)
	{
		return dconf.getStringList("path");
	}

	public static World getWorld(String path)
	{
		return Bukkit.getServer().getWorld(Config.getDataString(path));
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

	public static void setInt(String path, int i)
	{
		dconf.set(path, i);
	}

	public static void setList(String path, List<String> l)
	{
		dconf.set(path, l);
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
