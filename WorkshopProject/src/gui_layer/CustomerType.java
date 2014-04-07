package gui_layer;
import ctr_layer.CustomerCtr;

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

public class CustomerType extends SuperGUI {
	private String[] column_names={"id", "Price for Discount", "Price for free shipping", "Discount Price"};
	private Object[][] filling;
	protected CustomerCtr types;
	private JPanel panel;
	private JTextField tf_disc_perc;
	private JTextField tf_id;
	private JTextField tf_disc_price;
	private JTextField tf_free_ship;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private Dimension dim;
	private JPanel panel_5;
	private JButton btn_ok;

	/**
	 * Create the frame.
	 */
	public CustomerType() 
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
					edit();
				}
				else if (e.getClickCount() == 1)
				{
					view();
				}
			}
		});
		setTitle("Customer Types");
		setSize(730, 513);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		types=new CustomerCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		make_manager();
		Object[][] temp=types.get_all_customer_types();
		refill_table(temp);
		btn_delete.setVisible(false);
	}
	private void refill_table(Object[][] temp)
	{
		if (temp!=null)
		{
			fill_table(temp,column_names);
		}
		else 
		{
			fill_table(new Object[0][0],column_names);
		}
	}
	
	private void make_manager()
	{
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Customer Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tf_id = new JTextField("ID");
		tf_id.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(tf_id);
		tf_id.setColumns(20);
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setEnabled(false);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		tf_disc_price = new JTextField();
		tf_disc_price.setHorizontalAlignment(SwingConstants.CENTER);
		tf_disc_price.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if (((tf_disc_price.getText().equals("Price for Discount"))&&(tf_disc_price.getForeground()!=Color.black))||(tf_disc_price.getText().equals("Not Valid")))
				{
					tf_disc_price.setText("");
					tf_disc_price.setForeground(Color.black);
					tf_disc_price.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_disc_price.getText().isEmpty())
				{
					tf_disc_price.setText("Price for Discount");
					tf_disc_price.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_2.add(tf_disc_price);
		tf_disc_price.setColumns(20);
		tf_disc_price.setText("Price for Discount");
		tf_disc_price.setForeground(Color.LIGHT_GRAY);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		
		tf_free_ship = new JTextField();
		tf_free_ship.setHorizontalAlignment(SwingConstants.CENTER);
		tf_free_ship.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_free_ship.getText().equals("Price for Free Shipping"))||(tf_free_ship.getText().equals("Not valid")))
				{
					tf_free_ship.setText("");
					tf_free_ship.setForeground(Color.black);
					tf_free_ship.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_free_ship.getText().isEmpty())
				{
					tf_free_ship.setText("Price for Free Shipping");
					tf_free_ship.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_3.add(tf_free_ship);
		tf_free_ship.setColumns(20);
		tf_free_ship.setText("Price for Free Shipping");
		tf_free_ship.setForeground(Color.LIGHT_GRAY);
		
		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		
		tf_disc_perc = new JTextField();
		tf_disc_perc.setHorizontalAlignment(SwingConstants.CENTER);
		tf_disc_perc.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_disc_perc.getText().equals("Discount %"))||(tf_disc_perc.getText().equals("Not valid")))
				{
					tf_disc_perc.setText("");
					tf_disc_perc.setForeground(Color.black);
					tf_disc_perc.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_disc_perc.getText().isEmpty())
				{
					tf_disc_perc.setText("Discount %");
					tf_disc_perc.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_4.add(tf_disc_perc);
		tf_disc_perc.setColumns(20);
		tf_disc_perc.setText("Discount %");
		tf_disc_perc.setForeground(Color.LIGHT_GRAY);
		
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
				if (tf_disc_price.isEnabled())
				{
					create_edit();
				}
			}
		});
	}
	
	private void clear_fields()
	{
		tf_id.setText("ID");
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setBackground(Color.white);
		
		tf_disc_price.setText("Price for Discount");
		tf_disc_price.setForeground(Color.LIGHT_GRAY);
		tf_disc_price.setEnabled(true);
		tf_disc_price.setBackground(Color.white);
		
		tf_disc_perc.setText("Price for Free Shipping");
		tf_disc_perc.setForeground(Color.LIGHT_GRAY);
		tf_disc_perc.setEnabled(true);
		tf_disc_perc.setBackground(Color.white);
		
		tf_free_ship.setText("Discount %");
		tf_free_ship.setForeground(Color.LIGHT_GRAY);
		tf_free_ship.setEnabled(true);
		tf_free_ship.setBackground(Color.white);

	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (types.customer_type_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][3];
			temp[0] = types.get_customer_type(Integer.parseInt(info));
			}
		}
		refill_table(temp);
	}
	
	protected void clear()
	{
		clear_fields();
		filling=types.get_all_customer_types();
		refill_table(filling);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	protected void create()
	{
		clear_fields();
	}
	
	protected void create_edit()
	{
		if ((is_ready())&&(tf_disc_price.isEnabled()))
		{
			int id=-1;
			try
			{
				id = Integer.parseInt(tf_id.getText());
			}
			catch (Exception e)
			{
				System.out.println("Error while parsing id for editing: " + e);
			}
			float disc_price = Float.parseFloat(tf_disc_price.getText());
			float free_ship = Float.parseFloat(tf_free_ship.getText());
			float disc_perc = Float.parseFloat(tf_disc_perc.getText());
			if (id==-1)
			{
				types.add_customer_type(disc_price, free_ship, disc_perc);
				JOptionPane.showMessageDialog(this, "Successully created a customer.", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				types.update_customer_type(id, disc_price, free_ship, disc_perc);
				JOptionPane.showMessageDialog(this, "Successully edited a customer.", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			clear();
			
		}
	}
	private boolean is_ready()
	{
		boolean is_ready=true;
		if ((tf_disc_price.getText().equals("Price for Discount"))||(tf_disc_price.getText().equals(""))||(!is_float(tf_disc_price.getText())))
		{
			tf_disc_price.setBackground(Color.red);
			tf_disc_price.setText("Not Valid");
			is_ready=false;
		}
		if ((tf_free_ship.getText().equals("Price for Free Shipping"))||(tf_free_ship.getText().equals(""))||(!is_float(tf_free_ship.getText())))
		{
			tf_free_ship.setBackground(Color.red);
			tf_free_ship.setText("Not valid");
			is_ready=false;
		}
		if ((tf_disc_perc.getText().equals("Discount %"))||(tf_disc_perc.getText().equals(""))||(!is_float(tf_disc_perc.getText())))
		{
			tf_disc_perc.setBackground(Color.red);
			tf_disc_perc.setText("Not valid");
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
			fill_fields(types.get_customer_type(t_id),false);
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
				Object[] temp=types.get_customer_type(t_id);
				fill_fields(temp,true);
			}
		}
	}
	private void fill_fields(Object[] fill,boolean is_edit)
	{
		tf_id.setText(String.valueOf(fill[0]));
		tf_disc_price.setText(String.valueOf(fill[1]));
		tf_free_ship.setText(String.valueOf(fill[2]));
		tf_disc_perc.setText(String.valueOf(fill[3]));
		if (!is_edit)
		{
			tf_disc_price.setEnabled(false);
			tf_free_ship.setEnabled(false);
			tf_disc_perc.setEnabled(false);
		}
		else
		{
			tf_id.setForeground(Color.black);
			tf_disc_price.setEnabled(true);
			tf_disc_price.setForeground(Color.black);
			tf_free_ship.setEnabled(true);
			tf_free_ship.setForeground(Color.black);
			tf_disc_perc.setEnabled(true);
			tf_disc_perc.setForeground(Color.black);
		}
	}

}
