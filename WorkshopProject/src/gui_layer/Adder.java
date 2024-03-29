package gui_layer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.ListSelectionModel;

import ctr_layer.SupplyLineCtr;

import java.awt.FlowLayout;
import java.util.ArrayList;

public class Adder extends JInternalFrame {

	protected JPanel contentPane;
	protected JTextField field_search;
	private JPanel panel_2;
	private JPanel search_panel;
	protected JPanel button_panel;
	private JPanel panel_1; 
	private JScrollPane scrollPane;
	protected JTable add_table;
	private String[] column_names;
	private Object[][] filling;
	private JPanel panel;
	private boolean customer;

	/**
	 * Create the frame.
	 */
	public Adder(boolean i_customer) 
	{
		super("", true, true, true, true);
		customer = i_customer;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		search_panel = new JPanel();
		contentPane.add(search_panel, BorderLayout.NORTH);
		search_panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		search_panel.add(panel_1, BorderLayout.WEST);
		
		button_panel = new JPanel();
		contentPane.add(button_panel, BorderLayout.SOUTH);
		button_panel.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		button_panel.add(panel_2, BorderLayout.EAST);
		
		JButton btnOk = new JButton("Ok");
		panel_2.add(btnOk);
		btnOk.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg4)
			{
				confirm();
			}
		});
		
		panel = new JPanel();
		button_panel.add(panel, BorderLayout.WEST);
		
		
		filling = new Object[0][0];
		make_buttons();
		make_table();
	}
	
	private void make_buttons()
	{
		JButton btn_search = new JButton("Search");
		btn_search.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				search();
			}
		});
		
		field_search = new JTextField();
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
		field_search.setColumns(10);
		field_search.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent arg0)
			{
				if ((field_search.getText().equals("Name/ID"))&&(field_search.getForeground()!=Color.black))
				{
					field_search.setText("");
					field_search.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (field_search.getText().equals(""))
				{
					field_search.setText("Name/ID");
					field_search.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_1.add(field_search);
		panel_1.add(btn_search);
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				clear();
			}
		});
		panel_1.add(btn_clear);
		if(!customer)
		{
			JButton btn_add = new JButton("Add");
			panel.add(btn_add);
			btn_add.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg1)
				{
					add_product();
				}
			});
		}
	}
	
	private void make_table()
	{
		add_table = new JTable()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		add_table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add_table.setFillsViewportHeight(true);
		add_table.setModel(new DefaultTableModel(filling, column_names));
		add_table.setAutoCreateRowSorter(true);
		add_table.setRowSelectionAllowed(true);
		add_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add_table.getTableHeader().setReorderingAllowed(false);
		add_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		add_table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					if(!customer)
					{
						add_product();
					}
					else
					{
						select_customer();
					}
				}
			}
		});

		DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
		right_renderer.setHorizontalAlignment(JLabel.LEFT);
		add_table.setDefaultRenderer(String.class, right_renderer);
		
		scrollPane = new JScrollPane(add_table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));
		
	}
	
	protected void fill_table(Object[][] fill, String[] columns)
	{
		filling = fill;
		column_names=columns;
		add_table.setModel(new MyTableModel(filling, column_names));
	}
	
	protected void search()
	{
		
	}
	protected void clear()
	{
		
	}
	
	protected void confirm()
	{
		this.dispose();
	}
	protected void add_product()
	{
		
	}
	
	protected void select_customer()
	{
		
	}

	protected boolean is_number(String temp)
	{
		try
		{
			Integer.parseInt(temp);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

}
