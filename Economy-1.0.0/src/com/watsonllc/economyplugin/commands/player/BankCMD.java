package com.watsonllc.economyplugin.commands.player;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.commands.bank.BalanceSUB;
import com.watsonllc.economyplugin.commands.bank.AccountSUB;
import com.watsonllc.economyplugin.commands.bank.TaxRateSUB;
import com.watsonllc.economyplugin.config.Messages;

public class BankCMD {

	public static boolean logic(Player player, String[] args) {
		if (Commands.usePermissions() && !player.hasPermission("economy.bank")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}

		// bank balance
		// bank taxrate
		// bank create
		switch (args[0]) {
		case "balance":
			return BalanceSUB.logic(player);
		case "taxrate":
			return TaxRateSUB.logic(player);
		case "account":
			return AccountSUB.logic(player, args);
		}

		return true;
	}
}