package model_layer;

import java.util.ArrayList;

public class Rent extends Sale
{
	private String date;
	private String return_date;
	private ArrayList<RentLineItem> items;

	public Rent()
	{
		items = new ArrayList<RentLineItem>();
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getReturn_date()
	{
		return return_date;
	}

	public void setReturn_date(String return_date)
	{
		this.return_date = return_date;
	}
	
	public void add_item(RentLineItem rli)
	{
		items.add(rli);
	}

	public ArrayList<RentLineItem> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<RentLineItem> items)
	{
		this.items = items;
	}



}
