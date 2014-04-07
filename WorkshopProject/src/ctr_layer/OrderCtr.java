package ctr_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import model_layer.Customer;
import model_layer.CustomerType;
import model_layer.Delivery;
import model_layer.Order;
import model_layer.Product;
import model_layer.RentLineItem;
import model_layer.SaleLineItem;
import db_layer.DBCustomer;
import db_layer.DBOrder;
import db_layer.DBProduct;

public class OrderCtr
{
	private DBOrder db;
	private DBProduct db_p;
	private static Order ord;
	private DBCustomer db_c;
	private static Customer cust;
	private SupplyLineCtr prod_ctr;
	public OrderCtr()
	{
		db = new DBOrder();
		db_p = new DBProduct();
		db_c = new DBCustomer();
		prod_ctr = new SupplyLineCtr();
		if(cust == null)
		{
			cust = new Customer();
		}
		if(ord == null)
		{
			ord = new Order();
		}
	}
	
	public Object[] get_order(int id)
	{
		return make_order_array(find_order(id, false));
	}
	
	public Object[][] get_all_orders()
	{
		return make_orders_array(find_all_orders(false));
	}
	
	public Object[][] get_complete_orders()
	{
		return make_orders_array(find_complete_orders(false));
	}
	
	public Object[][] get_incomplete_orders()
	{
		return make_orders_array(find_incomplete_orders(false));
	}
	
	public Object[][] get_order_items(int order_id)
	{
		return make_items_array(find_order(order_id, true));
	}
	
	public boolean order_exists(int id)
	{
		return find_order(id, false) != null;
	}
	
	
	
	public boolean add_order_with_del(int customer_id, boolean pay_on_delivery, String delivery_date, String payment_date,
			 LinkedList<int[]> ids_amounts, boolean complete)
	{
		Date p_d = Utilities.convert_string_to_date(payment_date);
		Date d_d = Utilities.convert_string_to_date(delivery_date);
		Delivery del = new Delivery(true, d_d, pay_on_delivery);
		ArrayList<SaleLineItem> items = new ArrayList<SaleLineItem>();
		SaleLineItem item;
		Product prod;
		Customer cust = db_c.find_customer(customer_id);

		for(int[] data : ids_amounts)
		{
			int amount = data[1];
			prod = db_p.find_product(data[0], false);
			prod_ctr.subtract_amount(prod.getId(), amount);
			item = new SaleLineItem(prod, amount);
			items.add(item);
			
		}
		ord = new Order(cust, p_d, items, complete);
		float price_qual_for_free_shipment = cust.getCust_type().getPrice_qual_for_free_shipment();
		if (ord.getTotal_price() >  price_qual_for_free_shipment && price_qual_for_free_shipment != -1)
		{
			del.setCost(0);
		}
		ord.setDelivery(del);
		int rc = db.insert_order(ord);
		return  rc == 1;
	}
	
	public boolean add_order_without_del(int customer_id,  String payment_date, LinkedList<int[]> ids_amounts, boolean complete)
	{
		Date p_d = Utilities.convert_string_to_date(payment_date);
		ArrayList<SaleLineItem> items = new ArrayList<SaleLineItem>();
		SaleLineItem item;
		Product prod;
		Customer cust = db_c.find_customer(customer_id);
		for(int[] data : ids_amounts)
		{
			int amount = data[1];
			prod = db_p.find_product(data[0], false);
			prod_ctr.subtract_amount(prod.getId(), amount);
			item = new SaleLineItem(prod, data[1]);
			items.add(item);
			
		}
		ord = new Order(cust, p_d, items, complete);
		return db.insert_order(ord) == 1;
	}
	
