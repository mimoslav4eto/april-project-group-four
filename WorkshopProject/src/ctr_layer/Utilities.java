package ctr_layer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities
{
	public Utilities()
	{
		// TODO Auto-generated constructor stub
	}
	

	
	public static Date convert_string_to_date(String s)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try
		{
			date = sdf.parse(s);
		}
		catch(ParseException e)
		{
			System.out.println("Error while parsing date: " + s);
		}
		return date;
	}
	
	public static String convert_date_to_string(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

}
