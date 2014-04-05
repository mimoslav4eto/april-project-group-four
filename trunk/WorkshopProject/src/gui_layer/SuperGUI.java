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

import java.awt.ScrollPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;

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
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SpringLayout;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;

public class SuperGUI extends JFrame {

	protected JPanel contentPane;
	protected JTextField field_search;
	private JPanel panel_2;
	private JPanel search_panel;
	protected JPanel button_panel;
	private JPanel panel_1; 
	private JScrollPane scrollPane;
	protected JTable table;
	private Object[] column_names = {};
	private Object[][] filling;

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
	public SuperGUI() {
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
		
		JButton btn_create = new JButton("New");
		btn_create.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg1)
			{
				create();
			}
		});
		panel_2.add(btn_create);
		
		JButton btn_edit = new JButton("Edit");
		btn_edit.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg2)
			{
				create_edit();
			}
		});
		panel_2.add(btn_edit);
		
		JButton btn_delete = new JButton("Delete");
		btn_delete.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg4)
			{
				delete();
			}
		});
		panel_2.add(btn_delete);
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
		DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
		right_renderer.setHorizontalAlignment(JLabel.LEFT);
		table.setDefaultRenderer(String.class, right_renderer);
		
		scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));
		
	}
	
	protected void fill_table(Object[][] fill, Object[] columns)
	{
		filling = fill;
		column_names=columns;
		table.setModel(new DefaultTableModel(filling, column_names));
	}
	
	protected void search()
	{
		
	}
	protected void clear()
	{
		
	}
	protected void create()
	{
		
	}
	protected void view()
	{
		
	}
	protected void edit()
	{
		
	}
	protected void delete()
	{
		
	}
	protected boolean is_number(String temp)
	{
		try
		{
			int i = Integer.parseInt(temp);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
	protected void create_edit()
	{
		
	}
}
