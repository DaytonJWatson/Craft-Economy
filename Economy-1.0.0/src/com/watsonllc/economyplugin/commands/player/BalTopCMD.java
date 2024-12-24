package com.watsonllc.economyplugin.commands.player;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.config.Accounts;

public class BalTopCMD {
	
	public static boolean logic(Player player) {
		
		Accounts a = new Accounts();
		
		player.sendMessage(a.getTopRichestPlayersFormatted(10));
		
		return true;
	}
	
}
