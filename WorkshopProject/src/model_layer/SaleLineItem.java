package model_layer;

public class SaleLineItem extends LineItem
{
	private float total_price;
	public SaleLineItem()
	{

	}
	
	public SaleLineItem(Product product, int amount)
	{
		super(product, amount);
		total_price = calculate_price();
	}
	
	public float getTotal_price()
	{
		return total_price;
	}
	public void setTotal_price(float total_price)
	{
		this.total_price = total_price;
	}
	
	public float calculate_price()
	{
		return getProduct().getPrice() * getAmount();
	}
	

}
