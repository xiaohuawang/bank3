package bank3;

import java.util.Date;
import java.util.GregorianCalendar;

public class Account
{

	private String account_number;

	private double starting_balance;

	private int customer_id;

	
	private int account_type_id;
	
	


	


	public int getAccount_type_id()
	{
		return account_type_id;
	}
	public void setAccount_type_id(int account_type_id)
	{

		this.account_type_id = account_type_id;
	}
	public int getCustomer_id()
	{
		return customer_id;
	}
	public void setCustomer_id(int customer_id)
	{
		this.customer_id = customer_id;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public double getStarting_balance() {
		return starting_balance;
	}

	public void setStarting_balance(double starting_balance) {
		this.starting_balance = starting_balance;

	}


	