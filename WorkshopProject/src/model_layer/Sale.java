package model_layer;

public class Sale
{
	private String id;
	private float price;
	private Customer customer;
	private Delivery delivery;
	private String invoice_nr;
	private String payment_date;


	public Sale()
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


	public float getPrice()
	{
		return price;
	}


	public void setPrice(float price)
	{
		this.price = price;
	}


	public Customer getCustomer()
	{
		return customer;
	}


	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}


	public Delivery getDelivery()
	{
		return delivery;
	}


	public void setDelivery(Delivery delivery)
	{
		this.delivery = delivery;
	}


	public String getInvoice_nr()
	{
		return invoice_nr;
	}


	public void setInvoice_nr(String invoice_nr)
	{
		this.invoice_nr = invoice_nr;
	}


	public String getPayment_date()
	{
		return payment_date;
	}


	public void setPayment_date(String payment_date)
	{
		this.payment_date = payment_date;
	}

}
