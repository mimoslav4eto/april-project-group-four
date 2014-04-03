package model_layer;

public class LineItem
{
	private int id;
	private Product product;
	private int amount;
	public LineItem()
	{
		// TODO Auto-generated constructor stub
	}
	
	public LineItem(Product product, int amount)
	{
		this.product = product;
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
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}

}
