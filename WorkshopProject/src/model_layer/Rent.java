package model_layer;

import java.util.ArrayList;

public class Rent
{
	private int rent_id;
	private Customer customer;
	private Delivery delivery;
	private float rent_price;
	private String date;
	private String return_date;
	private ArrayList<RentLineItem> items;

	public Rent()
	{
		items = new ArrayList<RentLineItem>();
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





}
