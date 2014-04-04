package ctr_layer;

import java.util.HashMap;

import model_layer.Customer;
import model_layer.CustomerType;
import db_layer.DBCustomer;

public class CustomerCtr
{
	private DBCustomer db;
	private static HashMap<Integer, Customer> all_customers;
	private CustomerType ct;
	public CustomerCtr()
	{
		db = new DBCustomer();
		if(all_customers == null)
		{
			all_customers = new HashMap<Integer, Customer>();
			get_all_customers();
		}
		ct = new CustomerType();
	}
	
	
	
	public boolean add_customer(String name, String phone_nr, String email,
			String address, String zipcode, String city, String preferences, int type_id)
	{
		CustomerType ct = find_customer_type(type_id);
		Customer cust = new Customer(name, phone_nr, email, address, zipcode, city, ct, preferences);
		int key = db.insert_customer(cust);
		if( key !=-1)
		{
			all_customers.put(key, cust);
			return true;
		}
		return false;
		
	}
	
	public boolean update_customer(int id, String name, String phone_nr, String email,
			String address, String zipcode, String city, String preferences, int type_id)
	{
		CustomerType ct = find_customer_type(type_id);
		Customer cust = find_customer(id);
		cust.setAddress(address);
		cust.setCity(city);
		cust.setCust_type(ct);
		cust.setEmail(email);
		cust.setName(name);
		cust.setPhone_nr(phone_nr);
		cust.setPreferences(preferences);
		cust.setZipcode(zipcode);
		if(db.insert_customer(cust) == 1)
		{
			all_customers.put(id, cust);
			return true;
		}
		return false;
	}
	
	public boolean delete_customer(int id)
	{
		
		Customer empty = find_customer(id);
		empty.setAddress(null);
		empty.setCity(null);
		empty.setEmail(null);
		empty.setName(null);
		empty.setPhone_nr(null);
		empty.setPreferences(null);
		if(db.update_customer(empty) == 1)
		{
			all_customers.put(id, empty);
			return true;
		}
		return false;
		
	}
	
	public boolean add_customer_type(float price_qual_for_disc, float price_qual_for_free_shipment, float disc_perc)
	{
		CustomerType ct = new CustomerType(price_qual_for_disc, price_qual_for_free_shipment, disc_perc);
		return db.insert_customer_type(ct) == 1;
	}
	
	public boolean update_customer_type(int id, float price_qual_for_disc, float price_qual_for_free_shipment, float disc_perc)
	{
		CustomerType ct = new CustomerType(price_qual_for_disc, price_qual_for_free_shipment, disc_perc);
		ct.setId(id);
		return db.update_customer_type(ct) == 1;
	}
	
	public boolean customer_exists(int customer_id)
	{
		return find_customer(customer_id) != null;
	}

	private Customer find_customer(int id)
	{
		Customer cust  = null;
		if (all_customers.containsKey(id))
		{
			cust =  all_customers.get(id);
		}

		return cust;
	}
	
	private HashMap<Integer, Customer> get_all_customers()
	{
		if (all_customers.isEmpty())
		{
			all_customers = db.get_all_customers();
		}
		return all_customers;
	}
	
	private CustomerType find_customer_type(int id)
	{
		if (ct.getId() == id)
		{
			return ct;
		}
		ct = db.find_customer_type(id);
		
		if(ct != null)
		{
			return ct;
		}
		return null;
	}
}
