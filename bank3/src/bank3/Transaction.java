package bank3;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Transaction
{

	private String account_number;

	private int transaction_type_id;
	private Date transaction_date = new Date();
	private Date t_date = new Date();
	private double amount;
	private set_date set ;
	
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public int getTransaction_type_id() {
		return transaction_type_id;
	}
	public void setTransaction_type_id(int transaction_type_id) {
		this.transaction_type_id = transaction_type_id;
	}
	public Date getTransaction_date() {
		return t_date;
	}
	public void setTransaction_date(Date transaction_date) {
		t_date = transaction_date;
	}
	
	
	public void process_transaction(Account account){
		
		switch (transaction_type_id){
		case 1:
			account.setStarting_balance(account.getStarting_balance() + amount);
			break;
		case 2:
		case 3:
		case 4:
			account.setStarting_balance(account.getStarting_balance() - amount);
			if(account.getStarting_balance() < 0){ 
				account.setStarting_balance(account.getStarting_balance() - 35);
			}
			break;
		default: System.out.println("Invalid transaction type.");
		}
	}

}