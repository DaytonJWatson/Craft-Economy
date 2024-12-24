package com.watsonllc.economyplugin.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.Economy;
import com.watsonllc.economyplugin.Utils;
import com.watsonllc.economyplugin.commands.player.BalTopCMD;
import com.watsonllc.economyplugin.commands.player.BalanceCMD;
import com.watsonllc.economyplugin.commands.player.BankCMD;
import com.watsonllc.economyplugin.commands.player.PayCMD;
import com.watsonllc.economyplugin.config.Config;
import com.watsonllc.economyplugin.config.Messages;

public class Commands implements CommandExecutor, TabCompleter {

	private static final List<String> permissions = Arrays.asList("economy.balance", "economy.bank");
	private static final int TOTAL_PERMISSIONS = 2;

	public static void setup() {
		Economy.instance.getCommand("balance").setExecutor(new Commands());
		Economy.instance.getCommand("baltop").setExecutor(new Commands());
		Economy.instance.getCommand("bank").setExecutor(new Commands());
		Economy.instance.getCommand("economy").setExecutor(new Commands());
		Economy.instance.getCommand("pay").setExecutor(new Commands());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Economy.instance.getLogger().warning(Messages.get("invalidInstance"));
			return false;
		}

		Player player = (Player) sender;

		if(args.length == 0) {
			if(label.equalsIgnoreCase("economy")) {
				helpMenu(player);
				return true;
			}
				
			if(label.equalsIgnoreCase("balance") || label.equalsIgnoreCase("bal")) {
				BalanceCMD.logic(player);
				return true;
			}	
			
			if(label.equalsIgnoreCase("baltop")) {
				BalTopCMD.logic(player);
				return true;
			}	
		}
		
		if(args.length == 1) {
			if(label.equalsIgnoreCase("bank")) {
				BankCMD.logic(player, args);
				return true;
			}	

			if(label.equalsIgnoreCase("pay")) {
				PayCMD.logic(player, args[0], args);
				return true;
			}	
		}
		
		if(args.length == 2) {
			if(label.equalsIgnoreCase("bank")) {
				BankCMD.logic(player, args);
				return true;
			}	

			if(label.equalsIgnoreCase("pay")) {
				PayCMD.logic(player, args[0], args);
				return true;
			}
		}
		
		helpMenu(player);

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> completions = new ArrayList<>();

		// bank account
		// bank balance
		// bank taxrate
		if (label.equalsIgnoreCase("bank")) {
			if (args.length == 1) {
				List<String> subCommands = Arrays.asList("account", "balance", "taxrate");
				return subCommands;
			}
			// bank account create
			// bank account close
			if (args.length == 2 && args[0].equalsIgnoreCase("account")) {
				List<String> subCommands = Arrays.asList("create", "close");
				return subCommands;
			}
		}

		// pay <player> <amount>
		if (label.equalsIgnoreCase("pay")) {
			if (args.length == 1) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					completions.add(player.getName());
				}
			}

			if (args.length == 2) {
				List<String> subCommands = Arrays.asList("1", "10", "100", "1000", "10000", "100000", "1000000");
				return subCommands;
			}
		}

		return completions;
	}

	public static boolean usePermissions() {
		Config c = new Config();
		return (boolean) c.get("pluginSettings.usePermissions");
	}

	private static boolean helpMenu(Player player) {
		player.sendMessage(Utils.color("&8==================== &aEconomy Help &8===================="));

		if (player.hasPermission("economy.balance") || !usePermissions())
			player.sendMessage(Utils.color("&8/&abalance &7- View your bank account balance"));

		if (player.hasPermission("economy.bank") || !usePermissions())
			player.sendMessage(Utils.color(
					"&8/&abank &8[&7account&8|&7balance&8|&7taxrate&8] &7- View the bank settings and manage your account"));

		if (player.hasPermission("economy.pay") || !usePermissions())
			player.sendMessage(
					Utils.color("&8/&apay &8<&7player&8> <&7amount&8> &7- &7Pay the amount to another player"));

		if (usePermissions()) {
			int invalidPerms = 0;
			for (int i = 0; i < permissions.size(); i++) {
				if (!player.hasPermission(permissions.get(i))) {
					invalidPerms++;
				}
			}

			if (invalidPerms == TOTAL_PERMISSIONS) {
				player.sendMessage(Utils.color(
						"&cYou need a permission manager plugin to use commands! If you dont have a permission manager, you can disable 'usePermissions' in the config.yml"));
				player.sendMessage(Utils.color("&6Available permissions&7: &f" + permissions.toString()));
			}
		}
		return true;
	}

}