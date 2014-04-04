package db_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model_layer.Product;
import model_layer.Supplier;

public class DBSuppliesWith
{
	private Connection con;
	private DBSupplier db_s;
	private DBProduct db_p;
	public DBSuppliesWith()
	{
		con = DBConnection.get_instance().get_connection();
		db_s = new DBSupplier();
		db_p = new DBProduct();
	}
	
	public int insert_relation(Product prod, Supplier sup)
	{
		int rc = -1;
		int p_id = prod.getId();
		int s_id = sup.getId();
		System.out.println("Inserting product-supplier relation: " + p_id + "|" + s_id);
		try
		{
			PreparedStatement ps = UtilityFunctions.make_insert_statement(con, "Supplies", "product_id, supplier_id");
			ps.setInt(1, p_id);
			ps.setInt(2, s_id);
			ps.setQueryTimeout(5);
			rc = ps.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println("Error while inserting supplier-product relation: " + se);
		}
		return rc;
	}
	
	public int insert_multiple_relations(ArrayList<int[]> ids)
	{
		int rc = -1;
		int size = ids.size();
		try
		{
			PreparedStatement ps = UtilityFunctions.make_multiple_insert_statement(con, "Supplies", "product_id, supplier_id", size);
			int i = 0;
			for (int[] data : ids)
			{
				ps.setInt(1 + i*2, data[0]);
				ps.setInt(2 + i*2, data[1]);
				
			}
			ps.setQueryTimeout(5);
			rc = ps.executeUpdate()/size;
		}
		catch(SQLException se)
		{
			System.out.println("Error while inserting multiple supplier-product relation: " + se);
		}
		return rc;
	}
	
	public int delete_relation(Product prod, Supplier sup)
	{
		int rc = -1;
		int p_id = prod.getId();
		int s_id = sup.getId();
		System.out.println("Deleting product-supplier relation: " + p_id + "|" + s_id);
		try
		{
			PreparedStatement ps = con.prepareStatement("DELETE FROM Supplies WHERE p_id = ? AND s_id = ?");
			ps.setInt(1, p_id);
			ps.setInt(2, s_id);
			ps.setQueryTimeout(5);
			rc = ps.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println("Error while deleting supplier-product relation: " + se);
		}
		return rc;
	}
	
	public ArrayList<Supplier> find_suppliers_of(int product_id)
	{
		ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
		ResultSet results;
		String query = "SELECT e.id, e.name, e.address, e.zipcode, e.phone_nr, e.email, "
				+ "z.city, z.country, s.CVR, s.description "
				+ "FROM Supplies AS sups, Entity AS e, ZipCity AS z, Supplier AS s "
				+ "WHERE sups.supplier_id = s.id AND e.id = s.id AND e.type = 'supplier' AND sups.product_id = ?";
		try
		{
			PreparedStatement ps = UtilityFunctions.prepare_statement(con, query, "", product_id);
			ps.setQueryTimeout(5);
			results = ps.executeQuery();
			while(results.next())
			{
				suppliers.add(db_s.create_supplier(results));
			}
			return suppliers;
		}
		catch(SQLException se)
		{
			System.out.println("Error while searching for suppliers of product: ");
			se.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Product> find_supplied_by(int supplier_id)
	{
		ArrayList<Product> products = new ArrayList<Product>();
		ResultSet results;
		String query = "SELECT p.id, p.name, p.retail_price, p.price, p.rent_price, p.min_amount, p.amount "
				+ "FROM Supplies AS sups, Product AS p "
				+ "WHERE sups.product_id = p.id AND sups.supplier_id = ?";
		try
		{
			PreparedStatement ps = UtilityFunctions.prepare_statement(con, query, "", supplier_id);
			ps.setQueryTimeout(5);
			results = ps.executeQuery();
			while(results.next())
			{
				products.add(db_p.create_product(results));
			}
			return products;
		}
		catch(SQLException se)
		{
			System.out.println("Error while searching for products provided by supplier: ");
			se.printStackTrace();
			return null;
		}
	}

}
