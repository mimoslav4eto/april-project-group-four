package gui_layer;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel
{
	private String[] column_names;
	private Object[][] filling;

	public MyTableModel(Object[][] filling, String[] column_names)
	{
		super(filling, column_names);

	}

	public Class<?> getColumnClass(int c)
	{
		if (getRowCount() == 0 ||getValueAt(0, c) == null)
		{
			return String.class;
		}
		else
		{
			return getValueAt(0, c).getClass();
		}
	}

	public boolean isCellEditable(int row, int col)
	{
		return false;
	}
}