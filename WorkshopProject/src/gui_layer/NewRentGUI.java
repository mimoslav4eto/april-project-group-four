package gui_layer;


import ctr_layer.OrderCtr;
import ctr_layer.RentCtr;
import ctr_layer.SupplyLineCtr;
import ctr_layer.Utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;


import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JCheckBox;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;

public class NewRentGUI extends NewOrderRentSuperGUI
{
	
	private final String[] column_names = { "Product ID", "Product name", "Product retail price", "Product rent price", "Amount", "Daily price" };
	private Object[][] filling;
	private RentCtr rent_ctr;
	private SupplyLineCtr prod_ctr;
	private JTextField id_tf;
	private JTextField b_d_tf;
	private JTextField r_d_tf;
	private JTextField price_tf;
	private JTextField d_d_tf;
	private JTextField d_c_tf;
	private JTextField c_id_tf;
	private JTextField c_n_tf;
	private JCheckBox completed_box;
	private JCheckBox chckbxPaymentOnDelivery;
	private JCheckBox chckbxDelivered;
	private JCheckBox chckbxDelivery;
	private boolean is_open;
	private ArrayList<Object[]> selected;
	private boolean delivery;
	private float[] prices;
	private LinkedList<int[]> ids_amounts;
	

	public NewRentGUI(boolean i_creating, int i_id)
	{
		super(i_creating, i_id);
		delivery = false;
		is_open = false;
		rent_ctr = new RentCtr();
		prod_ctr = new SupplyLineCtr();
		ids_amounts = new LinkedList<int[]>();
		selected = new ArrayList<Object[]>();
		prices = new float[2];
		make_data_display();
		prepare_gui();
		if(!creating)
		{
			refresh_table(rent_ctr.get_rent_items(id));
		}
		
	}
	
