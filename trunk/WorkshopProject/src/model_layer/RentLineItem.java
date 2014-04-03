package model_layer;

public class RentLineItem extends LineItem
{
	private float daily_price;
	public RentLineItem()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public RentLineItem(Product product, int amount)
	{
		super(product, amount);
		daily_price = calculate_daily_price();
	}


	public float getDaily_price()
	{
		return daily_price;
	}
	public void setDaily_price(float daily_price)
	{
		this.daily_price = daily_price;
	}
	
	public float calculate_daily_price()
	{
		return getProduct().getRent_price() * getAmount();
	}

}
