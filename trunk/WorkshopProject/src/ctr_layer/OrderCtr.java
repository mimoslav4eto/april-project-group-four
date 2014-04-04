package ctr_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import model_layer.Customer;
import model_layer.Delivery;
import model_layer.Order;
import model_layer.Product;
import model_layer.SaleLineItem;
import db_layer.DBCustomer;
import db_layer.DBOrder;
import db_layer.DBProduct;

public class OrderCtr
{
	private DBOrder db;
	private DBProduct db_p;
	private Order ord;
	private DBCustomer db_c;
	public OrderCtr()
	{
		db = new DBOrder();
		db_p = new DBProduct();
		db_c = new DBCustomer();
	}
	
	public boolean add_order_with_del(int customer_id, boolean pay_on_delivery, Date delivery_date, Date payment_date,
			int invoice_nr, LinkedList<int[]> ids_amounts)
	{
		
		Delivery del = new Delivery(true, delivery_date, pay_on_delivery);
		ArrayList<SaleLineItem> items = new ArrayList<SaleLineItem>();
		SaleLineItem item;
		Product prod;
		Customer cust = db_c.find_customer(customer_id);

		for(int[] data : ids_amounts)
		{
			prod = db_p.find_product(data[0], false);
			item = new SaleLineItem(prod, data[1]);
			items.add(item);
			
		}
		ord = new Order(cust, payment_date, invoice_nr, items);
		float price_qual_for_free_shipment = cust.getCust_type().getPrice_qual_for_free_shipment();
		if (ord.getTotal_price() >  price_qual_for_free_shipment && price_qual_for_free_shipment != -1)
		{
			del.setCost(0);
		}
		ord.setDelivery(del);
		int rc = db.insert_order(ord);
		return  rc == 1;
	}
	
	public boolean add_order_without_del(int customer_id,  Date payment_date, int invoice_nr, LinkedList<int[]> ids_amounts)
	{
		ArrayList<SaleLineItem> items = new ArrayList<SaleLineItem>();
		SaleLineItem item;
		Product prod;
		Customer cust = db_c.find_customer(customer_id);
		for(int[] data : ids_amounts)
		{
			prod = db_p.find_product(data[0], false);
			item = new SaleLineItem(prod, data[1]);
			items.add(item);
			
		}
		ord = new Order(cust, payment_date, invoice_nr, items);
		return db.insert_order(ord) == 1;
	}
	
	public boolean update_delivery_status(int order_id)
	{
		Order ord = find_order(order_id, true);
		Delivery del = ord.getDelivery();
		del.setIn_progress(false);
		ord.setDelivery(del);
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
	
	public boolean order_exists(int id)
	{
		return find_order(id, false) != null;
	}

}
