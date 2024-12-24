package com.watsonllc.economyplugin.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.Commands;
import com.watsonllc.economyplugin.config.Messages;
import com.watsonllc.economyplugin.economy.AccountController;

public class PayCMD {
	
	// args        0        1
	// 		PAY <PLAYER> <AMOUNT>
	
	public static boolean logic(Player player, String targetPlayer, String[] args) {
		if(Commands.usePermissions() && !player.hasPermission("economy.pay")) {
			player.sendMessage(Messages.get("noPermission"));
			return false;
		}
		
		if(args.length != 2) {
			player.sendMessage(Messages.get("invalidSyntax").replace("%syntax%", Utils.color("&7/&cpay &7<&cplayer&7> <&camount&7>")));
			return false;
		}
			
		if(args.length == 2) {
			double payAmount;
			
			if(!isInt(args[1]) || !isDouble(args[1])) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("invalid amount type").toUpperCase()));
				return false;
			} else
				payAmount = Double.valueOf(args[1]);
			
			Player target = Bukkit.getPlayer(targetPlayer);
			
			if(target == null) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("player '"+ targetPlayer + "' cant be found").toUpperCase()));
				return false;
			}
			
			if(target == player) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("you cant pay yourself").toUpperCase()));
				return false;
			}
			
			AccountController AC = new AccountController(player);
			if(AC.willOverdraw(payAmount) || AC.isNegative() && !player.hasPermission("economy.bypass")) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("insufficent funds").toUpperCase()));
				return false;
			}
			
			if(payAmount<=0) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("invalid amount").toUpperCase()));
				return false;
			}
			
			AccountController ACTarget = new AccountController(target);
			
			if(ACTarget.accountNull()) {
				player.sendMessage(Messages.get("transactionFailed").replace("%reason%", ("player '"+ targetPlayer +"' doesnt have an active account").toUpperCase()));
				return false;
			}
			
			AC.withdraw(payAmount);
			ACTarget.deposit(payAmount);
			
			player.sendMessage(Messages.get("paidToPlayer").replace("%player%", target.getName()).replace("%amount%", Utils.formatToCurrency(payAmount)));
			target.sendMessage(Messages.get("paidFromPlayer").replace("%player%", player.getName()).replace("%amount%", Utils.formatToCurrency(payAmount)));	
		}
		return true;
	}
	
	private static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}