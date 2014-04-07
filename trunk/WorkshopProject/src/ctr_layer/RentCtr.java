package ctr_layer;

import java.text.SimpleDateFormat;
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
	private static Rent rent;
	public RentCtr()
	{
		db = new DBRent();
		db_p = new DBProduct();

		if(rent == null)
		{
			rent = new Rent();
		}
	}
	
	public Object[] get_rent(int id)
	{
		return make_rent_array(find_rent(id, false));
	}
	
	public Object[][] get_all_rents()
	{
		return make_rents_array(find_all_rents(false));
	}
	
	public Object[][] get_complete_rents()
	{
		return make_rents_array(find_all_complete_rents(false));
	}
	public Object[][] get_incomplete_rents()
	{
		return make_rents_array(find_all_incomplete_rents(false));
	}
	
	public Object[][] get_rent_items(int rent_id)
	{
		return make_items_array(find_rent(rent_id, true));
	}
	
	
	public boolean add_rent_with_del(int customer_id, boolean pay_on_delivery, String delivery_date, String date,
			String return_date, LinkedList<int[]> ids_amounts)
	{
		Date d_d = Utilities.convert_string_to_date(delivery_date);
		Date d = Utilities.convert_string_to_date(date);
		Date r_d = Utilities.convert_string_to_date(return_date);
		Delivery del = new Delivery(true, d_d, pay_on_delivery);
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
		rent = new Rent(cust, d, r_d, items, false);
		rent.setDelivery(del);
		int rc = db.insert_rent(rent);
		return  rc == 1;
	}
	
	public boolean add_rent_without_del(int customer_id,  String date, String return_date, LinkedList<int[]> ids_amounts)
	{
		Date r_d = Utilities.convert_string_to_date(return_date);
		Date d = Utilities.convert_string_to_date(date);
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
		rent = new Rent(cust, d, r_d, items, false);
		return db.insert_rent(rent) == 1;
	}
	
	public boolean update_delivery_status(int rent_id)
	{
		rent = find_rent(rent_id, true);
		Delivery del = rent.getDelivery();
		del.setIn_progress(false);
		rent.setDelivery(del);
		return db.update_rent(rent) == 1;
	}
	
	public float finish_rent(int id, boolean recalculate_price)
	{
		rent = find_rent(id, false);
		rent.setComplete(true);
		float price; 
		if (recalculate_price)
		{
			price = rent.calculate_price(rent.getDate(), new Date());
		}
		else
		{
			price = rent.getRent_price();
		}
		boolean succeed = db.update_rent(rent) == 1;
		if (succeed)
		{
			return price;
		}
		return -1;
	}
	
	public boolean rent_exists(int id)
	{
		return find_rent(id, false) != null;
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
	
	private ArrayList<Rent> find_all_rents(boolean make_association)
	{
		return db.get_all_rents(make_association);
	}
	
	private ArrayList<Rent> find_all_complete_rents(boolean make_association)
	{
		return db.get_specific_rents("complete", "", 1, make_association);
	}
	
	private ArrayList<Rent> find_all_incomplete_rents(boolean make_association)
	{
		return db.get_specific_rents("complete", "", 0, make_association);
	}


	private Object[] make_rent_array(Rent rent)
	{
		int id = rent.getRent_id();
		Customer cust = rent.getCustomer();
		int cust_id = cust.getId();
		String cust_name = cust.getName();
		String date = Utilities.convert_date_to_string(rent.getDate());
		String return_date = Utilities.convert_date_to_string(rent.getReturn_date());
		float rent_price = rent.getRent_price();
		boolean complete = rent.isComplete();
		Delivery del = rent.getDelivery();
		if (del != null)
		{
			float delivery_cost = del.getCost();
			String delivery_date = Utilities.convert_date_to_string(del.getDate());
			Object[] data = { id, date, return_date, rent_price, delivery_cost, delivery_date, cust_id, cust_name, complete };
			return data;
		}
		else
		{
			Object[] data = {  id, date, return_date, rent_price, null, null, cust_id, cust_name, complete };
			return data;
		}
		
		
	}
	
	private Object[][] make_rents_array(ArrayList<Rent> rents)
	{
		Object[][] data = new Object[rents.size()][9];
		int i = 0;
		for(Rent rent : rents)
		{
			data[i] = make_rent_array(rent);
			i++;
		}
		return data;
	}
	
	private Object[] make_item_array(RentLineItem item)
	{
		Product prod = item.getProduct();
		int amount = item.getAmount();
		int product_id = prod.getId();
		String name = prod.getName();
		float retail_price = prod.getRetail_price();
		Float rent_price = prod.getRent_price();
		float daily_price = item.getDaily_price();
		Object[] data = { product_id, name, retail_price, rent_price, amount, daily_price };
		return data;
	}
	
	private Object[][] make_items_array(Rent rent)
	{
		ArrayList<RentLineItem> items = rent.getItems();
		Object[][] data = new Object[items.size()][6];
		int i = 0;
		for(RentLineItem item : items)
		{
			data[i] = make_item_array(item);
			i++;
		}
		return data;
	}

}
