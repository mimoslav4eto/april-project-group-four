package ctr_layer;

import java.util.ArrayList;
import java.util.Collection;
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
			find_all_customers();
		}
		ct = new CustomerType();
	}
	
	public Object[] get_customer_by_id(int id)
	{
		return make_customer_array(find_customer(id));
	}
	
	public Object[][] get_customer_by_name(String name)
	{
		return make_customers_array(find_customers_by_name(name));
	}
	
	public Object[][] get_all_customers()
	{
		return make_customers_array(find_all_customers());
	}
	
	public Object[][] get_non_deleted_customers()
	{
		return make_customers_array(find_non_deleted_customers());
	}
	
	public Object[] get_customer_type(int id)
	{
		return make_type_array(find_customer_type(id));
	}
	
	public Object[][] get_all_customer_types()
	{
		if (make_types_array(find_all_customer_types())==null)
		{
			return new Object[0][0];
		}
		return make_types_array(find_all_customer_types());
	}
	
	public int add_customer(String name, String phone_nr, String email,
			String address, String zipcode, String city, String preferences, int type_id)
	{
		CustomerType ct = find_customer_type(type_id);
		Customer cust = new Customer(name, phone_nr, email, address, zipcode, city, ct, preferences);
		int key = db.insert_customer(cust);
		if( key !=-1)
		{
			cust.setId(key);
			all_customers.put(key, cust);
			return key;
		}
		return -1;
		
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
		if(db.update_customer(cust) == 1)
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
	public boolean customer_type_exists(int type_id)
	{
		return find_customer_type(type_id) != null;
	}

	private Customer find_customer(int id)
	{
		Customer cust  = null;
		if (all_customers.containsKey(id))
		{
			cust =  all_customers.get(id);
			if(cust.getName() == null)
			{
				cust = null;
			}
		}

		return cust;
	}
	
	private HashMap<Integer, Customer> find_all_customers()
	{
		if (all_customers.isEmpty())
		{
			all_customers = db.get_all_customers();
		}
		return all_customers;
	}
	
	private ArrayList<Customer> find_non_deleted_customers()
	{
		ArrayList<Customer> non_deleted = new ArrayList<Customer>();
		for(Customer customer : all_customers.values())
		{
			if (customer.getName() != null)
			{
				non_deleted.add(customer);
			}
		}
		return non_deleted;
	}
	
	private HashMap<Integer, Customer> find_customers_by_name(String name)
	{
		return db.get_some_customers("name", name, -1);
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
	
	private ArrayList<CustomerType> find_all_customer_types()
	{
		return db.get_all_types();
	}
	
	private Object[] make_customer_array(Customer cust)
	{
		String name = cust.getName();
		if (name!=null)
		{
			String address = cust.getAddress();
			String city = cust.getCity();
			String email = cust.getEmail();
			String phone_nr = cust.getPhone_nr();
			int id = cust.getId();
			String preferences = cust.getPreferences();
			String zipcode =  cust.getZipcode();
			ct = cust.getCust_type();
			float disc_perc = ct.getDisc_perc();
			int t_id = ct.getId();
			float price_qual_for_disc = ct.getPrice_qual_for_disc();
			float price_qual_for_free_shipment = ct.getPrice_qual_for_free_shipment();
			
			Object[] data = { id, name, address, zipcode, city, email, phone_nr, preferences, t_id, disc_perc, price_qual_for_disc, price_qual_for_free_shipment };
			return data;
		}
		else
		{
			return null;
		}
	}
	
	private Object[][] make_customers_array(HashMap<Integer, Customer> map)
	{
		Object[][] data = new Object[map.size()][12];
		int i =0;
		for(Customer cust : map.values())
		{
			data[i] = make_customer_array(cust);
			i++;
		}
		return data;
	}
	
	private Object[][] make_customers_array(ArrayList<Customer> customers)
	{
		Object[][] data = new Object[customers.size()][12];
		int i =0;
		for(Customer cust : customers)
		{
			data[i] = make_customer_array(cust);
			i++;
		}
		return data;
	}
	
	private Object[][] make_types_array(ArrayList<CustomerType> types)
	{
		Object[][] data = new Object[types.size()][4];
		int i = 0;
		for(CustomerType type : types)
		{
			data[i] = make_type_array(type);
			i++;
		}
		return data;
	}
	
	private Object[] make_type_array(CustomerType type)
	{
		int id = type.getId();
		float disc_perc = type.getDisc_perc();
		float price_qual_for_disc = type.getPrice_qual_for_disc();
		float price_qual_for_free_shipment = type.getPrice_qual_for_free_shipment();
		Object[] data = { id, disc_perc, price_qual_for_disc, price_qual_for_free_shipment };
		return data;
	}
}
