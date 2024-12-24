package com.watsonllc.economyplugin.commands.bank;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Messages;
import com.watsonllc.economyplugin.economy.AccountController;

public class AccountSUB {
	public static boolean logic(Player player, String[] args) {
		if(Commands.usePermissions() && !player.hasPermission("economy.bank.account")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}
		
		// bank account
		if(args.length == 1	&& args[0].equalsIgnoreCase("account")) {
			player.sendMessage(Messages.get("invalidSyntax").replace("%syntax%", Utils.color("&7/&cbank account &7<&ccreate&7|&cclose&7>")));
			return false;
		}
		
		AccountController AC = new AccountController(player);
		
		// bank account close
		if(args.length == 2 && args[1].equalsIgnoreCase("close")) {
			if(AC.accountNull() == true) {
				player.sendMessage(Messages.get("accountNull"));
				return false;
			}
			
			if(AC.isNegative()) {
				player.sendMessage(Messages.get("closeAccountFail").replace("%reason%", ("negative account balance").toUpperCase()));
				return false;
			}
			
			AC.closeAccount();
			player.sendMessage(Messages.get("accountClosed"));
		}
		
		// bank account create
		if(args.length == 2 && args[1].equalsIgnoreCase("create")) {
			if(AC.accountNull() == false) {
				player.sendMessage(Messages.get("accountExists"));
				return false;
			}
			
			AC.createAccount();
			player.sendMessage(Messages.get("createAccount"));
		}

		return true;
	}
}