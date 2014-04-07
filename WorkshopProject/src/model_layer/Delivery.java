package model_layer;

import java.util.Date;

public class Delivery
{
	private int id;
	private boolean in_progress;
	private Date date;
	private float cost;
	private boolean pay_on_delivery;

	public Delivery()
	{
		// TODO Auto-generated constructor stub
	}
	
	

	public Delivery(boolean in_progress, Date date, boolean pay_on_delivery)
	{
		this.in_progress = in_progress;
		this.date = date;
		this.pay_on_delivery = pay_on_delivery;

		cost = calculate_cost();
	
	}



	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public boolean isIn_progress()
	{
		return in_progress;
	}

	public void setIn_progress(boolean in_progress)
	{
		this.in_progress = in_progress;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
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
	
	private float calculate_cost()
	{
		float c = 45f;
		if (pay_on_delivery)
		{
			c += 25f;
		}
		return c;
	}

	

	

}
