package model_layer;

public class RentLineItem extends LineItem
{
	private double daily_price;
	public RentLineItem()
	{
		// TODO Auto-generated constructor stub
	}
	public double getDaily_price()
	{
		return daily_price;
	}
	public void setDaily_price(double daily_price)
	{
		this.daily_price = daily_price;
	}
	
	public void calculate_daily_price()
	{
		daily_price = getProduct().getRent_price() * getAmount();
	}

}