	public float[] calc_order_price(int cust_id, LinkedList<int[]> ids_amounts, boolean pay_on_delivery, boolean delivery)
	{
		float[] prices = new float[2];
		float tot_price = 0;
		float del_cost = 0; 
		if(cust.getId()!= cust_id)
		{
			cust=db_c.find_customer(cust_id);
		}
		CustomerType ct = cust.getCust_type();
		for(int[] data : ids_amounts)
		{
			tot_price += prod_ctr.calculate_ord_cost(data[0], data[1]);
		}
		float price_qual_for_free = ct.getPrice_qual_for_free_shipment();
		float price_qual = ct.getPrice_qual_for_disc();
		float disc_perc = ct.getDisc_perc();
		if(delivery)
		{
			if(price_qual_for_free!= -1 && tot_price > price_qual_for_free)
			{
				del_cost = 0;
			}
			else
			{
				del_cost = new Delivery(pay_on_delivery).getCost(); 
			}
			
		}
		if(price_qual != -1)
		{
			tot_price *=  (1- disc_perc);
		}
		tot_price += del_cost;
		prices[0] = tot_price;
		prices[1] = del_cost;
		return prices;

	}
	

	
	
	
	public boolean update_delivery_status(int order_id)
	{
		ord = find_order(order_id, false);
		Delivery del = ord.getDelivery();
		del.setIn_progress(false);
		ord.setDelivery(del);
		return db.update_order(ord) == 1;
	}
	
	public boolean finish_order(int order_id)
	{
		ord = find_order(order_id, false);
		ord.setComplete(true);
		Delivery del = ord.getDelivery();
		if (del != null && del.isIn_progress())
		{
			del.setIn_progress(false);
			ord.setDelivery(del);
		}
		return db.update_order(ord) == 1;
	}
	
	private Order find_order(int id, boolean make_association)
	{
		if(ord.getOrder_id() == id && !(ord.getItems().isEmpty() && make_association))
		{
			return ord;
		}
		Order t_ord = db.find_order(id, make_association);
		if(t_ord != null)
		{
			ord = t_ord;
		}
		return t_ord;
	}
	
	private ArrayList<Order> find_all_orders(boolean make_association)
	{
		return db.get_all_orders(make_association);
	}
	
	private ArrayList<Order> find_complete_orders(boolean make_association)
	{
		return db.find_specific_orders("complete", "", 1, make_association);
	}
	
	private ArrayList<Order> find_incomplete_orders(boolean make_association)
	{
		return db.find_specific_orders("complete", "", 0, make_association);
	}
	
	private Object[] make_order_array(Order order)
	{
		int id = order.getOrder_id();
		Customer cust = order.getCustomer();
		int cust_id = cust.getId();
		String cust_name = cust.getName();
		int invoice_nr = order.getInvoice_nr();
		String payment_date = Utilities.convert_date_to_string(order.getPayment_date());
		float total_price = order.getTotal_price();
		Delivery del = order.getDelivery();
		boolean complete = order.isComplete();
		
		if(del == null)
		{
			Object[] data = { id, payment_date, total_price, invoice_nr,  cust_id, cust_name, null, null, null, null, complete };
			System.out.println(data[1]);
			
			return data;
		}
		else
		{
			float delivery_cost = del.getCost();
			String delivery_date = Utilities.convert_date_to_string(del.getDate());
			boolean in_progress = del.isIn_progress();
			boolean pay_on_delivery = del.isPay_on_delivery();
			Object[] data = { id, payment_date, total_price, invoice_nr,  cust_id, cust_name, in_progress, pay_on_delivery, delivery_date,  delivery_cost, complete };
			System.out.println(data[1]);
			return data;
		}
		
			
	}
	
	private Object[][] make_orders_array(ArrayList<Order> orders)
	{
		Object[][] data = new Object[orders.size()][10];
		int i = 0;
		for (Order order : orders)
		{
			
			data[i] = make_order_array(order);
			i++;
		}
		return data;
	}
	
	private Object[] make_item_array(SaleLineItem item)
	{
		Product prod = item.getProduct();
		int amount = item.getAmount();
		int product_id = prod.getId();
		String name = prod.getName();
		float retail_price = prod.getRetail_price();
		Float price = prod.getPrice();
		float total_price = item.getTotal_price();
		Object[] data = { product_id, name, retail_price, price, amount, total_price };
		return data;
	}
	
	private Object[][] make_items_array(Order order)
	{
		ArrayList<SaleLineItem> items = order.getItems();
		Object[][] data = new Object[items.size()][6];
		int i = 0;
		for(SaleLineItem item : items)
		{
			data[i] = make_item_array(item);
			i++;
		}
		return data;
	}
	
	
	
	

}
