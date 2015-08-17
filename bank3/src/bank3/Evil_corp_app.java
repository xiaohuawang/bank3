package bank3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Evil_corp_app {
	public static final double overdraftFee = 35.0;
	public static void main(String [] args)
	{
		
		Scanner key = new Scanner(System.in);
		Validator val = new Validator();
		
		//Initializing parameters for account class

		String account_number;
		String name;
		String starting_balance;
		String birth_date;
		
		//Initializing parameters for transaction class.
		

		//private String account_number;
		String transaction_type_id="";
		String amount="";
		String transaction_date = "";
		
		
		
		//Add account information into the database.
		AccountDBHelper all_in_sql = new AccountDBHelper();
		
		int selection = getSelection(key);

		while(selection != 5)
		{
			switch (selection)
			{
				case 1 : addCustomer(key);
					break;
				case 2 : addAccount(key);
					break;
				case 3 : performTransaction(key);
					break;
				case 4 : transferMoney(key);
					break;
			}
			selection = getSelection(key);
		}
		
	}
	
	public static int getSelection(Scanner key)
	{
		System.out.println("**************************************************");
		System.out.println("**********Welcome to Evil Corp bank **************");
		System.out.println("**************************************************");
		System.out.println();
		System.out.println("Please select what do you want to do?");
		System.out.println("1. Add a customer");
		System.out.println("2. Add an account to Existing Customer");
		System.out.println("3. Perform Transactions");
		System.out.println("4. Transfer money between accounts");
		System.out.println("5. Exit");
	
		
		int selection = 0;
		String selectionStr ="";
		boolean isValid = false;
		while(!isValid)
		{
			System.out.print("Selection: ");
			selectionStr = key.next();
			key.nextLine();
			
			isValid = Validator.validateIntWithRange(selectionStr, 1, 5);
			if (!isValid)
			{
				System.out.println("Invalid selection, please try again!");
			}
		}
		selection = Integer.parseInt(selectionStr);
		return selection;
	}
	
	public static void addCustomer(Scanner key)
	{
		System.out.println("Add customer");
		Customer customer = new Customer();
		
		System.out.print("Enter first name: ");
		customer.setFirst_name(key.next());
		key.nextLine();
		
		System.out.print("Enter last name: ");
		customer.setLast_name(key.next());
		key.nextLine();
		
		boolean availablePhone = false;
		AccountDBHelper db = new AccountDBHelper();
		while(!availablePhone)
		{
			System.out.print("Enter phone number: ");
			customer.setPhone_number(key.next());
			key.nextLine();
			
			availablePhone = db.availablePhone(customer.getPhone_number());
			if(!availablePhone)
			{
				System.out.println("Phone is not available, please try again!");
			}
		}
		db.insertCustomer(customer);
		System.out.println("Customer Created!");
	}
	

	
	public static void addAccount(Scanner key)
	{

		AccountDBHelper helper = new  AccountDBHelper();
	
		System.out.print("Enter phone number: ");
		String phone_number = key.next();
		key.nextLine();
		
		while(helper.availablePhone(phone_number)){
	
			System.out.println("Customer is not exists ");
			System.out.print("Enter phone number: ");
			phone_number = key.next();
			key.nextLine();
		}
		Customer customer = helper.findCustomerByPhone(phone_number);
		Account account = new Account();

		account.setCustomer_id(customer.getId());
		
		System.out.print("Enter account number: ");
		String account_number = key.next();
		key.nextLine();
		
		while(!helper.availableAccountNumber(account_number))
		{
	
			System.out.println("Account number existed ");
			System.out.print("Enter account number: ");
			account_number = key.next();
			key.nextLine();
		}
		account.setAccount_number(account_number);
		
		
		System.out.print("Enter starting balance : ");
		String starting_balance = key.next();
		key.nextLine();
		
		
		
		boolean isValid = false;
		while(!isValid)
		{
			if(Validator.validateDoubleWithRange(starting_balance, 0, 1000000)){
				isValid = true;
			}else{
				System.out.println("Invalid balance, try again.");
				starting_balance = key.next();
				key.nextLine();
			}
			
		}
		
		account.setStarting_balance(Double.parseDouble(starting_balance));
		helper.insertAccount(account);
		System.out.println("Account created");
		
		
	}
	
	public static void performTransaction(Scanner key)
	{
		AccountDBHelper all_in_sql = new AccountDBHelper();
		System.out.println("Perform transactions");
		
		String account_number = " ";
		transaction_loop:
		while(true)
		{
			System.out.println("Enter account number or -1 to exit transactions: ");
			account_number = key.next();
			key.nextLine();
			
			if(account_number.equalsIgnoreCase("-1"))
			{
				break transaction_loop;
			}
			
			
			//Look for the account number and see if that in the database.
			
			boolean hasAccount = false;
			Account account = new Account();
			while(!hasAccount)
			{
				if(account_number.equalsIgnoreCase("-1"))
				{
					break transaction_loop;
				}
				
				account = all_in_sql.getAccountFromNumber(account_number);
				
				if(account.getAccount_number() != null)
				{
					hasAccount = true;
				}
				else
				{
					System.out.println("Account not found! Please try again");
					System.out.println("Enter account number or -1 to exit transactions: ");
					account_number = key.next();
					key.nextLine();
				}
			}
			
			String transaction_type_id = " ";
			
			boolean validType = false;
			while(!validType)
			{
				System.out.println("Enter the transaction type: \n"
						+ "1 - Deposite.\n"
						+ "2 - Check.\n"
						+ "3 - Withdrawal.\n"
						+ "4 - Debit Card.");
				transaction_type_id = key.next();
				key.nextLine();
				validType = Validator.validateIntWithRange(transaction_type_id, 1, 4);
				
				if(!validType)
				{
					System.out.println("Invalid type, please try again!");
				}
			}
			
			Transaction transaction = new Transaction();
			
			
			transaction.setAccount_number(account_number);
			//set tran type id
			transaction.setTransaction_type_id(Integer.parseInt(transaction_type_id));
			
			
			String amount = "0";
			validType = false;
			while(!validType)
			{
				System.out.println("Enter Transaction amount: ");
				
				amount = key.next();
				key.nextLine();
				validType = Validator.validateDoubleWithRange(amount, 0, 10000000);
				
				if(!validType)
				{
					System.out.println("Invalid amount, please try again! (from 0 to 10000000)");
				}
			}
			
			//set tran amount
			transaction.setAmount(Double.parseDouble(amount));
			
			String transaction_date = " ";
			validType = false;
			while(!validType)
			{
				System.out.println("Enter Transaction date: (MM/DD/YYYY)");
				transaction_date =  key.next();
				key.nextLine();
				validType = Validator.validateDateWithFormat(transaction_date);
				if (!validType){
					System.out.println("Invalid date, try insert date (MM/DD/YYYY): ");
				}

			}
		
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
			
			try
			{
				transaction.setTransaction_date(sdf.parse(transaction_date));
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//transaction.process_transaction(account);
			
			all_in_sql.addTransaction(transaction);
		
			//update balance
			
			//if check, withdrawn, debit card
			if (Integer.parseInt(transaction_type_id) == 2 || Integer.parseInt(transaction_type_id) == 3 || Integer.parseInt(transaction_type_id) == 4)
			{
				ArrayList<Account> accounts = processOverDraft(account,Double.parseDouble(amount));
				
				for(Account acct : accounts)
				{
					all_in_sql.updateBalance(acct);
				}
			}
			//if deposit
			else
			{
				System.out.println("Deposit complete!");
				account.setStarting_balance(account.getStarting_balance() + Double.parseDouble(amount));
				
				all_in_sql.updateBalance(account);
			}
		
					
			//Process transaction.
			


		}
	}
	
	public static void transferMoney(Scanner key)
	{
		AccountDBHelper all_in_sql = new AccountDBHelper();
		System.out.println("Transfer Money  \n\n");
		String account_number1 = " ";
		String account_number2 = " ";
		//From
		System.out.println("Enter the account number that you want to transfer from: ");
		
		account_number1 = key.next();
		key.nextLine();
		
		Account account1 = new Account ();
		Account account2 = new Account ();
		boolean hasAccount = false;
		
		while(!hasAccount)
		{

			account1 = all_in_sql.getAccountFromNumber(account_number1);
			
			if(account1.getAccount_number() != null)
			{
				hasAccount = true;
			}
			else
			{
				System.out.println("Account not found! Please try again");
				System.out.println("Enter the account number that you want to transfere from: ");
				account_number1 = key.next();
				key.nextLine();
			}
		}
		
		String amount = " ";
		System.out.println("Enter the ammount: ");
		
		amount =  key.next();
		key.nextLine();
		boolean validType = false;
		while(!validType)
		{

			validType = Validator.validateDoubleWithRange(amount, 0, account1.getStarting_balance());
			if (!validType){
				System.out.println("Invalid amount \n");
				System.out.println("Enter the ammount: ");
				
				amount =  key.next();
				key.nextLine();
			}
			else
			{
				validType = true;
			}

		}
		
		
		//TO
		////////////////////
		System.out.println("Enter the account number that you want to transfer to: ");
		
		account_number2 = key.next();
		key.nextLine();
		
		
		hasAccount = false;
		
		while(!hasAccount)
		{

			account2 = all_in_sql.getAccountFromNumber(account_number2);
			
			if(account2.getAccount_number() != null)
			{
				hasAccount = true;
			}
			else
			{
				System.out.println("Account not found! Please try again");
				System.out.println("Enter the account number that you want to transfere to: ");
				account_number2 = key.next();
				key.nextLine();
			}
		}
		
		//Transfer process
		
		//parse amount from string to double
		double amountNumber = Double.parseDouble(amount);
		
		//if the accounts has the same customer
		if(account1.getCustomer_id() == account2.getCustomer_id())
		{
			//if overdraft, charge $35
			if(account1.getStarting_balance() < amountNumber)
			{
				System.out.println("Account number has not enough money, charged $35 dollars fee");
				account1.setStarting_balance(account1.getStarting_balance() - Double.parseDouble(amount) - 35);
				account2.setStarting_balance(account2.getStarting_balance() + Double.parseDouble(amount ));
			}
			
			//if not overdraft, make transfer
			else
			{
				System.out.println("Money transfered. Thank you!");
				account1.setStarting_balance(account1.getStarting_balance() - Double.parseDouble(amount));
				account2.setStarting_balance(account2.getStarting_balance() + Double.parseDouble(amount ));
			}
			
			//Update accounts after transfer money.
			all_in_sql.updateBalance(account1);
			all_in_sql.updateBalance(account2);
			
		}
		
		//2 different customers
		else
		{
			//call process overdraft method
			ArrayList<Account> fromAccounts = processOverDraft(account1, amountNumber);
			
			//update toAccount
			account2.setStarting_balance(account2.getStarting_balance() + Double.parseDouble(amount ));
			
			//update accounts
			all_in_sql.updateBalance(account2);
			for(Account tempAcct : fromAccounts)
			{
				all_in_sql.updateBalance(tempAcct);
			}
		}

	

		
		
	}
	
	public static ArrayList<Account> processOverDraft(Account account, double amount)
	{
		AccountDBHelper db = new AccountDBHelper();
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		// if account type = saving
		if (account.getAccount_type_id() == 2)
		{
			//and amount > account balance (overdraft)
			if (amount > account.getStarting_balance())
			{
				//if it's a saving, and overdraft, charge $35 dollars
				account.setStarting_balance(account.getStarting_balance() - amount - 35);
			}
			
			//else if have enough fund, charge amount 
			else
			{
				account.setStarting_balance(account.getStarting_balance() - amount);
			}
			
			accounts.add(account);
		}
			
		
		//else if account type = checking
		else if (account.getAccount_type_id() == 1)
		{
			//if overdraft
			if (amount > account.getStarting_balance())
			{
				//has saving
				if(db.hasSaving(account.getCustomer_id()))
				{
					Account savingAccount = db.getSavingAccount(account.getCustomer_id());
					
					//saving the left over from checking account
					double amountLeft = account.getStarting_balance();
					
					//set starting balance to 0
					account.setStarting_balance(0);
					
					//does saving have enough money if yes charge $15 for transfer fee, if not charge $15 transfer fee and $ 35 overdraft
					if(savingAccount.getStarting_balance()>(amount - amountLeft))
					{
						System.out.println("Not enough fund in checking account, getting money from saving and charged $15 transfer fee");
						savingAccount.setStarting_balance(savingAccount.getStarting_balance() + amountLeft - amount - 15);
					}
					else
					{
						System.out.println("Not enough fund in checking account, getting money from saving and charged $15 transfer fee");
						System.out.println("Not enough fund in saving account, charged $35");
						savingAccount.setStarting_balance(savingAccount.getStarting_balance() + amountLeft - amount - 15 - 35);
					}
					
					// add 2 accounts (after update balance) to the arraylist
					accounts.add(account);
					accounts.add(savingAccount);
					
				}
				//dont have saving, charge $35 overdraft
				else
				{
					account.setStarting_balance(account.getStarting_balance() - amount - 35);
					
					accounts.add(account);
				}
			}
			
			// if fund is enough, update balance
			else
			{
				account.setStarting_balance(account.getStarting_balance() - amount);
				accounts.add(account);
			}
		}
		return accounts;
	}
}
