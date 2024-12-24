package com.watsonllc.economyplugin.economy;

import com.watsonllc.economyplugin.config.Bank;

public class BankController {

	private Bank bank;
	
	public double BALANCE;
	public double TAX_RATE;
	
	public BankController() {
		bank = new Bank();
		
		BALANCE = bank.getBalance();
		TAX_RATE = bank.getTaxRate();
	}
	
	public double getBankBalance() {
		return BALANCE;
	}
	
	public void setBankBalance(double amount) {
		bank.setBalance(amount);
		bank.save();
	}

	public void deposit(double amount) {
		bank.updateBalance(Bank.ADDITION, amount);
	}
	
	public void withdraw(double amount) {
		bank.updateBalance(Bank.SUBTRACTION, amount);
	}
	
	public double reloadBalance() {
		this.BALANCE = bank.getBalance();
		return BALANCE;
	}
	
	public double getTaxRate() {
		return TAX_RATE;
	}
	
	public void setTaxRate(double rate) {
		bank.setTaxRate(rate);
	}
	
	public double reloadTaxRate() {
		this.TAX_RATE = bank.getTaxRate();
		return TAX_RATE;
	}
	
	public boolean willOverdraw(double amount) {
		reloadBalance();
		return BALANCE<amount;
	}
	
	public boolean isNegative() {
		reloadBalance();
		return BALANCE<0;
	}
}