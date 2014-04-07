package ctr_layer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import model_layer.Customer;
import model_layer.CustomerType;
import model_layer.Delivery;
import model_layer.Rent;
import model_layer.Product;
import model_layer.RentLineItem;
import db_layer.DBCustomer;
import db_layer.DBRent;
import db_layer.DBProduct;

public class RentCtr
{
	private DBRent db;
	private DBProduct db_p;
	private SupplyLineCtr prod_ctr;
	private DBCustomer db_c;
	private static Rent rent;
	private static Customer cust;
	public RentCtr()
	{
		db = new DBRent();
		db_p = new DBProduct();
		db_c = new DBCustomer();
		prod_ctr = new SupplyLineCtr();
		
		if(cust == null)
		{
			cust = new Customer();
		}

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
			int id = data[0];
			int amount = data[1];
			prod = db_p.find_product(id, false);
			item = new RentLineItem(prod, amount);
			items.add(item);
			prod_ctr.subtract_amount(id, amount);
			
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
			int id = data[0];
			int amount = data[1];
			prod = db_p.find_product(id, false);
			item = new RentLineItem(prod, amount);
			items.add(item);
			prod_ctr.subtract_amount(id, amount);
			
		}
		rent = new Rent(cust, d, r_d, items, false);
		return db.insert_rent(rent) == 1;
	}
	
	public float[] calc_rent_price(int cust_id, LinkedList<int[]> ids_amounts, boolean pay_on_delivery, boolean delivery, String start_date, String end_date)
	{
		Date begin = Utilities.convert_string_to_date(start_date);
		Date end = Utilities.convert_string_to_date(end_date);
		int days = (int)(end.getTime() - begin.getTime())/(1000*60*60*24);
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
			tot_price += prod_ctr.calculate_rent_cost(data[0], data[1], days);
		}
		float price_qual_for_free = ct.getPrice_qual_for_free_shipment();
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
		tot_price += del_cost;
		prices[0] = tot_price;
		prices[1] = del_cost;
		return prices;

	}
	
	public boolean update_delivery_status(int rent_id)
	{
		rent = find_rent(rent_id, true);
		Delivery del = rent.getDelivery();
		del.setIn_progress(false);
		rent.setDelivery(del);
		return db.update_rent(rent) == 1;
	}
	
	public boolean finish_rent(int id, boolean recalculate_price)
	{
		rent = find_rent(id, true);
		rent.setComplete(true);
		Delivery del = rent.getDelivery();
		del.setIn_progress(false);
		rent.setDelivery(del);
		if (recalculate_price)
		{
			rent.calculate_price(rent.getDate(), new Date());
		}
		ArrayList<RentLineItem> items = rent.getItems();
		for(RentLineItem item: items)
		{
			prod_ctr.increase_amount(item.getProduct().getId(), item.getAmount());
		}
		
		return db.update_rent(rent) == 1;
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
			boolean in_progress = del.isIn_progress();
			boolean pay_on_delivery = del.isPay_on_delivery();
			Object[] data = { id, date, return_date, rent_price, cust_id, cust_name, in_progress, pay_on_delivery, delivery_date,  delivery_cost, complete };
			return data;
		}
		else
		{
			Object[] data = {  id, date, return_date, rent_price, cust_id, cust_name, null, null, null, null, complete };
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
