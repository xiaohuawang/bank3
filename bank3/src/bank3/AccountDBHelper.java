package bank3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountDBHelper
{
	private static final String ACCOUNT_TABLE = "Evil_Account";
	private static final String ID = "ID";
	private static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	private static final String NAME = "NAME";
	private static final String STARTING_BALANCE = "STARTING_BALANCE";
	private static final String BIRTH_DATE = "BIRTH_DATE";
	
	private static final String TRANSACTION_TABLE = "Evil_Transactions";

	private static final String TRANSACTION_TYPE_ID = "TRANSACTION_TYPE_ID";
	private static final String TRANSACTION_DATE = "TRANSACTION_DATE";
	private static final String AMOUNT = "AMOUNT";
	private static final String CUSTOMER_ID = "CUSTOMER_ID";
	
	private static final String ACCOUNT_TYPE = "EVIL_ACCOUNT_TYPE";
	private static final String ACCOUNT_TYPE_NAME = "TYPE_NAME";
	
	public void updateBalance(Account account)
	{
		String sql = "UPDATE " + ACCOUNT_TABLE + " SET " + STARTING_BALANCE + " = " + account.getStarting_balance()
				+ " WHERE " + ACCOUNT_NUMBER + "= '"+ account.getAccount_number() + "'" ;
		System.out.println(sql);
		try
		{
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteAccount(String account_number)
	{
		String sql = "DELETE FROM " + ACCOUNT_TABLE + " WHERE account_number = '" + account_number +"'";
		try
		{
			Statement stmt = getConnection().createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public  ArrayList<Account> getAllAccounts()
	{
		ArrayList<Account> accounts = new ArrayList<Account>();

        String sql = "select * from " + ACCOUNT_TABLE;

        //creating PreparedStatement object to execute query
        ResultSet result = selectSQL(sql);
        try
        {
        	 while(result.next())
             {
             	Account account = new Account();
             	account.setAccount_number(result.getString("account_number"));
             	account.setCustomer_id(result.getInt("customer_id"));
             	account.setStarting_balance(result.getDouble("starting_balance"));
       	
             	//TODO add fields for accounts
             	accounts.add(account);
             }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
       
		
		return accounts;
	}
	
	public void addTransaction(Transaction transaction)
	{
		//insert transaction
		String insertTransaction = "INSERT INTO " + TRANSACTION_TABLE + 
				"( " +
				" " + ACCOUNT_NUMBER + ", " +
				" " + TRANSACTION_TYPE_ID + ", " +
				" " + TRANSACTION_DATE + ", " +
				" " + AMOUNT +
				" ) VALUES  " +
				"(?,?,?,?)";
		System.out.println(insertTransaction);
		
		try
		{
			java.util.Date utilDate = transaction.getTransaction_date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    
			PreparedStatement prepareStatement = getConnection().prepareStatement(insertTransaction);
			
			prepareStatement.setString(1, transaction.getAccount_number());
			prepareStatement.setInt(2, transaction.getTransaction_type_id());
			prepareStatement.setDate(3, sqlDate);
			prepareStatement.setDouble(4,transaction.getAmount());
			
			prepareStatement.executeUpdate();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//update balance
		Account account = getAccountFromNumber(transaction.getAccount_number());
		
		//TODO update balance
		
			
	}
	
	
	public Account getAccountFromNumber(String accountNumber)
	{
		Account account = new Account();
		String sql = "SELECT * FROM " + ACCOUNT_TABLE + " WHERE account_number = '" + accountNumber + "'";
//		String sql = "SELECT * FROM " + ACCOUNT_TABLE;
		System.out.println(sql);
		ResultSet result = selectSQL(sql);
		try
		{
			while(result.next())
			{
             	account.setAccount_number(result.getString("account_number"));
             	account.setCustomer_id(result.getInt("customer_id"));
             	account.setStarting_balance(result.getDouble("starting_balance"));
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return account;
	}
	
	private static final String CUSTOMER_TABLE = "Evil_Customer";
	private static final String FIRST_NAME = "First_Name";
	private static final String LAST_NAME = "Last_Name";
	private static final String PHONE_NUMBER = "Phone_Number";
	
	public void insertCustomer(Customer customer)
	{
		String insertCustomer = "INSERT INTO " + CUSTOMER_TABLE + 
				"( "+
				" " + FIRST_NAME + ", " +
				" " + LAST_NAME + ", " +
				" " + PHONE_NUMBER +
				" ) VALUES  " +
				"(?,?,?)";
		System.out.println(insertCustomer);
		try
		{
			PreparedStatement prepareStatement = getConnection().prepareStatement(insertCustomer);
			prepareStatement.setString(1, customer.getFirst_name());
			prepareStatement.setString(2, customer.getLast_name());
			prepareStatement.setString(3, customer.getPhone_number());

			prepareStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  Customer findCustomerByPhone(String phone_number)
	{
		Customer customer = new Customer();
		String sql = "SELECT * FROM " + CUSTOMER_TABLE + " WHERE phone_number = '" + phone_number + "'";

		System.out.println(sql);
		ResultSet result = selectSQL(sql);
		try
		{
			while(result.next())
			{
				customer.setId(result.getInt("ID"));
				customer.setFirst_name(result.getString(FIRST_NAME));
				customer.setLast_name(result.getString(LAST_NAME));
				customer.setPhone_number(result.getString(PHONE_NUMBER));
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return customer;
	}
	public void insertAccount(Account account)
	{
		String insertAccount = "INSERT INTO " + ACCOUNT_TABLE + 
				"( "+
				" " + ACCOUNT_NUMBER + ", " +
				" " + CUSTOMER_ID + ", " +
				" " + STARTING_BALANCE  +
	
				" ) VALUES  " +
				"(?,?,?)";
		System.out.println(insertAccount);
		try
		{
			
			PreparedStatement prepareStatement = getConnection().prepareStatement(insertAccount);
			prepareStatement.setString(1, account.getAccount_number());
			prepareStatement.setInt(2, account.getCustomer_id());
			prepareStatement.setDouble(3, account.getStarting_balance());

			
			prepareStatement.executeUpdate();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean availablePhone(String phoneNumber)
	{
		String sql = "SELECT COUNT(*) FROM EVIL_CUSTOMER WHERE PHONE_NUMBER = '" + phoneNumber + "'"; 
		ResultSet rs = selectSQL(sql);
		int count = 0;
		try
		{
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count > 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean availableAccountNumber(String accountNumber)
	{
		String sql = "SELECT COUNT(*) FROM EVIL_ACCOUNT WHERE ACCOUNT_NUMBER = '" + accountNumber + "'"; 
		ResultSet rs = selectSQL(sql);
		int count = 0;
		try
		{
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (count > 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			conn =  DBConnection.getConnection();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public ResultSet selectSQL(String sql)
	{
		Connection conn = null;
		ResultSet result = null;
		try
		{
			conn =  DBConnection.getConnection();
			PreparedStatement preStatement = conn.prepareStatement(sql);
			result = preStatement.executeQuery();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//
	
	public void insertAccount_type(AccountType type)
	{
		String insertAccountType = "INSERT INTO " + ACCOUNT_TYPE + 
				"( "+
				" " + ID + ", " +
				" " + ACCOUNT_TYPE_NAME +
				" ) VALUES  " +
				"(?,?)";
		System.out.println(insertAccountType);
		try
		{
			
			PreparedStatement prepareStatement = getConnection().prepareStatement(insertAccountType);
			prepareStatement.setInt(1, type.getId());
			prepareStatement.setString(2, type.getName());
			prepareStatement.executeUpdate();
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public HashMap< String, Integer> getAcctType(){
		String sql = "SELECT * FROM " + ACCOUNT_TYPE ;
		
		HashMap< String, Integer> account_type = new HashMap< String, Integer>();
		
		
		try
		{
			ResultSet result = selectSQL(sql);
			while(result.next())
			{
				account_type.put(result.getString("TYPE_NAME"),result.getInt("ID"));
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return account_type;
		
	}
	
	
	
	public boolean hasChecking(int customer_id){
		String sql = "SELECT COUNT(*) FROM EVIL_ACCOUNT WHERE CUSTOMER_ID = " + customer_id + " AND ACCOUNT_TYPE_ID = 1" ; 
		ResultSet result = selectSQL(sql);
		int count = 0;
		boolean hasChecking;
		try
		{
			result.next();
			count = result.getInt(1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hasChecking = (count > 0) ? true:false;
		return hasChecking;
	}
	
	public boolean hasSaving(int customer_id){
		String sql = "SELECT COUNT(*) FROM EVIL_ACCOUNT WHERE CUSTOMER_ID = " + customer_id + " AND ACCOUNT_TYPE_ID = 2" ; 
		ResultSet result = selectSQL(sql);
		int count = 0;
		boolean hasSaving;
		try
		{
			result.next();
			count = result.getInt(1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hasSaving = (count > 0) ? true:false;
		return hasSaving;
	}
	
	
	public Account getSavingAccount(int customer_id){
		String sql = "SELECT * FROM EVIL_ACCOUNT WHERE CUSTOMER_ID = " + customer_id + " AND ACCOUNT_TYPE_ID = 2" ; 
		ResultSet result = selectSQL(sql);
		Account account = new Account();
		
		System.out.println(sql);
		try
		{
			while(result.next())
			{
				account.setAccount_type_id(result.getInt(ID));
				account.setCustomer_id(result.getInt(CUSTOMER_ID));
				account.setStarting_balance(result.getDouble(STARTING_BALANCE));
				account.setAccount_number(result.getString(ACCOUNT_NUMBER));
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return account;
	}
}