	private void make_data_display()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EmptyBorder(1, 2, 1, 3), "Order Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{172, 0};
		gbl_panel.rowHeights = new int[]{16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel id_lbl = new JLabel("ID");
		panel_1.add(id_lbl);
		
		id_tf = new JTextField();
		id_tf.setEnabled(false);
		panel_1.add(id_tf);
		id_tf.setColumns(10);
		
		
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel b_d_lbl = new JLabel("Begin date");
		panel_2.add(b_d_lbl);
		
		b_d_tf = new DateTextField();
		panel_2.add(b_d_tf);
		b_d_tf.setColumns(10);
		
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel r_d_lbl = new JLabel("Return date");
		panel_3.add(r_d_lbl);
		
		r_d_tf = new DateTextField();
		panel_3.add(r_d_tf);
		r_d_tf.setColumns(10);
		
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel price_lbl = new JLabel("Price");
		panel_4.add(price_lbl);
		
		price_tf = new JTextField();
		price_tf.setEnabled(false);
		panel_4.add(price_tf);
		price_tf.setColumns(10);
		
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.insets = new Insets(0, 0, 5, 0);
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 4;
		panel.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel c_id_lbl = new JLabel("Customer ID");
		panel_5.add(c_id_lbl);
		
		c_id_tf = new JTextField();
		panel_5.add(c_id_tf);
		c_id_tf.setColumns(10);
		
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 5;
		panel.add(panel_6, gbc_panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel c_n_lbl = new JLabel("Customer name");
		panel_6.add(c_n_lbl);
		
		c_n_tf = new JTextField();
		panel_6.add(c_n_tf);
		c_n_tf.setColumns(10);
		
		
		JPanel panel_10 = new JPanel();
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.insets = new Insets(0, 0, 5, 0);
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 6;
		panel.add(panel_10, gbc_panel_10);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxDelivery = new JCheckBox("Delivery");
		panel_10.add(chckbxDelivery);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 7;
		panel.add(panel_7, gbc_panel_7);
		panel_7.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		JLabel d_d_lbl = new JLabel("Delivery date");
		panel_7.add(d_d_lbl);
		
		d_d_tf = new DateTextField();
		panel_7.add(d_d_tf);
		d_d_tf.setColumns(10);

		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 8;
		panel.add(panel_8, gbc_panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel d_c_lbl = new JLabel("Delivery cost");
		panel_8.add(d_c_lbl);
		
		d_c_tf = new JTextField();
		panel_8.add(d_c_tf);
		d_c_tf.setColumns(10);
		
		
		JPanel panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.anchor = GridBagConstraints.WEST;
		gbc_panel_11.fill = GridBagConstraints.VERTICAL;
		gbc_panel_11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 9;
		panel.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxPaymentOnDelivery = new JCheckBox("Payment on delivery");
		
		panel_11.add(chckbxPaymentOnDelivery);
		
		JPanel panel_12 = new JPanel();
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 10;
		panel.add(panel_12, gbc_panel_12);
		panel_12.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxDelivered = new JCheckBox("Delivered");
		
		panel_12.add(chckbxDelivered);
		
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.anchor = GridBagConstraints.SOUTHWEST;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 11;
		panel.add(panel_9, gbc_panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		if(!creating)
		{
			completed_box = new JCheckBox("Completed");
			panel_9.add(completed_box);
			completed_box.setEnabled(false);
		}
		

		d_c_tf.setEnabled(false);
		
		
	}
	
	private void prepare_gui()
	{
		if(!creating)
		{
			b_d_tf.setEnabled(false);
			c_id_tf.setEnabled(false);
			r_d_tf.setEnabled(false);
			c_n_tf.setEnabled(false);
			chckbxDelivery.setEnabled(false);
			d_d_tf.setEnabled(false);
			chckbxDelivered.setEnabled(false);
			chckbxPaymentOnDelivery.setEnabled(false);
			fill_fields();
		}
		else
		{
			
			id_tf.setText("ID");
			d_d_tf.setText("");

			b_d_tf.addFocusListener(new FocusAdapter()
			{
				public void focusGained(FocusEvent arg0)
				{
					make_normal_bg(b_d_tf);
				}
			});
			d_d_tf.setEnabled(false);
			d_d_tf.addFocusListener(new FocusAdapter()
			{
				public void focusGained(FocusEvent arg0)
				{
					make_normal_bg(d_d_tf);
				}

			});
			d_c_tf.setEnabled(false);
			c_n_tf.setEnabled(false);
			c_id_tf.addFocusListener(new FocusAdapter()
			{
				public void focusGained(FocusEvent arg0)
				{
					make_normal_bg(c_id_tf);
					
				}
				
				public void focusLost(FocusEvent arg0)
				{
					display_prices();
				}
			});
			
			c_id_tf.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if(!is_open)
					{
						is_open = true;
						AddCustomer ac = new AddCustomer();
						ac.setVisible(true);
						
					}
				}
			} );
			
			r_d_tf.addFocusListener(new FocusAdapter()
			{
				public void focusGained(FocusEvent arg0)
				{
					make_normal_bg(r_d_tf);
				}

			});
			chckbxDelivered.setVisible(false);
			chckbxPaymentOnDelivery.setEnabled(false);
			chckbxPaymentOnDelivery.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg0)
				{
					display_prices();
				}
			});
			chckbxDelivery.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg0)
				{
					if (delivery)
					{
						delivery = false;
						d_d_tf.setEnabled(false);
						chckbxPaymentOnDelivery.setEnabled(false);
						d_d_tf.setText("");
						d_c_tf.setText("");
						chckbxPaymentOnDelivery.setSelected(false);
					}
					else
					{
						delivery = true;
						d_d_tf.setEnabled(true);
						chckbxPaymentOnDelivery.setEnabled(true);
						display_prices();
					}
				}
			});
		}
	}
	
	private void refresh_table(Object[][] data)
	{
		if (data != null)
		{
			filling = data;
			fill_table(filling, column_names);
		}
		else 
		{
			fill_table(new Object[0][0], column_names);
		}
	}
	
	private void fill_fields()
	{
		Object[] data = rent_ctr.get_rent(id);
		if(data != null)
		{
			id_tf.setText(String.valueOf(data[0]));
			b_d_tf.setText(String.valueOf(data[1]));
			r_d_tf.setText(String.valueOf(data[2]));
			price_tf.setText(String.valueOf(data[3]));
			c_id_tf.setText(String.valueOf(data[4]));
			c_n_tf.setText(String.valueOf(data[5]));
			boolean has_delivery = (data[6] != null);
			chckbxDelivery.setSelected(has_delivery);
			if(has_delivery)
			{
				d_d_tf.setText(String.valueOf(data[8]));
				d_c_tf.setText(String.valueOf(data[9]));
				chckbxDelivered.setSelected(!(boolean)data[6] );
				chckbxPaymentOnDelivery.setSelected((boolean)data[7] );
				completed_box.setSelected((boolean) data[10]);
			}
		}
	}
	

	@Override
	protected void create()
	{
		boolean correct = true;
		String c_id = c_id_tf.getText();
		String begin_date = b_d_tf.getText();
		String return_date = r_d_tf.getText();
		int cust_id = -1;
		if(is_number(c_id))
		{
			cust_id = Integer.parseInt(c_id);
			if(!cust_ctr.customer_exists(cust_id))
			{
				correct = false;
				make_error_bg(c_id_tf);
			}
		}
		else
		{
			correct = false;
			make_error_bg(c_id_tf);
		}
		if(!Utilities.valid_date(begin_date))
		{
			correct = false;
			make_error_bg(b_d_tf);
		}
		
		if(!Utilities.valid_date(return_date))
		{
			correct = false;
			make_error_bg(r_d_tf);
		}
		
		if(Utilities.day_difference(begin_date, return_date) <=0)
		{
			correct = false;
			make_error_bg(b_d_tf);
			make_error_bg(r_d_tf);
		}

		if(ids_amounts.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "You cannot make an empty order.\nAdd some products to it!", "Error", JOptionPane.ERROR_MESSAGE);
			correct=false;
		}
		
		if(chckbxDelivery.isSelected())
		{
			boolean pay_on_delivery = chckbxPaymentOnDelivery.isSelected();
			String delivery_date = d_d_tf.getText();
			
			if(!Utilities.valid_date(delivery_date))
			{
				correct = false;
				make_error_bg(d_d_tf);
			}
			if(correct)
			{
				rent_ctr.add_rent_with_del(cust_id, pay_on_delivery, delivery_date, begin_date, return_date, ids_amounts);
				this.dispose();
			}
		}
		else if(correct)
		{

			rent_ctr.add_rent_without_del(cust_id, begin_date, return_date, ids_amounts);
			this.dispose();
			
		}
			

	}

	@Override
	protected void edit()
	{
		if(creating)
		{
			int row_index = table.getSelectedRow();
			if (row_index != -1)
			{
				if( table.getModel().getValueAt(row_index, 0) != null)
				{
					int id = (int)table.getModel().getValueAt(row_index, 0);
					String quantity = JOptionPane
							.showInputDialog("Please input the amount of the product");
					if (is_number(quantity))
					{
						int amount =Integer.parseInt(quantity);
						if (amount > 0)
						{
							if (prod_ctr.is_such_amount(id, amount))
							{
								int[] data = { id, amount };
								replace_ids_amounts(data);
								selected_replace((make_selected(prod_ctr.get_product_by_id(id), amount)));
								refresh_table(make_filling(selected));
								display_prices();
							}
							else
							{
								JOptionPane.showMessageDialog(this, "Amount too high.\nNot enough product in the stock.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(this, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	@Override
	protected void add()
	{
		if(!is_open)
		{
			AddProduct a = new AddProduct();
			
			a.setVisible(true);
			is_open = true;
		}
	}
		


	@Override
	protected void delete()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				remove_from_selected_and_ids((int)table.getModel().getValueAt(row_index, 0));
				refresh_table(make_filling(selected));
			}
		}

	}

	@Override
	protected void cancel()
	{
		this.dispose();
	}

	
	private void make_error_bg(JTextField field)
	{
		field.setBackground(Color.red);
		field.setText("Not Valid");
	}
	
	private void make_normal_bg(JTextField field)
	{
		if(field.getBackground().equals(Color.red))
		{
			field.setBackground(Color.white);
			field.setText("");
		}
	}
	
	private Object[] make_selected(Object[] prod_data, int amount)
	{
		Object[] data = new Object[6];
		data[0] = prod_data[0];
		data[1] = prod_data[1];
		data[2] = prod_data[2];
		data[3] = prod_data[4];
		data[4] = amount;
		data[5] = (float)prod_data[4]*amount;
		return data;
	}
	
	private Object[][] make_filling(ArrayList<Object[]> data)
	{
		Object[][] info = new Object[data.size()][];
		int i = 0;
		for (Object[] arr : data)
		{
			info[i] = arr;
			i++;
		}
		return info;
	}
	
	private void selected_replace(Object[] data)
	{
		for (Object[] info : selected)
		{
			if (data[0] == info[0])
			{
				selected.remove(info);
				
				break;
			}
		}
		selected.add(data);
	}
	
	private void replace_ids_amounts(int[] data)
	{
		for(int[] i_a : ids_amounts)
		{
			if (i_a[0] == data[0])
			{
				ids_amounts.remove(i_a);
				break;
			}
		}
		ids_amounts.add(data);
	}
	
	private void remove_from_selected_and_ids(int id)
	{

		for (int[] i : ids_amounts)
		{
			if(i[0] == id)
			{
				ids_amounts.remove(i);
				break;
			}
		}
		for (Object[] i : selected)
		{
			if((int)i[0] == id)
			{
				selected.remove(i);
				break;
			}
		}
		
		
	}
	
	private void display_prices()
	{
		String c_id = c_id_tf.getText();
		if(is_number(c_id))
		{
			int cust_id = Integer.parseInt(c_id);
			if(cust_ctr.customer_exists(cust_id))
			{
				String begin_date = b_d_tf.getText();
				String return_date = r_d_tf.getText();
				if(Utilities.valid_date(begin_date) && Utilities.valid_date(return_date) && Utilities.day_difference(begin_date, return_date) >= 0)
				{
					prices = rent_ctr.calc_rent_price(cust_id, ids_amounts, chckbxPaymentOnDelivery.isSelected(), delivery, begin_date, return_date);
					
				}
				else
				{
					prices[0] = 0;
					prices[1] = 0;
				}
			}
			else
			{
				prices[0] = 0;
				prices[1] = 0;
			}
			price_tf.setText(String.valueOf(prices[0]));
			if (delivery)
			{
				d_c_tf.setText(String.valueOf(prices[1]));
				
			}
		}
	}
	

	
	class AddProduct extends Adder
	{
		private String[] column_names = {"ID", "Name", "Daily price", "Amount"};
		private Object[][] filling;
		public AddProduct()
		{
			super(false);

			this.addInternalFrameListener(new InternalFrameListener() {

				public void internalFrameClosing(InternalFrameEvent arg0) {
					is_open=false;
				}

				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameDeiconified(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameIconified(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameOpened(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			refresh_small(prod_ctr.get_non_deleted_products());

		}
		
		private void refresh_small(Object[][] data)
		{
			filling = new Object[data.length][4];
			for(int i = 0; i< data.length; i++)
			{
				filling[i][0] = data[i][0];
				filling[i][1] = data[i][1];
				filling[i][2] = data[i][4];
				filling[i][3] = data[i][5];
			}
			fill_table(filling, column_names);
		}
		@Override
		protected void confirm()
		{
			is_open = false;
			this.dispose();
		}
		
		@Override
		protected void add_product()
		{
			int row_index = add_table.getSelectedRow();
			if (row_index != -1)
			{
				if( add_table.getModel().getValueAt(row_index, 0) != null)
				{
					int id = (int)add_table.getModel().getValueAt(row_index, 0);
					String quantity = JOptionPane
							.showInputDialog("Please input the amount of the product");
					if (is_number(quantity))
					{
						int amount =Integer.parseInt(quantity);
						if (amount > 0)
						{
							if (prod_ctr.is_such_amount(id, amount))
							{
								int[] data = { id, amount };
								replace_ids_amounts(data);
								selected_replace((make_selected(prod_ctr.get_product_by_id(id), amount)));
								refresh_table(make_filling(selected));
								display_prices();
							}
							else
							{
								JOptionPane.showMessageDialog(this, "Amount too high.\nNot enough product in the stock.", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(this, "Amount cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Amount must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		
		@Override
		protected void search()
		{
			
			String num = field_search.getText();
			if (is_number(num))
			{
				Object[] data = null;
				int id = Integer.parseInt(num);
				if (prod_ctr.product_exists(id))
				{
				filling = new Object[1][4];
				data = prod_ctr.get_product_by_id(id);
				filling[0][0] = data[0];
				filling[0][1] = data[1];
				filling[0][2] = data[4];
				filling[0][3] = data[5];
				}
			}
			else
			{
				Object data[][] = null;
				data = prod_ctr.get_products_by_name(num);
				if (data != null)
				{
					filling = new Object[data.length][4];
					for(int i = 0; i < data.length; i ++)
					{
						filling[i][0] = data[i][0];
						filling[i][1] = data[i][1];
						filling[i][2] = data[i][4];
						filling[i][3] = data[i][5];
					}
				}
			}
			fill_table(filling, column_names);
		}
		
		@Override
		protected void clear()
		{
			refresh_small(prod_ctr.get_non_deleted_products());
		}
		
		
	}
	
	class AddCustomer extends Adder
	{
		private String[] column_names = { "ID", "Name", "Address", "City", "Price qualified for free shipment" };
		private Object[][] filling;
		public AddCustomer()
		{
			super(true);

			this.addInternalFrameListener(new InternalFrameListener() {

				public void internalFrameClosing(InternalFrameEvent arg0) {
					is_open=false;
				}

				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameDeiconified(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameIconified(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void internalFrameOpened(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			refresh_small(cust_ctr.get_non_deleted_customers());
		}
		
		
		private void refresh_small(Object[][] data)
		{
			filling = new Object[data.length][5];
			for(int i = 0; i< data.length; i++)
			{
				filling[i][0] = data[i][0];
				filling[i][1] = data[i][1];
				filling[i][2] = data[i][2];
				filling[i][3] = data[i][4];
				filling[i][4] = data[i][11];
			}
			fill_table(filling, column_names);
		}
		
		@Override
		protected void select_customer()
		{
			int row_index = add_table.getSelectedRow();
			if (row_index != -1)
			{
				if( add_table.getModel().getValueAt(row_index, 0) != null)
				{
					c_id_tf.setText(String.valueOf(add_table.getModel().getValueAt(row_index, 0)));
					c_n_tf.setText(String.valueOf(add_table.getModel().getValueAt(row_index, 1)));
					is_open = false;
					this.dispose();
				}
			}
		}
		@Override
		protected void confirm()
		{
			select_customer();
		}
		
		@Override
		protected void search()
		{
			filling = null;
			String num = field_search.getText();
			if (is_number(num))
			{
				Object[] data = null;
				int id = Integer.parseInt(num);
				if (cust_ctr.customer_exists(id))
				{
					filling = new Object[1][5];
					data = cust_ctr.get_customer_by_id(id);
					filling[0][0] = data[0];
					filling[0][1] = data[1];
					filling[0][2] = data[2];
					filling[0][3] = data[4];
					filling[0][4] = data[11];
				}
			}
			else
			{
				Object data[][] = null;
				data = cust_ctr.get_customer_by_name(num);
				if (data != null)
				{
					filling = new Object[data.length][5];
					for(int i = 0; i< data.length; i++)
					{
						filling[i][0] = data[i][0];
						filling[i][1] = data[i][1];
						filling[i][2] = data[i][2];
						filling[i][3] = data[i][4];
						filling[i][4] = data[i][11];
					}
				}
			}
			fill_table(filling, column_names);
		}
		
		@Override
		protected void clear()
		{
			refresh_small(cust_ctr.get_non_deleted_customers());
		}
	}
	
}
