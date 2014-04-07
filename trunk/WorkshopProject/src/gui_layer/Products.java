package gui_layer;
import ctr_layer.SupplyLineCtr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

public class Products extends SuperGUI {
	
	private String[] column_names={"id", "Name", "Price", "Rent Price", "Amount"};
	private Object[][] filling;
	protected SupplyLineCtr products;
	private JPanel panel;
	private JTextField tf_min;
	private JTextField tf_amount;
	private JTextField tf_id;
	private JTextField tf_name;
	private JTextField tf_retail;
	private JTextField tf_price;
	private JTextField tf_rent;
	private JButton btn_supl;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private boolean is_opened;
	private boolean is_editable;
	private Dimension dim;
	private JPanel panel_5;
	private JButton btn_ok;
	private ArrayList<Integer> supplier_ids;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Products p = new Products();
					p.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Products() {
		
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
					view();
			}
		});
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					edit();
				}
				else if (e.getClickCount() == 1)
				{
					view();
				}
			}
		});
		table.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() == '\u007F')
				{
					delete();
				}
			}
		});
		setTitle("Products");
		setSize(730, 513);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		is_opened=false;
		products=new SupplyLineCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		make_manager();
		Object[][] temp=products.get_all_products();
		refill_table(temp);
		is_editable=true;
		
	}
	private void refill_table(Object[][] temp)
	{
		if (temp!=null)
		{
			filling=new Object[temp.length][5];
			for (int i=0;i<temp.length;i++)
			{
				filling[i][0]=temp[i][0];
				filling[i][1]=temp[i][1];
				filling[i][2]=temp[i][3];
				filling[i][3]=temp[i][4];
				filling[i][4]=temp[i][6];
			}
			fill_table(filling,column_names);
		}
		else 
		{
			fill_table(new Object[0][0],column_names);
		}
	}
	
	private void make_manager()
	{
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Product", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		supplier_ids=new ArrayList<Integer>();
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		tf_id = new JTextField("ID");
		panel_1.add(tf_id);
		tf_id.setColumns(10);
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setEnabled(false);
		
		tf_name = new JTextField();
		panel_1.add(tf_name);
		tf_name.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent arg0)
			{
				if (((tf_name.getText().equals("Name"))&&(tf_name.getForeground()!=Color.black))||(tf_name.getText().equals("Not Valid")))
				{
					tf_name.setText("");
					tf_name.setForeground(Color.black);
					tf_name.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_name.getText().isEmpty())
				{
					tf_name.setText("Name");
					tf_name.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_name.setColumns(10);
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		tf_retail = new JTextField();
		tf_retail.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if (((tf_retail.getText().equals("Retail Price"))&&(tf_retail.getForeground()!=Color.black))||(tf_retail.getText().equals("Not Valid")))
				{
					tf_retail.setText("");
					tf_retail.setForeground(Color.black);
					tf_retail.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_retail.getText().isEmpty())
				{
					tf_retail.setText("Retail Price");
					tf_retail.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_2.add(tf_retail);
		tf_retail.setColumns(10);
		tf_retail.setText("Retail Price");
		tf_retail.setForeground(Color.LIGHT_GRAY);

		tf_price = new JTextField();
		panel_2.add(tf_price);
		tf_price.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_price.getText().equals("Price"))||(tf_price.getText().equals("Not valid")))
				{
					tf_price.setText("");
					tf_price.setForeground(Color.black);
					tf_price.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_price.getText().isEmpty())
				{
					tf_price.setText("Price");
					tf_price.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_price.setColumns(10);
		tf_price.setText("Price");
		tf_price.setForeground(Color.LIGHT_GRAY);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		
		tf_rent = new JTextField();
		tf_rent.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_rent.getText().equals("Rent Price"))||(tf_rent.getText().equals("Not valid")))
				{
					tf_rent.setText("");
					tf_rent.setForeground(Color.black);
					tf_rent.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_rent.getText().isEmpty())
				{
					tf_rent.setText("Rent Price");
					tf_rent.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_3.add(tf_rent);
		tf_rent.setColumns(10);
		tf_rent.setText("Rent Price");
		tf_rent.setForeground(Color.LIGHT_GRAY);

		tf_min = new JTextField();
		panel_3.add(tf_min);
		tf_min.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_min.getText().equals("Minimum Amount"))||(tf_min.getText().equals("Not valid")))
				{
					tf_min.setText("");
					tf_min.setForeground(Color.black);
					tf_min.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_min.getText().isEmpty())
				{
					tf_min.setText("Minimum Amount");
					tf_min.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_min.setColumns(10);
		tf_min.setText("Minimum Amount");
		tf_min.setForeground(Color.LIGHT_GRAY);
		
		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		
		tf_amount = new JTextField();
		tf_amount.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_amount.getText().equals("Amount"))||(tf_amount.getText().equals("Not valid")))
				{
					tf_amount.setText("");
					tf_amount.setForeground(Color.black);
					tf_amount.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_amount.getText().isEmpty())
				{
					tf_amount.setText("Amount");
					tf_amount.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_4.add(tf_amount);
		tf_amount.setColumns(10);
		tf_amount.setText("Amount");
		tf_amount.setForeground(Color.LIGHT_GRAY);
		
		btn_supl = new JButton("  Suppliers  ");
		btn_supl.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (!is_opened)
				{
					is_opened=true;
					if (!is_number(tf_id.getText()))
					{
						supplier_ids=products.get_all_suppliers_ids();
					}
					else
					{
						supplier_ids=products.get_suppliers_ids_of(Integer.parseInt(tf_id.getText()));
					}
					make_table_types();
				}
			}
		});
		panel_4.add(btn_supl);
		
		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 5;
		panel.add(panel_5, gbc_panel_5);
		
		btn_ok = new JButton("OK");
		panel_5.add(btn_ok);
		btn_ok.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				if (tf_name.isEnabled())
				{
					create_edit();
				}
			}
		});
	}
	private void make_table_types()
	{
		Supp p;
		if (String.valueOf(tf_id.getText()).equals("ID"))
		{
			p=new Supp(this,tf_id.getText());
		}
		else
		{
			p=new Supp(this,tf_id.getText());
		}
		
		p.setVisible(true);
	}
	private void clear_fields()
	{
		tf_id.setText("ID");
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setBackground(Color.white);
		
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		tf_name.setEnabled(true);
		tf_name.setBackground(Color.white);
		
		tf_retail.setText("Retail Price");
		tf_retail.setForeground(Color.LIGHT_GRAY);
		tf_retail.setEnabled(true);
		tf_retail.setBackground(Color.white);
		
		tf_price.setText("Price");
		tf_price.setForeground(Color.LIGHT_GRAY);
		tf_price.setEnabled(true);
		tf_price.setBackground(Color.white);
		
		tf_min.setText("Minimum Amount");
		tf_min.setForeground(Color.LIGHT_GRAY);
		tf_min.setEnabled(true);
		tf_min.setBackground(Color.white);
		
		tf_amount.setText("Amount");
		tf_amount.setForeground(Color.LIGHT_GRAY);
		tf_amount.setEnabled(true);
		tf_amount.setBackground(Color.white);
		
		tf_rent.setText("Rent Price");
		tf_rent.setForeground(Color.LIGHT_GRAY);
		tf_rent.setEnabled(true);
		tf_rent.setBackground(Color.white);
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (products.product_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][4];
			temp[0] = products.get_product_by_id(Integer.parseInt(info));
			}
		}
		else
		{
			if (products.get_products_by_name(info)!=null)
			{
				
				temp = products.get_products_by_name(info);
			}
		}
		refill_table(temp);
	}
	
	protected void clear()
	{
		clear_fields();
		filling=products.get_all_products();
		refill_table(filling);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
		is_editable=true;
	}
	protected void create()
	{
		clear_fields();
		is_editable=true;
	}
	
	protected void create_edit()
	{
		if ((is_ready())&&(tf_name.isEnabled()))
		{
			int id=-1;
			try
			{
				id = Integer.parseInt(tf_id.getText());
			}
			catch (Exception e)
			{
			}
			String name = tf_name.getText();
			float retail_price = Float.parseFloat(tf_retail.getText());
			Float price = Float.parseFloat(tf_price.getText());
			Float rent_price = Float.parseFloat(tf_rent.getText());
			int minimum_amount= new Integer(tf_min.getText());
			int amount = Integer.parseInt(tf_amount.getText());
			if (id==-1)
			{
				int temp_count=0;
				products.add_product(name, retail_price, price, rent_price, minimum_amount, amount,supplier_ids.get(0));
				supplier_ids.remove(0);
				Object[][] prod=products.get_products_by_name(name);
				for (int i=0;i<prod.length;i++)
				{
					if (id==Integer.parseInt(String.valueOf(prod[i][0])))
					{
						temp_count=i;
						break;
					}
				}
				for (int s_i:supplier_ids)
				{
				products.insert_product_supplier_relation(temp_count, s_i);
				}
				supplier_ids.clear();
				JOptionPane.showMessageDialog(this, "Successully created a product.", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				products.update_product(id, name, retail_price, price, rent_price, minimum_amount, amount);
				JOptionPane.showMessageDialog(this, "Successully edited a product.", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			clear();
			supplier_ids=new ArrayList<>();
		}
	}
	private boolean is_ready()
	{
		boolean is_ready=true;
		if ((tf_name.getText().equals("Name"))||(tf_name.getText().equals("")))
		{
			tf_name.setBackground(Color.red);
			tf_name.setText("Not Valid");
			is_ready=false;
		}
		if ((tf_retail.getText().equals("Retail Price"))||(tf_retail.getText().equals(""))||(!is_float(tf_retail.getText())))
		{
			tf_retail.setBackground(Color.red);
			tf_retail.setText("Not Valid");
			is_ready=false;
		}
		if ((tf_price.getText().equals("Price"))||(tf_price.getText().equals(""))||(!is_float(tf_price.getText())))
		{
			tf_price.setBackground(Color.red);
			tf_price.setText("Not valid");
			is_ready=false;
		}
		if ((tf_rent.getText().equals("Rent Price"))||(tf_rent.getText().equals(""))||(!is_float(tf_rent.getText())))
		{
			tf_rent.setBackground(Color.red);
			tf_rent.setText("Not valid");
			is_ready=false;
		}
		if ((tf_min.getText().equals("Minimum Amount"))||(tf_min.getText().equals(""))||(!is_number(tf_min.getText())))
		{
			tf_min.setBackground(Color.red);
			tf_min.setText("Not valid");
			is_ready=false;
		}
		if ((tf_amount.getText().equals("Amount"))||(tf_amount.getText().equals(""))||(!is_number(tf_amount.getText())))
		{
			tf_amount.setBackground(Color.red);
			tf_amount.setText("Not valid");
			is_ready=false;
		}
		
		return is_ready;
	}
	
	protected void view()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			int t_id=(int)table.getModel().getValueAt(row_index, 0);
			fill_fields(products.get_product_by_id(t_id),false);
			is_editable=false;
		}
	}
	
	protected void edit()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				int t_id=(int) table.getModel().getValueAt(row_index, 0);
				Object[] temp=products.get_product_by_id(t_id);
				fill_fields(temp,true);
				is_editable=true;
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
						"Are you sure you want to delete this product?",
						"Delete product", JOptionPane.YES_NO_OPTION))
				{
					products.delete_product((int)table.getModel().getValueAt(row_index, 0));
					filling=products.get_all_products();
					fill_table(filling,column_names);
				}
			}
		}
	}
	private void fill_fields(Object[] fill,boolean is_edit)
	{
		tf_id.setText(String.valueOf(fill[0]));
		tf_name.setText(String.valueOf(fill[1]));
		tf_retail.setText(String.valueOf(fill[2]));
		tf_price.setText(String.valueOf(fill[3]));
		tf_rent.setText(String.valueOf(fill[4]));
		tf_min.setText(String.valueOf(fill[5]));
		tf_amount.setText(String.valueOf(fill[6]));
		if (!is_edit)
		{
			tf_name.setEnabled(false);
			tf_retail.setEnabled(false);
			tf_price.setEnabled(false);
			tf_rent.setEnabled(false);
			tf_min.setEnabled(false);
			tf_amount.setEnabled(false);
		}
		else
		{
			tf_id.setForeground(Color.black);
			tf_name.setEnabled(true);
			tf_name.setForeground(Color.black);
			tf_retail.setEnabled(true);
			tf_retail.setForeground(Color.black);
			tf_price.setEnabled(true);
			tf_price.setForeground(Color.black);
			tf_rent.setEnabled(true);
			tf_rent.setForeground(Color.black);
			tf_min.setEnabled(true);
			tf_min.setForeground(Color.black);
			tf_amount.setEnabled(true);
			tf_amount.setForeground(Color.black);
		}
	}

