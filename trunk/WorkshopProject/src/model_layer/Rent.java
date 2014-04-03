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
	private ArrayList<RentLineItem> items;

	public Rent()
	{
		items = new ArrayList<RentLineItem>();
	}

	public Rent(Customer customer, Date date, Date return_date, ArrayList<RentLineItem> items)
	{
		super();
		this.customer = customer;
		this.date = date;
		this.return_date = return_date;
		this.items = items;
		
		rent_price = calculate_price(date, return_date);
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
	
	private float calculate_price(Date begin, Date end)
	{
		float price = 0;
		long day_difference = count_days(begin, end);
		for (RentLineItem item : items)
		{
			price += item.getDaily_price();
		}
		return price * day_difference;
	}
	
	private long count_days(Date begin, Date end)
	{
		long milisecond_difference = end.getTime() - begin.getTime();
		return TimeUnit.MILLISECONDS.toDays(milisecond_difference);
	}

}
