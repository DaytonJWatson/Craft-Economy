package com.watsonllc.economyplugin;

import org.bukkit.plugin.java.JavaPlugin;

import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Config;

public class Economy extends JavaPlugin {

	public static Economy instance;
	
	private Config config = new Config();
	
	@Override
	public void onEnable() {
		instance = this;
		
		Commands.setup();
		config.setup();
	}
}