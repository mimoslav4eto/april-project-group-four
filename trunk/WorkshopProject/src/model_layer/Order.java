package model_layer;
import java.util.Date;
import java.util.ArrayList;
public class Order
{
	private int order_id;
	private Customer customer;
	private Delivery delivery;
	private float total_price;
	private Date payment_date;
	private int invoice_nr;
	private ArrayList<SaleLineItem> items;
	private boolean complete;

	public Order()
	{
		items = new ArrayList<SaleLineItem>();
	}
	
	

	public Order(Customer customer, Date payment_date,
			int invoice_nr, ArrayList<SaleLineItem> items, boolean complete)
	{
		this.customer = customer;
		delivery = null;
		this.payment_date = payment_date;
		this.invoice_nr = invoice_nr;
		this.items = items;
		this.complete = complete;
		calculate_price(customer.getCust_type().getDisc_perc(), customer.getCust_type().getPrice_qual_for_disc());
		
	}



	public boolean isComplete()
	{
		return complete;
	}



	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}



	public int getOrder_id()
	{
		return order_id;
	}

	public void setOrder_id(int order_id)
	{
		this.order_id = order_id;
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
		calculate_price(customer.getCust_type().getDisc_perc(), customer.getCust_type().getPrice_qual_for_disc());
	}

	public float getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(float total_price)
	{
		this.total_price = total_price;
	}

	public Date getPayment_date()
	{
		return payment_date;
	}

	public void setPayment_date(Date payment_date)
	{
		this.payment_date = payment_date;
	}

	public int getInvoice_nr()
	{
		return invoice_nr;
	}

	public void setInvoice_nr(int invoice_nr)
	{
		this.invoice_nr = invoice_nr;
	}

	public ArrayList<SaleLineItem> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<SaleLineItem> items)
	{
		this.items = items;
	}
	
	public void add_item(SaleLineItem item)
	{
		items.add(item);
	}
	
	private void calculate_price(float disc_perc, float price_qual_for_disc)
	{
		total_price = 0;
		for (SaleLineItem item : items)
		{
			total_price += item.getTotal_price();
		}
		if(total_price > price_qual_for_disc && disc_perc != -1)
		{
			total_price *= disc_perc;
		}
		
		if(delivery != null)
		{
			total_price += delivery.getCost();
		}
		
	}
	


}
