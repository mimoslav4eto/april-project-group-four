package model_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Rent
{
	private int rent_id;
	private Customer customer;
	private Delivery delivery;
	private float rent_price;
	private Date date;
	private Date return_date;
	private boolean complete;
	private ArrayList<RentLineItem> items;

	public Rent()
	{
		items = new ArrayList<RentLineItem>();
	}

	public Rent(Customer customer, Date date, Date return_date, ArrayList<RentLineItem> items, boolean complete)
	{
		super();
		this.customer = customer;
		this.date = date;
		this.return_date = return_date;
		this.items = items;
		this.complete = complete;
		delivery = null;
		calculate_price(date, return_date);
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}

	public int getRent_id()
	{
		return rent_id;
	}



	public void setRent_id(int rent_id)
	{
		this.rent_id = rent_id;
	}



	public Customer getCustomer()
	{
		return customer;
	}



	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}



	public Delivery getDelivery()
	{
		return delivery;
	}



	public void setDelivery(Delivery delivery)
	{
		this.delivery = delivery;
		calculate_price(date, return_date);
	}



	public float getRent_price()
	{
		return rent_price;
	}



	public void setRent_price(float rent_price)
	{
		this.rent_price = rent_price;
	}



	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Date getReturn_date()
	{
		return return_date;
	}

	public void setReturn_date(Date return_date)
	{
		this.return_date = return_date;
	}

	public ArrayList<RentLineItem> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<RentLineItem> items)
	{
		this.items = items;
	}

	public void add_item(RentLineItem rli)
	{
		items.add(rli);
	}
	
	public void calculate_price(Date begin, Date end)
	{
		if(end.getTime()!= return_date.getTime())
		{
			return_date = end;
			if(begin.getTime() <= end.getTime())
			{
				date = end;
				rent_price = 0;
			}
		}
		rent_price = 0;
		long day_difference = count_days(begin, end);
		for (RentLineItem item : items)
		{
			rent_price += item.getDaily_price();
		}
		rent_price *= day_difference;
	}
	
	private long count_days(Date begin, Date end)
	{
		long milisecond_difference = end.getTime() - begin.getTime();
		return TimeUnit.MILLISECONDS.toDays(milisecond_difference);
	}

}
