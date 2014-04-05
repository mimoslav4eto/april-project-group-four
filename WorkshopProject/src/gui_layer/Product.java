package gui_layer;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import ctr_layer.SupplyLineCtr;

public class Product extends SuperGUI {
	private Object[] column_names={"id", "Name", "Price", "Rent Price", "Amount"};
	private Object[][] filling;
	private SupplyLineCtr products;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Customer frame = new Customer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Product() {
		setTitle("Products");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		products=new SupplyLineCtr();
		
		//filling=products.get_all_products();
		filling=new Object[0][0];
		fill_table(filling,column_names);
		
	}
	
	
	protected void search()
	{
		Object[][] fill = new Object[1][5];
		String info = field_search.getText();
		if (is_number(info))
		{
			if (products.product_exists(Integer.parseInt(info)))
			{
			fill[0] = products.find_product(Integer.parseInt(info),false);
			}
			else 
			{
				fill[0]=null;
			}
		}
		else
		{
			if (products.find_customer_by_name(info)!=null)
			{
				fill[0] = products.find_customer_by_name(info);
			}
			else 
			{
				fill[0]=null;
			}
		}
		fill_table(fill, column_names);
	}
	
	protected void clear()
	{
		fill_table(filling,column_names);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	protected void create()
	{
		ProductManager p = new ProductManager(-1, 0);
		p.setVisible(true);
	}
	
	protected void view()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				ProductManager p = new ProductManager((int) table.getModel().getValueAt(row_index, 0), 2);
				p.setVisible(true);
			}
		}
	}
	
	protected void edit()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				ProductManager p = new ProductManager((int) table.getModel().getValueAt(row_index, 0), 1);
				p.setVisible(true);
			}
		}
	}
	
	protected void delete()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete this customer?",
						"Delete customer", JOptionPane.YES_NO_OPTION))
				{
					products.delete_product((String) table.getModel().getValueAt(row_index, 0));
					//filling=productss.get_all_products();
					filling=new Object[0][0];
					fill_table(filling,column_names);
				}
			}
		}
	}

}
