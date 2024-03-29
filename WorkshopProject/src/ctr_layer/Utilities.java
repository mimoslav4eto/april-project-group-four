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
	
	public static boolean valid_date(String s)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			return (sdf.parse(s).getTime() + 1000*60*60*24- new Date().getTime()) >= 0;
		}
		catch(ParseException e)
		{
			return false;
		}
		
	}
	
	public static String convert_date_to_string(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
	
	public static long day_difference(String begin, String end)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			Date begin_date = sdf.parse(begin);
			Date end_date = sdf.parse(end);
			long difference = (end_date.getTime() - begin_date.getTime()) / (1000*60*60*24);
			if(difference < 0)
			{
				return 0;
			}
			return difference;
		}
		catch(ParseException e)
		{
			return 0;
		}
	}

}
