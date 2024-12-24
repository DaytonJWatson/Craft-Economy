package com.watsonllc.economyplugin.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.watsonllc.economyplugin.Economy;

public class Bank {

	public static final int ADDITION = 000;
	public static final int SUBTRACTION = 001;
	public static final int MULTIPLY = 002;
	public static final int DIVIDE = 003;
	public static final int RESET = 004;

	private static File bankFile = new File(Economy.instance.getDataFolder(), "bank.yml");
	private static YamlConfiguration bank = YamlConfiguration.loadConfiguration(bankFile);

	public void create() {
		if (bankFile.exists()) {
			load();
			return;
		}

		createDefault();
	}

	public void save() {
		try {
			bank.save(bankFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			bank.load(bankFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void createDefault() {
		Config c = new Config();

		set("bank.balance", c.get("bankSettings.defaultBalance"));
		set("bank.taxRate", c.get("bankSettings.defaultTaxRate"));
		save();
	}

	public Object get(String path) {
		return bank.get(path);
	}

	public void set(String path, Object obj) {
		bank.set(path, obj);
	}

	public void updateBalance(int operation, double amount) {
		double currentBalance = getBalance();
		
		switch (operation) {
		case ADDITION:
			currentBalance += amount;
			break;
		case SUBTRACTION:
			currentBalance -= amount;
			break;
		case MULTIPLY:
			currentBalance *= amount;
			break;
		case DIVIDE:
			currentBalance /= amount;
			break;
		case RESET:
			currentBalance = amount;
			break;
		default:
			return;
		}
		
		setBalance(currentBalance);
		save();
	}

	public void setBalance(double d) {
		bank.set("bank.balance", d);
		save();
	}

	public double getBalance() {
		return bank.getLong("bank.balance");
	}

	public double getDefaultBalance() {
		Config c = new Config();
		return (double) c.get("bankSettings.defaultBalance");
	}

	public double getTaxRate() {
		return bank.getDouble("bank.taxRate");
	}

	public void setTaxRate(double taxRate) {
		bank.set("bank.taxRate", taxRate);
		save();
	}

	public double getDefaultTaxRate() {
		Config c = new Config();
		return (double) c.get("bankSettings.defaultTaxRate");
	}
}