class Supp extends SuperGUI {
	
	private Object[][] filling;
	private String[] column_names={"id","Name", "Phone Number", "Country"};
	private Products instance;
	private JButton btn_add;
	private JButton btn_remove;
	private JPanel btn_panel;
	private String id;
	
	public Supp(Products instance, String is_id) {
		this.instance=instance;
		setTitle("Current Suppliers");
		setSize(450, 300);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		id=is_id;
		filling= products.get_suppliers_by_ids(supplier_ids);
		refill_table(filling);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount()==2)
				choose();
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	is_opened=false;
		    }
		});
		btn_panel= new JPanel();
		search_panel.add(btn_panel,BorderLayout.EAST);
		
		
		btn_add=new JButton("Add Supplier");
		if (!is_editable)
		{
			btn_add.setVisible(false);
		}
		btn_panel.add(btn_add);
		btn_add.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
					choose();
			}
		});
		btn_remove=new JButton("Delete Supplier");
		if (!is_editable)
		{
			btn_remove.setVisible(false);
		}
		btn_panel.add(btn_remove);
		btn_remove.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
					remove();
			}
		});
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (supplier_ids.contains(Integer.parseInt(info)))
			{
			temp = new Object[1][4];
			temp[0] = products.get_supplier_by_id(Integer.parseInt(info));
			
			}
			refill_table(temp);
		}
		else
		{
			 for (int row = 0; row <= table.getRowCount() - 1;) {
	                    if (!info.equals(String.valueOf(table.getValueAt(row, 1)))) {
	                    	((MyTableModel)table.getModel()).removeRow(row);
	                }
	                    else
	                    {
	                    	row++;
	                    }
	            }
		}
	}
	private void refill_table(Object[][] temp)
	{
		Object[][] fill;
		if ((temp!=null)&&(temp.length!=0))
		{
			fill=new Object[temp.length][4];
			for (int i=0;i<temp.length;i++)
			{
				
				fill[i][0]=temp[i][0];
				fill[i][1]=temp[i][1];
				fill[i][2]=temp[i][3];
				fill[i][3]=temp[i][4];
			}
			fill_table(fill,column_names);
		}
		else 
		{
			fill_table(new Object[0][0],column_names);
		}
	}
	protected void clear()
	{
		ArrayList<Object[]> temp=new ArrayList<>();
		Object[][] first;
		for (int supplier:supplier_ids)
		{
			temp.add(products.get_supplier_by_id(supplier));
		}
		first= temp.toArray(new Object[temp.size()][8]);
		refill_table(first);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	protected void choose()
	{
			Choose p = new Choose(this);
			p.setVisible(true);
	}
	private void remove()
	{
		if (is_number(id))
		{
			int row_index = table.getSelectedRow();
			if (row_index != -1)
			{
				int s_id=(int)table.getModel().getValueAt(row_index, 0);
				
				if (products.delete_product_supplier_relation(Integer.parseInt(id), s_id))
				{
					JOptionPane.showMessageDialog(this, "Successully deleted a supplier.", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "You didn't succeeded in creating a product a product. Please Try Again or contact Support.", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		else
		{
			int row_index = table.getSelectedRow();
			if (row_index != -1)
			{
				int s_id=(int)table.getModel().getValueAt(row_index, 0);
				try
				{
					supplier_ids.remove((Object)s_id);
				}
				catch (Exception e)
				{
				}
			}
		}
		clear();
	}
}

class Choose extends SuperGUI {
	
	private Object[][] filling;
	private String[] column_names={"id","Name", "Phone Number", "Country"};
	private Supp instance;
	private JButton btn_add;
	private JPanel btn_panel;
	
	
	public Choose(Supp instance) {
		this.instance=instance;
		setTitle("Choose New Supplier");
		setSize(450, 300);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		filling= products.get_all_suppliers();
		refill_table(filling);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount()==2)
				choose();
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	is_opened=false;
		    }
		});
		btn_panel= new JPanel();
		search_panel.add(btn_panel,BorderLayout.EAST);
		
		
		btn_add=new JButton("Choose");
		
		btn_add.setVisible(true);
		btn_panel.add(btn_add);
		btn_add.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
					choose();
			}
		});
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (products.supplier_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][4];
			temp[0] = products.get_supplier_by_id(Integer.parseInt(info));
			}
		}
		refill_table(temp);
	}
	private void refill_table(Object[][] temp)
	{
		if ((temp!=null)&&(temp.length!=0))
		{
			filling=new Object[temp.length][4];
			for (int i=0;i<temp.length;i++)
			{
				
				filling[i][0]=temp[i][0];
				filling[i][1]=temp[i][1];
				filling[i][2]=temp[i][3];
				filling[i][3]=temp[i][4];
			}
			fill_table(filling,column_names);
		}
		else 
		{
			fill_table(new Object[0][0],column_names);
		}
	}
	protected void clear()
	{
		filling=products.get_all_suppliers();
		fill_table(filling, column_names);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	protected void choose()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			int s_id=(int)table.getModel().getValueAt(row_index, 0);
			if (!supplier_ids.contains(s_id))
			{
				supplier_ids.add(s_id);
				ArrayList<Object[]> temp=new ArrayList<>();
				Object[][] first;
				for (int supplier:supplier_ids)
				{
					temp.add(products.get_supplier_by_id(supplier));
				}
				first= temp.toArray(new Object[temp.size()][8]);
				instance.refill_table(first);
				dispose();
			}
	}
	}
}

}
