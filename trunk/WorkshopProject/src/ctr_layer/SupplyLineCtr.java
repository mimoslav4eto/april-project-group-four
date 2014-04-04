package ctr_layer;

import java.util.ArrayList;





import java.util.HashMap;

import model_layer.Product;
import model_layer.Supplier;
import db_layer.DBProduct;
import db_layer.DBSupplier;
import db_layer.DBSuppliesWith;

public class SupplyLineCtr
{
	private DBProduct db_p;
	private DBSupplier db_s;
	private static Supplier sup;
	private static HashMap<Integer, Product> all_products;
	private DBSuppliesWith db_sw;

	public SupplyLineCtr()
	{
		db_sw = new DBSuppliesWith();
		db_p = new DBProduct();

		if(all_products == null)
		{
			all_products = new HashMap<Integer, Product>();
			get_all_products();
		}
		db_s = new DBSupplier();
		if(sup == null)
		{
			sup= new Supplier();
		}
		
	}
	
	public boolean add_product(String name, float retail_price, Float price, Float rent_price, int min_amount,
			int amount, int supplier_id)
	{
		Product prod = new Product(name, retail_price, price, rent_price, min_amount, amount, false);
		if(supplier_id != -1)
		{
			ArrayList<Supplier> supplied_by = new ArrayList<Supplier>();
			if (!(supplier_id == sup.getId()))
			{
				Supplier t_s = db_s.find_supplier(supplier_id, false);
				if(t_s != null)
				{
					sup = t_s;
					supplied_by.add(sup);
				}
			}
			else 
			{ 
				supplied_by.add(sup); 
			}
			prod.setSupplied_by(supplied_by);
		}
		int key = db_p.insert_product(prod);
		if (key != -1)
		{
			all_products.put(key, prod);
			return true;
		}
		return false;
	}
	
	public boolean add_supplier(String name, String phone_nr, String email, String address, String zipcode, String city,String cVR, String description, String country)
	{		
		sup = new Supplier(name, phone_nr, email, address, zipcode, city, cVR, description, country);
		return db_s.insert_supplier(sup) == 1;
	}
	
	public boolean update_product_amount(int product_id, int amount)
	{
		Product prod = find_product(product_id, false);
		prod.setAmount(amount);
		if(db_p.update_product(prod) == 1)
		{
			all_products.put(product_id, prod);
			return true;
		}
		return false;
		
	}
	
	public boolean update_product(int product_id, String name, float retail_price, Float price, Float rent_price, int min_amount,
			int amount)
	{
		Product prod = find_product(product_id, false);
		prod.setAmount(amount);
		prod.setMin_amount(min_amount);
		prod.setName(name);
		prod.setPrice(rent_price);
		prod.setRetail_price(retail_price);
		prod.setRent_price(rent_price);
		if( db_p.update_product(prod) == 1)
		{
			all_products.put(product_id, prod);
			return true;
		}
		return false;
		
	}
	
	public boolean delete_product(int product_id)
	{
		Product prod = find_product(product_id, false);
		prod.setDeleted(true);
		prod.setAmount(0);
		if(db_p.update_product(prod) == 1)
		{
			all_products.put(product_id, prod);
			return true;
		}
		return false;
	}
	
	public boolean update_supplier(int supplier_id, String name, String phone_nr, String email, String address, String zipcode, String city,String cVR, String description, String country)
	{
		Supplier sup = find_supplier(supplier_id, false);
		sup.setName(name);
		sup.setPhone_nr(phone_nr);
		sup.setAddress(address);
		sup.setCity(city);
		sup.setCVR(cVR);
		sup.setDescription(description);
		sup.setEmail(email);
		sup.setZipcode(zipcode);

		return db_s.update_supplier(sup) == 1;
		
		
	}
	
	
	public boolean insert_product_supplier_relation(int p_id, int s_id)
	{
		Product prod = find_product(p_id, true);
		Supplier s = find_supplier(s_id, false);
		
		if(db_sw.insert_relation(prod, sup) == 1)
		{
			prod.getSupplied_by().add(s);
			all_products.put(p_id, prod);
			return true;
		}
		return false;
	}
	
	public boolean insert_product_supplier_relations(ArrayList<int[]> ids)
	{
		if(db_sw.insert_multiple_relations(ids) == 1)
		{
			for (int[] data : ids)
			{
				Product prod = find_product(data[0], true);
				Supplier s = find_supplier(data[1], false);
				prod.getSupplied_by().add(s);
				all_products.put(data[0], prod);
			}
			return true;
		}
		return false;
	}
	
	public boolean delete_product_supplier_relation(int p_id, int s_id)
	{
		Product p = find_product(p_id, true);
		Supplier s = new Supplier();
		
		s.setId(s_id);
		if(db_sw.delete_relation(p, s) == 1)
		{
			p.remove_supplier(s_id);
			all_products.put(p_id, p);
			return true;
		}
		return false;
	}
	
	private Product find_product(int product_id, boolean make_association)
	{
		Product prod = null;
		if (all_products.containsKey(product_id))
		{
			 prod = all_products.get(product_id);
			if((prod.getSupplied_by() != null) || !make_association)
			{
				return prod;
			}
			prod = db_p.find_product(product_id, make_association);
		}
		
		if (prod != null)
		{
			all_products.put(prod.getId(), prod);
		}
		return prod;
	}
	
	private Supplier find_supplier(int supplier_id, boolean make_association)
	{
		if(sup.getId() == supplier_id && ((sup.getSupplies_with() != null) || !make_association))
		{
			return sup;
		}
		Supplier t_s = db_s.find_supplier(supplier_id, make_association);
		if (t_s != null)
		{
			sup = t_s;
		}
		return t_s;
	}
	
	private HashMap<Integer, Product> get_all_products()
	{
		if(all_products.isEmpty())
		{
			all_products = db_p.get_all_products(true);
		}
		return all_products;
	}
	
	private ArrayList<Supplier> get_all_suppliers(boolean make_association)
	{

		return db_s.get_all_suppliers(make_association);
	}
	
	
	public boolean product_exists(int product_id)
	{
		return find_product(product_id, false) != null;
	}
	
	public boolean supplier_exists(int supplier_id)
	{
		return find_supplier(supplier_id, false) != null;
	}
	

}
