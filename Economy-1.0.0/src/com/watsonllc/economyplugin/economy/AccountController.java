package com.watsonllc.economyplugin.economy;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.watsonllc.economyplugin.config.Accounts;
import com.watsonllc.economyplugin.config.Config;

public class AccountController {
	private Accounts accounts;
	private BankController BC = new BankController();
	
	private Player player;
	private UUID uuid;
	private double balance;
	
	public String accountPath;
	
	// SETTINGS
	Config c = new Config();
	private final boolean dynamicBank = (boolean) c.get("pluginSettings.useDynamicBank");
	
	public AccountController(Player player) {
		accounts = new Accounts();
		
		this.player = player;
		this.uuid = player.getUniqueId();
		
		accountPath = "accounts."+ uuid;
		
		if(!accountNull())
			this.balance = accounts.getBalance(player);
	}
	
	public boolean accountNull() {
		return accounts.get(accountPath) == null;
	}
	
	public void createAccount() {
		accounts.createAccount(player);
		
		if(dynamicBank) {
			Accounts a = new Accounts();
			BC.withdraw(a.defaultBalance);
		}
	}
	
	public void createAccount(double amount) {
		accounts.createAccount(player, amount);
		
		if(dynamicBank)
			BC.withdraw(amount);
	}
	
	public void closeAccount() {
		if(isNegative())
			return;
		
		if(dynamicBank)
			BC.deposit(getBalance());
		
		accounts.closeAccount(player);
	}
	
	public double getBalance() {
		return accounts.getBalance(player);
	}
	
	public void reloadBalance() {
		this.balance = getBalance();
	}
	
	public void deposit(double amount) {
		reloadBalance();
		accounts.updateBalance(player, Accounts.ADDITION, amount);
	}
	
	public void depositFromBank(double amount) {
		reloadBalance();
		accounts.updateBalance(player, Accounts.ADDITION, amount);
		
		BankController BC = new BankController();
		BC.withdraw(amount);
	}
	
	public void withdraw(double amount) {		
		reloadBalance();
		accounts.updateBalance(player, Accounts.SUBTRACTION, amount);
	}
	
	public void multiply(double amount) {
		reloadBalance();
		accounts.updateBalance(player, Accounts.MULTIPLY, amount);
	}
	
	public void divide(double amount) {
		reloadBalance();
		accounts.updateBalance(player, Accounts.DIVIDE, amount);
	}
	
	public boolean willOverdraw(double amount) {
		reloadBalance();
		return balance<amount;
	}
	
	public boolean isNegative() {
		reloadBalance();
		return balance<0;
	}
}