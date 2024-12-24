package com.watsonllc.economyplugin;

import java.text.DecimalFormat;

import org.bukkit.ChatColor;

public class Utils {
	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static String formatToCurrency(double amount) {
        DecimalFormat formatter = new DecimalFormat("$###,###,###.00");
        return formatter.format(amount);
    }
	
	public static String formatToPercentage(double decimal) {
        DecimalFormat formatter = new DecimalFormat("0%");
        return formatter.format(decimal);
    }
}
