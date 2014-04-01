package model_layer;
import java.util.ArrayList;
public class Order
{
	private int order_id;
	private Customer customer;
	private Delivery delivery;
	private float total_price;
	private String payment_date;
	private int invoice_nr;
	private ArrayList<SaleLineItem> items;

	public Order()
	{
		items = new ArrayList<SaleLineItem>();
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
	}

	public float getTotal_price()
	{
		return total_price;
	}

	public void setTotal_price(float total_price)
	{
		this.total_price = total_price;
	}

	public String getPayment_date()
	{
		return payment_date;
	}

	public void setPayment_date(String payment_date)
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
	


}
