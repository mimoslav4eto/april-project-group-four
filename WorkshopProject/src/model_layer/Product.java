package model_layer;

import java.util.ArrayList;

public class Product
{
	private int id;
	private String name;
	private float retail_price;
	private Float price;
	private Float rent_price;
	private int min_amount;
	private int amount;
	private ArrayList<Supplier> supplied_by;
	
	

	public Product()
	{
		// TODO Auto-generated constructor stub
	}
	
	


	public Product(String name, float retail_price, Float price, Float rent_price, int min_amount, int amount)
	{
		this.name = name;
		this.retail_price = retail_price;
		this.price = price;
		this.rent_price = rent_price;
		this.min_amount = min_amount;
		this.amount = amount;
	}




	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public Float getPrice()
	{
		return price;
	}

	public void setPrice(Float price)
	{
		this.price = price;
	}

	public Float getRent_price()
	{
		return rent_price;
	}

	public void setRent_price(Float rent_price)
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
