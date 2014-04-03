package ctr_layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import model_layer.Customer;
import model_layer.Delivery;
import model_layer.Rent;
import model_layer.Product;
import model_layer.RentLineItem;

import db_layer.DBRent;
import db_layer.DBProduct;

public class RentCtr
{
	private DBRent db;
	private DBProduct db_p;
	private Rent rent;
	public RentCtr()
	{
		db = new DBRent();
		db_p = new DBProduct();
	}
	
	public boolean add_rent_with_del(int customer_id, boolean pay_on_delivery, Date delivery_date, Date date,
			Date return_date, LinkedList<int[]> ids_amounts)
	{
		Delivery del = new Delivery(true, delivery_date, pay_on_delivery);
		ArrayList<RentLineItem> items = new ArrayList<RentLineItem>();
		RentLineItem item;
		Product prod;
		Customer cust = new Customer();
		cust.setId(customer_id);

		for(int[] data : ids_amounts)
		{
			prod = db_p.find_product(data[0], false);
			item = new RentLineItem(prod, data[1]);
			items.add(item);
			
		}
		rent = new Rent(cust, date, return_date, items);
		rent.setDelivery(del);
		int rc = db.insert_rent(rent);
		return  rc == 1;
	}
	
	public boolean add_rent_without_del(int customer_id,  Date date, Date return_date, LinkedList<int[]> ids_amounts)
	{
		ArrayList<RentLineItem> items = new ArrayList<RentLineItem>();
		RentLineItem item;
		Product prod;
		Customer cust = new Customer();
		cust.setId(customer_id);
		for(int[] data : ids_amounts)
		{
			prod = db_p.find_product(data[0], false);
			item = new RentLineItem(prod, data[1]);
			items.add(item);
			
		}
		rent = new Rent(cust, date, return_date, items);
		return db.insert_rent(rent) == 1;
	}
	
	private Rent find_rent(int id, boolean make_association)
	{
		if(rent.getRent_id() == id && !(rent.getItems().isEmpty() && make_association))
		{
			return rent;
		}
		Rent t_rent = db.find_rent(id, make_association);
		if(t_rent != null)
		{
			rent = t_rent;
		}
		return t_rent;
	}
	
	public boolean rent_exists(int id)
	{
		return find_rent(id, false) != null;
	}

}
