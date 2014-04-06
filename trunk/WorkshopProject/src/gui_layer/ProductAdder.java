package gui_layer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
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

public class ProductAdder extends JFrame {

	protected JPanel contentPane;
	protected JTextField field_search;
	protected SupplyLineCtr prod_ctr;
	private JPanel panel_2;
	private JPanel search_panel;
	protected JPanel button_panel;
	private JPanel panel_1; 
	private JScrollPane scrollPane;
	protected JTable table;
	private String[] column_names;
	private Object[][] filling;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SuperGUI frame = new SuperGUI();
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
	public ProductAdder() {
		prod_ctr = new SupplyLineCtr();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 387);
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
		
		JButton btn_add = new JButton("Add");
		btn_add.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg1)
			{
				add_product();
			}
		});
		panel_2.add(btn_add);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg4)
			{
				cancel();
			}
		});
		panel_2.add(btn_cancel);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg4)
			{
				confirm();
			}
		});
		panel.add(btnOk);
	}
	
	private void make_table()
	{
		table = new JTable()
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(filling, column_names));
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					add_product();
				}
			}
		});

		DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
		right_renderer.setHorizontalAlignment(JLabel.LEFT);
		table.setDefaultRenderer(String.class, right_renderer);
		
		scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));
		
	}
	
	protected void fill_table(Object[][] fill, String[] columns)
	{
		filling = fill;
		column_names=columns;
		table.setModel(new MyTableModel(filling, column_names));
	}
	
	protected void search()
	{
		
	}
	protected void clear()
	{
		
	}
	
	protected void confirm()
	{
		
	}
	protected void add_product()
	{
		
	}
	protected void cancel()
	{
		this.dispose();
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
