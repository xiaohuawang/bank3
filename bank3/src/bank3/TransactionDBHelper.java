package bank3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class TransactionDBHelper
{
	public  ArrayList<Transaction> getAllTransactions()
	{
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        String sql = "select * from transactions";

        //creating PreparedStatement object to execute query
        ResultSet result = executeSQL(sql);
        try
        {
        	 while(result.next())
             {
             	Transaction transaction = new Transaction();
             	
             	
             	
             	
             	//TODO add fields for transactions
             	transactions.add(transaction);
             }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
       
		
		return transactions;
	}
	
	
	public ResultSet executeSQL(String sql)
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
}