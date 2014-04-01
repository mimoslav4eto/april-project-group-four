package model_layer;

public class Delivery
{
	private int id;
	private String status;
	private String date;
	private float cost;
	private boolean pay_on_delivery;

	public Delivery()
	{
		// TODO Auto-generated constructor stub
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public float getCost()
	{
		return cost;
	}

	public void setCost(float cost)
	{
		this.cost = cost;
	}

	public boolean isPay_on_delivery()
	{
		return pay_on_delivery;
	}

	public void setPay_on_delivery(boolean pay_on_delivery)
	{
		this.pay_on_delivery = pay_on_delivery;
	}

}
