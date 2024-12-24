package com.watsonllc.economyplugin.config;

import com.watsonllc.economyplugin.Economy;

public class Config {
	
	public void setup() {
		Accounts accounts = new Accounts();
		accounts.create();
		
		Bank bank = new Bank();
		bank.create();
		
		create();
		
		Messages messages = new Messages();
		messages.create();
	}
	
	public void create() {
		Economy.instance.getConfig().options().copyDefaults(true);
		Economy.instance.saveDefaultConfig();
	}
	
	public void save() {
		Economy.instance.saveConfig();
	}
	
	public void reload() {
		Economy.instance.reloadConfig();
	}
	
	public void set(String path, Object obj) {
		Economy.instance.getConfig().set(path, obj);
		save();
	}
	
	public Object get(String path) {
		return Economy.instance.getConfig().get(path);
	}
	
	public double getDouble(String path) {
		return Economy.instance.getConfig().getDouble(path);
	}
	
}
