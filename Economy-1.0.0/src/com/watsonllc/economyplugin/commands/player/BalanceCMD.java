package com.watsonllc.economyplugin.commands.player;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Messages;
import com.watsonllc.economyplugin.economy.AccountController;

public class BalanceCMD {
	
	public static boolean logic(Player player) {
		if(Commands.usePermissions() && !player.hasPermission("economy.balance")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}
		
		AccountController AC = new AccountController(player);
		
		if(AC.accountNull()) {
			player.sendMessage(Messages.get("accountNull"));
			return false;
		}
		
		double BALANCE = AC.getBalance();
		
		if(AC.isNegative())
			player.sendMessage(Messages.get("balanceNegative").replace("%balance%", Utils.formatToCurrency(BALANCE)));
		else
			player.sendMessage(Messages.get("balance").replace("%balance%", Utils.formatToCurrency(BALANCE)));
		
		return true;
	}
}