package com.watsonllc.economyplugin.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.watsonllc.economyplugin.Economy;
import com.watsonllc.economyplugin.Utils;

public class Messages {
	private static File messagesFile = new File(Economy.instance.getDataFolder(), "messages.yml");
	private static YamlConfiguration messages = YamlConfiguration.loadConfiguration(messagesFile);
	
	public void create() {
		if(messagesFile.exists()) {
			load();
			return;
		}
		
		createDefault();
	}
	
	
	public void save() {
		try {
			messages.save(messagesFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			messages.load(messagesFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	private void createDefault() {
		set("accountClosed", "&aYour account was successfully closed!");
		set("accountNull", "&cYou dont have an active bank account! Use &7/bank account create &cto create an account.");
		set("accountExists", "&cYou already have an active bank account!");
		set("bankBalance", "&7Global Economy Balance: &a%balance%");
		set("bankBalanceNegative", "&7Global Economy Balance: &b%balance%");
		set("balance", "&7Balance: &a%balance%");
		set("balanceNegative", "&7Balance: &c%balance%");
		set("closeAccountFail", "&cAccount failed to close! Reason: &7%reason%");
		set("createAccount", "&aYou created a new bank account!");
		set("invalidInstance", "&cThis command can only be used by players");
		set("invalidPlayer", "&cThat player doesnt exist or cant be found");
		set("invalidSyntax", "&cInvalid Syntax! Usage: %syntax%");
		set("noPermission", "&cYou dont have permission to do that");
		set("paidFromPlayer", "&aYou were paid &7%amount% &afrom &7%player%");
		set("paidToPlayer", "&aYou paid &7%amount% &ato &7%player%");
		set("taxRate", "&7Global Economy Tax Rate: &a%taxrate%");
		set("transactionFailed", "&cTransaction failed! Reason: &7%reason%");
		save();
	}

	public void set(String path, Object obj) {
		messages.set(path, obj);
	}
	
	public static String get(String path) {
		return (String) Utils.color((String) messages.get(path));
	}
}
