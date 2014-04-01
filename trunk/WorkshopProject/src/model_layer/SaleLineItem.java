package model_layer;

public class SaleLineItem extends LineItem
{
	private double total_price;
	public SaleLineItem()
	{
		// TODO Auto-generated constructor stub
	}
	public double getTotal_price()
	{
		return total_price;
	}
	public void setTotal_price(float total_price)
	{
		this.total_price = total_price;
	}
	
	public void calculate_price()
	{
		total_price = getProduct().getPrice() * getAmount();
	}
	

}
