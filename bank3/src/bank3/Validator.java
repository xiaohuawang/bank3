package bank3;

import java.text.SimpleDateFormat;

public class Validator
{
	public static boolean validateInt(String numberStr)
	{
		boolean isValid = false;
		isValid = numberStr.matches("^\\d+$");
		return isValid;
	}
	
	public static boolean validateIntWithRange(String numberStr, int min, int max)
	{
		boolean isValid = false;
		isValid = validateInt(numberStr);
		if (isValid)
		{
			int number = Integer.parseInt(numberStr);
			isValid = isValid && number >= min && number <= max;
		}
		return isValid;
	}
	
	public static boolean validateDouble(String numberStr)
	{
		boolean isValid = false;
		isValid = numberStr.matches("-?\\d+(\\.\\d+)?");
		return isValid;
	}
	
	public static boolean validateDoubleWithRange(String numberStr, double min, double max)
	{
		boolean isValid = false;
		isValid = validateDouble(numberStr);
		if(isValid)
		{
			double number = Double.parseDouble(numberStr);
			isValid = isValid && number >= min && number <= max;
		}
		return isValid;
	}
	
	public static boolean validateDateWithFormat(String dateStr)
	{
		boolean isValid = false;
		isValid = dateStr.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)");
		return isValid;
	}
	
	
}