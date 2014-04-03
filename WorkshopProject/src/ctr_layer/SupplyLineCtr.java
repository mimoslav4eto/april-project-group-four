package ctr_layer;

import java.util.ArrayList;





import model_layer.Product;
import model_layer.Supplier;
import db_layer.DBProduct;
import db_layer.DBSupplier;
import db_layer.DBSuppliesWith;

public class SupplyLineCtr
{
	private DBProduct db_p;
	private DBSupplier db_s;
	private Product prod;
	private Supplier sup;
	private DBSuppliesWith db_sw;

	public SupplyLineCtr()
	{
		db_sw = new DBSuppliesWith();
		db_p = new DBProduct();
		prod = new Product();
		db_s = new DBSupplier();
		sup = new Supplier();
	}
	
	public boolean add_product(String name, float retail_price, Float price, Float rent_price, int min_amount,
			int amount, int supplier_id)
	{
		prod = new Product(name, retail_price, price, rent_price, min_amount, amount);
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
		int rc = db_p.insert_product(prod);
		return rc == 1;
	}
	
	public boolean add_supplier(String name, String phone_nr, String email, String address, String zipcode, String city,String cVR, String description, String country)
	{
		sup = new Supplier(name, phone_nr, email, address, zipcode, city, cVR, description, country);
		int rc = db_s.insert_supplier(sup);
		return rc == 1;
	}
	
	private Product find_product(int product_id, boolean make_association)
	{
		if (prod.getId() == product_id && ((prod.getSupplied_by() != null) || !make_association))
		{
			return prod;
		}
		Product t_p = db_p.find_product(product_id, make_association);
		if(t_p != null)
		{
			prod = t_p;
		}
		return t_p;
	}
	
	private Supplier find_supplier(int supplier_id, boolean make_association)
	{
		if(sup.getId() == supplier_id && ((prod.getSupplied_by() != null) || !make_association))
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
	
	public boolean product_exists(int product_id)
	{
		return find_product(product_id, false) != null;
	}
	
	public boolean supplier_exists(int supplier_id)
	{
		return find_supplier(supplier_id, false) != null;
	}
	

}
