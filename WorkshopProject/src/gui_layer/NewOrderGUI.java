package gui_layer;


import ctr_layer.OrderCtr;
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

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import ctr_layer.OrderCtr;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;

public class NewOrderGUI extends NewOrderRentSuperGUI
{
	
	private final String[] column_names = { "Product ID", "Product name", "Product retail price", "Product price", "Amount", "Total price" };
	private Object[][] filling;
	private OrderCtr ord_ctr;
	private SupplyLineCtr prod_ctr;
	private JTextField id_tf;
	private JTextField p_d_tf;
	private JTextField price_tf;
	private JTextField i_n_tf;
	private JTextField d_d_tf;
	private JTextField d_c_tf;
	private JTextField c_id_tf;
	private JTextField c_n_tf;
	private JCheckBox completed_box;
	private JCheckBox chckbxPaymentOnDelivery;
	private JCheckBox chckbxDelivered;
	private JCheckBox chckbxDelivery;
	private ArrayList<Object[]> selected;
	private boolean delivery;
	private float[] prices;
	private LinkedList<int[]> ids_amounts;
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					NewOrderGUI frame = new NewOrderGUI(true, 1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NewOrderGUI(boolean i_creating, int i_id)
	{
		super(i_creating, i_id);
		delivery = false;
		ord_ctr = new OrderCtr();
		prod_ctr = new SupplyLineCtr();
		ids_amounts = new LinkedList<int[]>();
		selected = new ArrayList<Object[]>();
		prices = new float[2];
		make_data_display();
		prepare_gui();
		if(!creating)
		{
			refresh_table(ord_ctr.get_order_items(id));
		}
		
		// TODO Auto-generated constructor stub
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
		
		JLabel p_d_lbl = new JLabel("Payment date");
		panel_2.add(p_d_lbl);
		
		p_d_tf = new JTextField();
		panel_2.add(p_d_tf);
		p_d_tf.setColumns(10);
		
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel price_lbl = new JLabel("Price");
		panel_3.add(price_lbl);
		
		price_tf = new JTextField();
		panel_3.add(price_tf);
		price_tf.setColumns(10);
		
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel i_n_lbl = new JLabel("Invoice number");
		panel_4.add(i_n_lbl);
		
		i_n_tf = new JTextField();
		i_n_tf.setEnabled(false);
		panel_4.add(i_n_tf);
		i_n_tf.setColumns(10);
		
		
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
		
		d_d_tf = new JTextField();
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
		
		price_tf.setEnabled(false);
		d_c_tf.setEnabled(false);
		
		
	}
	
	private void prepare_gui()
	{
		if(!creating)
		{
			p_d_tf.setEnabled(false);
			c_id_tf.setEnabled(false);
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
			i_n_tf.setText("Invoice nr");
			
			p_d_tf.setText(Utilities.convert_date_to_string(new Date()));
			p_d_tf.addFocusListener(new FocusAdapter()
			{
				public void focusGained(FocusEvent arg0)
				{
					make_normal_bg(p_d_tf);
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
			});
			chckbxDelivered.setVisible(false);
			chckbxPaymentOnDelivery.setEnabled(false);
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
		Object[] data = ord_ctr.get_order(id);
		if(data != null)
		{
			id_tf.setText(String.valueOf(data[0]));
			p_d_tf.setText(String.valueOf(data[1]));
			price_tf.setText(String.valueOf(data[2]));
			i_n_tf.setText(String.valueOf(data[3]));
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
		String payment_date = p_d_tf.getText();
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
		if(!Utilities.valid_date(payment_date))
		{
			correct = false;
			make_error_bg(p_d_tf);
		}
		
		boolean complete = false;
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
				ord_ctr.add_order_with_del(cust_id, pay_on_delivery, delivery_date, payment_date, ids_amounts, complete);
			}
		}
		else if(correct)
		{
			if (payment_date.equals(Utilities.convert_date_to_string(new Date())))
			{
				complete = true;
			}
			ord_ctr.add_order_without_del(cust_id, payment_date, ids_amounts, complete);
			
		}
			

	}

	@Override
	protected void edit()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void add()
	{
		AddProduct a = new AddProduct();
		a.setVisible(true);
		
		String c_id = c_id_tf.getText();
		if(is_number(c_id))
		{
			int cust_id = Integer.parseInt(c_id);
			if(!cust_ctr.customer_exists(cust_id))
			{
				prices = ord_ctr.calc_order_price(cust_id, ids_amounts, chckbxPaymentOnDelivery.isSelected(), delivery);
			}
		}
	}
		


	@Override
	protected void delete()
	{
		// TODO Auto-generated method stub

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
	
	class AddProduct extends ProductAdder
	{
		private String[] column_names = {"ID", "Name", "Price", "Amount"};
		private Object[][] filling;
		public AddProduct()
		{
			super();
			refresh_table(prod_ctr.get_non_deleted_products());
		}
		
		private void refresh_table(Object[][] data)
		{
			filling = new Object[data.length][4];
			for(int i = 0; i< data.length; i++)
			{
				filling[i][0] = data[i][0];
				filling[i][1] = data[i][1];
				filling[i][2] = data[i][3];
				filling[i][3] = data[i][5];
			}
			fill_table(filling, column_names);
		}
	}

	/*class AddProductUI extends JInternalFrame
	{
		private JTextField search_tf;
		private JTable product_table;

		private String[] column_names = { "Art. number", "Name", "Quantity",
				"Category", "Amount qualified for discount", "Location",
				"Price" };

		public AddProductUI()
		{
			super("Add product", true, true, true, true);
			setBounds(100, 100, 450, 300);

			make_search();
			make_ctr_btns();
			make_table();

		}

		private void make_ctr_btns()
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setLayout(new BorderLayout(0, 0));

			JButton add_btn = new JButton("Add");
			add_btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					add();
				}
			});
			panel.add(add_btn, BorderLayout.WEST);

			JButton cancel_btn = new JButton("Cancel");
			cancel_btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
			panel.add(cancel_btn, BorderLayout.EAST);
		}

		private void make_search()
		{
			JPanel panel_1 = new JPanel();
			getContentPane().add(panel_1, BorderLayout.NORTH);
			panel_1.setLayout(new BorderLayout(0, 0));
			JPanel panel_2 = new JPanel();
			panel_1.add(panel_2, BorderLayout.WEST);
			panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JLabel search_lbl = new JLabel("Name/ID");
			panel_2.add(search_lbl);

			search_tf = new JTextField();
			panel_2.add(search_tf);
			search_tf.setColumns(10);

			JButton search_btn = new JButton("Search");
			search_btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					search();
				}
			});
			panel_2.add(search_btn);
			JButton clear_btn = new JButton("Clear");
			clear_btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					search_tf.setText("");
					refresh_inner();
				}
			});
			panel_2.add(clear_btn);
		}

		private void make_table()
		{
			JScrollPane scrollPane = new JScrollPane();
			getContentPane().add(scrollPane, BorderLayout.CENTER);

			final Object[][] filling = prod_ctr.get_all_products();

			product_table = new JTable();

			product_table.setFillsViewportHeight(true);
			product_table.setModel(new MyTableModel(filling, column_names));

			product_table.setPreferredScrollableViewportSize(new Dimension(500,
					70));
			product_table.setAutoCreateRowSorter(true);
			product_table.setRowSelectionAllowed(true);
			product_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			product_table.getColumnModel().getColumn(4).setMinWidth(160);
			product_table.getTableHeader().setReorderingAllowed(false);
			product_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			product_table.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 2)
					{
						add();
					}
				}
			});

			DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
			right_renderer.setHorizontalAlignment(JLabel.RIGHT);
			product_table.setDefaultRenderer(String.class, right_renderer);

			JScrollPane scroll_pane = new JScrollPane(product_table);
			scroll_pane.setBorder(new EmptyBorder(5, 10, 5, 10));
			getContentPane().add(scroll_pane, BorderLayout.CENTER);
		}

		private void add()
		{
			Object[] data = new Object[2];
			boolean ok = true;
			int row_index = product_table.getSelectedRow();
			if (row_index != -1)
			{
				if( product_table.getModel().getValueAt(row_index, 0) != null)
				{
					data[0] = String.valueOf(product_table.getModel().getValueAt(
							row_index, 0));
	
					for (Object[] position : selected)
					{
						if (position[0].equals(data[0]))
						{
							selected.remove(position);
							break;
						}
					}
					if (ok)
					{
						String quantity = JOptionPane
								.showInputDialog("Please input the amount of the product");
						if (is_numeric(quantity))
						{
							int amount =Integer.parseInt(quantity);
							if (amount > 0)
							{
								if (prod_ctr.is_such_amount((int) data[0], amount))
								{
									data[1] = Integer.parseInt(quantity);
									selected.add(data);
									refresh_inner();
									refresh_table(selected);
									//dispose();
		
								}
								else
								{
									JOptionPane.showMessageDialog(
													this,
													"Couldn't add a product.\nAmount higher than available quantity.",
													"Error",
													JOptionPane.INFORMATION_MESSAGE);
								}
							}
							else
							{
								JOptionPane.showMessageDialog(
												this,
												"Couldn't add a product.\nAmount must be greater than 0.",
												"Error",
												JOptionPane.INFORMATION_MESSAGE);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(
											this,
											"Couldn't add a product.\nAmount typed incorrectly.",
											"Error",
											JOptionPane.INFORMATION_MESSAGE);
						}

					}
				}
			}
		}

		private void search()
		{
			Object[][] filling = new Object[1][6];
			String contents = search_tf.getText();
			if (is_numeric(contents))
			{
				filling[0] = stock_ctr.find_product_by_art_nr(contents, false,
						false);
			}
			else
			{
				filling[0] = stock_ctr.find_product_by_name(contents, false,
						false);
			}
			product_table.setModel(new MyTableModel(filling, column_names));
		}
		
		private void refresh_inner()
		{
			Object[][] filling = stock_ctr.display_all_products();
			product_table.setModel(new MyTableModel(filling, column_names));
			product_table.getColumnModel().getColumn(4).setMinWidth(180);
		}

		private boolean is_numeric(String checker)
		{
			try
			{
				int i = Integer.parseInt(checker);
			}
			catch (NumberFormatException nfe)
			{
				return false;
			}
			return true;
		}

	}*/

}
