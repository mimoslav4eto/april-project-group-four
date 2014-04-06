package gui_layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class OrderGUI extends OrderRentSuperGUI
{
	private final String[] column_names = { "ID", "Payment date", "Price", "Invoice nr", "Customer name", "Completed" };
	private Object[][] filling;
	protected OrderCtr ord_ctr;
	private boolean is_opened;
	private Dimension dim;
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
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_11;
	private JPanel panel_12;
	private JPanel panel_13;
	
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					OrderGUI frame = new OrderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OrderGUI()
	{
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
					details();
				}
				else if (e.getClickCount() == 1)
				{
					view();
				}
			}
		});
		setTitle("Orders");
		setSize(730, 513);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		is_opened=false;
		state = 1;
		ord_ctr=new OrderCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		make_data_display();
		set_btn_names();
//		make_manager();
		Object[][] data = ord_ctr.get_all_orders();
		refresh_table(data);
		
	}
	
	private void set_btn_names()
	{
		all_rdb.setText("All orders");
		complete_rdb.setText("Completed orders");
		incomplete_rdb.setText("Incomplete orders");
		all_rdb.setSelected(true);
	}
	
	private void make_data_display()
	{
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EmptyBorder(5, 1, 2, 3), "Order Data", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		getContentPane().add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{172, 0};
		gbl_panel.rowHeights = new int[]{18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 37, 0};
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
		panel_1.add(id_tf);
		id_tf.setColumns(10);
		
		id_tf.setEnabled(false);
		
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
		p_d_tf.setEnabled(false);
		
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
		price_tf.setEnabled(false);
		
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
		panel_4.add(i_n_tf);
		i_n_tf.setColumns(10);
		i_n_tf.setEnabled(false);
		
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
		c_id_tf.setEnabled(false);
		
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
		c_n_tf.setEnabled(false);
		
		panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.insets = new Insets(0, 0, 5, 0);
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 6;
		panel.add(panel_7, gbc_panel_7);
		panel_7.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		JLabel d_d_lbl = new JLabel("Delivery date");
		panel_7.add(d_d_lbl);
		
		d_d_tf = new JTextField();
		panel_7.add(d_d_tf);
		d_d_tf.setColumns(10);
		d_d_tf.setEnabled(false);
		
		
		
		panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 7;
		panel.add(panel_8, gbc_panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel d_c_lbl = new JLabel("Delivery cost");
		panel_8.add(d_c_lbl);
		
		d_c_tf = new JTextField();
		panel_8.add(d_c_tf);
		d_c_tf.setColumns(10);
		d_c_tf.setEnabled(false);
		
		panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 8;
		panel.add(panel_11, gbc_panel_11);
		panel_11.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxPaymentOnDelivery = new JCheckBox("Payment on delivery");
		chckbxPaymentOnDelivery.setEnabled(false);
		panel_11.add(chckbxPaymentOnDelivery);
		
		panel_12 = new JPanel();
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 9;
		panel.add(panel_12, gbc_panel_12);
		panel_12.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxDelivered = new JCheckBox("Delivered");
		chckbxDelivered.setEnabled(false);
		panel_12.add(chckbxDelivered);
		
		panel_13 = new JPanel();
		GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.anchor = GridBagConstraints.WEST;
		gbc_panel_13.fill = GridBagConstraints.VERTICAL;
		gbc_panel_13.insets = new Insets(0, 0, 5, 0);
		gbc_panel_13.gridx = 0;
		gbc_panel_13.gridy = 10;
		panel.add(panel_13, gbc_panel_13);
		
		JButton btnFinishDelivery = new JButton("Finish delivery");
		btnFinishDelivery.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				finish_delivery();
			}
		});
		panel_13.setLayout(new GridLayout(0, 1, 0, 0));
		panel_13.add(btnFinishDelivery);
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.anchor = GridBagConstraints.SOUTHWEST;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 11;
		panel.add(panel_9, gbc_panel_9);
		panel_9.setLayout(new GridLayout(0, 1, 0, 0));
		
		completed_box = new JCheckBox("Completed");
		panel_9.add(completed_box);
		completed_box.setEnabled(false);
		
		btn_complete.setVisible(false);
		panel_13.setVisible(false);
	}
	
	private void fill_fields(Object[] data)
	{
		
		id_tf.setText(denullate(data[0]));
		p_d_tf.setText(denullate(data[1]));
		price_tf.setText(denullate(data[2]));
		i_n_tf.setText(denullate(data[3]));
		c_id_tf.setText(denullate(data[4]));
		c_n_tf.setText(denullate(data[5]));
		
		btn_complete.setVisible(false);
		panel_13.setVisible(false);
		
		if(data[6] == null)
		{
			panel_7.setVisible(false);
			panel_8.setVisible(false);
			panel_11.setVisible(false);
			panel_12.setVisible(false);

		}
		else
		{
			panel_7.setVisible(true);
			panel_8.setVisible(true);
			panel_11.setVisible(true);
			panel_12.setVisible(true);
			if((boolean) data[6])
			{
				panel_13.setVisible(true);
			}
			chckbxDelivered.setSelected(!(boolean) data[6]);
			chckbxPaymentOnDelivery.setSelected((boolean) data[7]);
			d_d_tf.setText(denullate(data[8]));
			d_c_tf.setText(denullate(data[9]));
		}
		if(!(boolean)data[10])
		{
			btn_complete.setVisible(true);
		}

		completed_box.setSelected((boolean)data[10]);
	}
	
	@Override
	protected void view()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			int ord_id = (int)table.getModel().getValueAt(row_index, 0);
			fill_fields(ord_ctr.get_order(ord_id));
		}
	}
	
	@Override
	protected void search()
	{
		Object[][] data = null;
		String num = field_search.getText();
		if (is_number(num))
		{
			int id = Integer.parseInt(num);
			if (ord_ctr.order_exists(id))
			{
			data = new Object[1][4];
			data[0] = ord_ctr.get_order(id);
			}
		}
		fill_table(data, column_names);
	}
	
	@Override
	protected void clear()
	{
		filling = decide();
		refresh_table(filling);
		field_search.setText("ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	
	@Override
	protected void finish()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
						"Are you sure you want to set this order as completed?\n"
						+ "If the order has delivery in progress it will be set as complete.",
						"Finish order", JOptionPane.YES_NO_OPTION))
				{
					if(ord_ctr.finish_order((int) table.getModel().getValueAt(row_index, 0)))
					{
						System.out.println("Order successfully completed!");
						view();
						clear();
					}
				}
			}
		}
	}
	
	@Override
	protected void details()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				NewOrderGUI no = new NewOrderGUI(false, (int)table.getModel().getValueAt(row_index, 0));
				no.setVisible(true);
			}
		}
	}
	
	@Override
	protected void create()
	{
		NewOrderGUI no = new NewOrderGUI(true, -1);
		no.setVisible(true);
	}
	
	private void finish_delivery()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
						"Are you sure you want to set this delivery as completed?",
						"Complete delivery", JOptionPane.YES_NO_OPTION))
				{
					if(ord_ctr.update_delivery_status((int) table.getModel().getValueAt(row_index, 0)))
					{
						System.out.println("Delivery successfully completed!");
						view();
					}
				}
			}
		}
	}
	
	private Object[][] decide()
	{
		if (state == 1)
		{
			return ord_ctr.get_all_orders();
		}
		else if (state == 2)
		{
			return ord_ctr.get_complete_orders();
		}
		else
		{
			return ord_ctr.get_incomplete_orders();
		}
	}
	
	
	
	
	private void refresh_table(Object[][] data)
	{
		if (data != null)
		{
			filling = new Object[data.length][6];
			for (int i = 0; i < data.length; i++)
			{
				filling[i][0] = data[i][0];
				filling[i][1] = data[i][1];
				filling[i][2] = data[i][2];
				filling[i][3] = data[i][3];
				filling[i][4] = data[i][5];
				filling[i][5] = data[i][10];
			}
			fill_table(filling, column_names);
		}
		else 
		{
			fill_table(new Object[0][0], column_names);
		}
	}
	
	private String denullate(Object ob)
	{
		try
		{
			return String.valueOf(ob);
		}
		catch(NullPointerException e)
		{
			return "";
		}
	}
	
	
	


}
