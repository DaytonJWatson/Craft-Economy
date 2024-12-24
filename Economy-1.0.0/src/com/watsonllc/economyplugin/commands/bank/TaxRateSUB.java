package com.watsonllc.economyplugin.commands.bank;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Messages;
import com.watsonllc.economyplugin.economy.BankController;

public class TaxRateSUB {

	public static boolean logic(Player player) {
		if(Commands.usePermissions() && !player.hasPermission("economy.bank.taxrate")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}
		
		BankController BC = new BankController();
		
		player.sendMessage(Messages.get("taxRate").replace("%taxrate%", Utils.formatToPercentage(BC.getTaxRate())));
		
		return true;
	}
}
