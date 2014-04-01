package model_layer;

import java.util.ArrayList;

public class Product
{
	private String id;
	private String name;
	private float retail_price;
	private float price;
	private float rent_price;
	private int min_amount;
	private ArrayList<Supplier> supplied_by;
	
	

	public Product()
	{
		// TODO Auto-generated constructor stub
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public float getRetail_price()
	{
		return retail_price;
	}


	public void setRetail_price(float retail_price)
	{
		this.retail_price = retail_price;
	}


	public float getPrice()
	{
		return price;
	}


	public void setPrice(float price)
	{
		this.price = price;
	}


	public float getRent_price()
	{
		return rent_price;
	}


	public void setRent_price(float rent_price)
	{
		this.rent_price = rent_price;
	}


	public int getMin_amount()
	{
		return min_amount;
	}


	public void setMin_amount(int min_amount)
	{
		this.min_amount = min_amount;
	}


	public ArrayList<Supplier> getSupplied_by()
	{
		return supplied_by;
	}


	public void setSupplied_by(ArrayList<Supplier> supplied_by)
	{
		this.supplied_by = supplied_by;
	}
	
	public void add_supplier(Supplier supplier)
	{
		supplied_by.add(supplier);
	}

}
