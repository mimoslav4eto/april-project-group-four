package model_layer;

public class CustomerType
{
	private int id;
	private float price_qual_for_disc;
	private float price_qual_for_free_shipment;
	private float disc_perc;

	public CustomerType()
	{
		// TODO Auto-generated constructor stub
	}
	

	public CustomerType(float price_qual_for_disc, float price_qual_for_free_shipment, float disc_perc)
	{
		this.price_qual_for_disc = price_qual_for_disc;
		this.price_qual_for_free_shipment = price_qual_for_free_shipment;
		this.disc_perc = disc_perc;
	}


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public float getPrice_qual_for_disc()
	{
		return price_qual_for_disc;
	}

	public void setPrice_qual_for_disc(float price_qual_for_disc)
	{
		this.price_qual_for_disc = price_qual_for_disc;
	}

	public float getPrice_qual_for_free_shipment()
	{
		return price_qual_for_free_shipment;
	}

	public void setPrice_qual_for_free_shipment(float price_qual_for_free_shipment)
	{
		this.price_qual_for_free_shipment = price_qual_for_free_shipment;
	}

	public float getDisc_perc()
	{
		return disc_perc;
	}

	public void setDisc_perc(float disc_perc)
	{
		this.disc_perc = disc_perc;
	}


	

}
