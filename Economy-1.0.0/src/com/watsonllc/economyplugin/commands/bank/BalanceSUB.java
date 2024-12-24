package com.watsonllc.economyplugin.commands.bank;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Messages;
import com.watsonllc.economyplugin.economy.BankController;

public class BalanceSUB {
	
	public static boolean logic(Player player) {
		if(Commands.usePermissions() && !player.hasPermission("economy.bank.balance")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}
		
		BankController BC = new BankController();
		
		if(BC.isNegative())
			player.sendMessage(Messages.get("bankBalanceNegative").replace("%balance%", Utils.formatToCurrency(BC.getBankBalance())));
		else
			player.sendMessage(Messages.get("bankBalance").replace("%balance%", Utils.formatToCurrency(BC.getBankBalance())));
		
		return true;
	}
}