package com.watsonllc.economyplugin.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Economy;

public class Accounts {
	public static final int ADDITION = 000;
	public static final int SUBTRACTION = 001;
	public static final int MULTIPLY = 002;
	public static final int DIVIDE = 003;
	public static final int RESET = 004;
	
	Config c = new Config();
	public final double defaultBalance = c.getDouble("playerSettings.defaultBalance");
	
	private static File accountsFile = new File(Economy.instance.getDataFolder(), "accounts.yml");
	private static YamlConfiguration accounts = YamlConfiguration.loadConfiguration(accountsFile);
	
	public void create() {
		if(accountsFile.exists()) {
			load();
			return;
		}
		
		createDefault();
	}
	
	
	public void save() {
		try {
			accounts.save(accountsFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			accounts.load(accountsFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void createDefault() {
		save();
	}

	public void set(String path, Object obj) {
		accounts.set(path, obj);
	}
	
	public void setBalance(Player player, double amount) {
		String UUID = player.getUniqueId().toString();
		accounts.set("accounts."+ UUID + ".balance", amount);
		save();
	}
	
	public void updateBalance(Player player, int operation, double amount) {
		double balance = getBalance(player);
		
		switch (operation) {
		case ADDITION:
			balance += amount;
			break;
		case SUBTRACTION:
			balance -= amount;
			break;
		case MULTIPLY:
			balance *= amount;
			break;
		case DIVIDE:
			balance /= amount;
			break;
		case RESET:
			balance = amount;
			break;
		default:
			return;
		}
		
		setBalance(player, balance);
	}
	
	public Object get(String path) {
		return accounts.get(path);
	}
	
	public double getBalance(Player player) {
		String UUID = player.getUniqueId().toString();
		return accounts.getDouble("accounts."+ UUID +".balance");
	}
	
	public void createAccount(Player player) {
		String name = player.getName();
		UUID uuid = player.getUniqueId();
		
		set("accounts."+ uuid +".user", name);
		set("accounts."+ uuid +".balance", defaultBalance);
		save();
	}
	
	public void createAccount(Player player, double balance) {
		String name = player.getName();
		UUID uuid = player.getUniqueId();
		
		set("accounts."+ uuid +".user", name);
		set("accounts."+ uuid +".balance", balance);
		save();
	}
	
	public void closeAccount(Player player) {
		String uuid = player.getUniqueId().toString();
		
		set("accounts."+ uuid, null);
		save();
	}
	
	public String getTopRichestPlayersFormatted(int topN) {
        Map<String, Double> playerBalances = new HashMap<>();

        for (String key : accounts.getConfigurationSection("accounts").getKeys(false)) {
            String user = accounts.getString("accounts." + key + ".user");
            double balance = accounts.getDouble("accounts." + key + ".balance");
            playerBalances.put(user, balance);
        }

        List<Map.Entry<String, Double>> sortedPlayers = new ArrayList<>(playerBalances.entrySet());
        sortedPlayers.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        StringBuilder message = new StringBuilder(ChatColor.GOLD + "Top " + topN + " Highest Balances:\n");
        for (int i = 0; i < Math.min(topN, sortedPlayers.size()); i++) {
            Map.Entry<String, Double> player = sortedPlayers.get(i);
            message.append(ChatColor.GOLD).append(i + 1).append(". ")
                    .append(ChatColor.GRAY).append(player.getKey())
                    .append(ChatColor.GREEN).append(" $")
                    .append(player.getValue() < 0 ? ChatColor.RED : ChatColor.GREEN)
                    .append(player.getValue()).append("\n");
        }

        return message.toString();
    }
}