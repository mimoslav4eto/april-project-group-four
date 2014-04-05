package gui_layer;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ctr_layer.OrderCtr;

public class OrderGUI extends SuperGUI
{
	private String[] column_names = { "ID", "Payment date", "Price", "Invoice nr", "Customer name", "Completed" };
	private Object[][] filling;
	protected OrderCtr ord_ctr;
	private boolean is_opened;
	private Dimension dim;
	
	
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
					edit();
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
		ord_ctr=new OrderCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
//		make_manager();
		Object[][] data = ord_ctr.get_all_orders();
		refresh_table(data);
		
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
				filling[i][4] = data[i][7];
				filling[i][5] = data[i][8];
			}
			fill_table(filling, column_names);
		}
		else 
		{
			fill_table(new Object[0][0], column_names);
		}
	}

}
