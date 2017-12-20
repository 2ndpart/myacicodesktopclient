package com.myacico.ui.internalframe;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import org.jdesktop.swingx.JXDatePicker;

import com.myacico.sql.Database;
import com.myacico.ui.builder.UIBuilder;
import com.myacico.ui.frame.DetailPackagingFrame;

import com.myacico.util.HelperClass;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IFramePackagingViewer extends JInternalFrame {
	private JTextField txtCustID;
	private JTextField txtOrderID;
	private JTable table;
	private String baseSelectQuery = "SELECT user_id as \"USER ID\", transaction_id as \"TRANSACTION ID\",order_number as \"ORDER NUMBER\", shipping_address as \"SHIPPING ADDRESS\", courier as \"KURIR\", transaction_time as \"TRANSACTION TIME\", transaction_status as \"STATUS\" FROM app_transaction WHERE transaction_status = 'PAID' OR transaction_status = 'SHIPPING'";
	private JXDatePicker dtpFrom = new JXDatePicker();
	private JXDatePicker dtpTo = new JXDatePicker();
	/**
	 * Create the frame.
	 */
	public IFramePackagingViewer() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 824, 435);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{230, 0, 46, 86, 0, 46, 0, 86, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label = new JLabel("Customer ID");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		
		txtCustID = new JTextField();
		txtCustID.setColumns(10);
		GridBagConstraints gbc_txtCustID = new GridBagConstraints();
		gbc_txtCustID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCustID.anchor = GridBagConstraints.NORTH;
		gbc_txtCustID.gridwidth = 2;
		gbc_txtCustID.insets = new Insets(0, 0, 5, 5);
		gbc_txtCustID.gridx = 2;
		gbc_txtCustID.gridy = 0;
		panel.add(txtCustID, gbc_txtCustID);
		
		JLabel lblOrderNumber = new JLabel("Order Number");
		GridBagConstraints gbc_lblOrderNumber = new GridBagConstraints();
		gbc_lblOrderNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderNumber.gridx = 0;
		gbc_lblOrderNumber.gridy = 1;
		panel.add(lblOrderNumber, gbc_lblOrderNumber);
		
		txtOrderID = new JTextField();
		txtOrderID.setColumns(10);
		GridBagConstraints gbc_txtOrderID = new GridBagConstraints();
		gbc_txtOrderID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOrderID.anchor = GridBagConstraints.NORTH;
		gbc_txtOrderID.gridwidth = 2;
		gbc_txtOrderID.insets = new Insets(0, 0, 5, 5);
		gbc_txtOrderID.gridx = 2;
		gbc_txtOrderID.gridy = 1;
		panel.add(txtOrderID, gbc_txtOrderID);
		
		JCheckBox checkBox_1 = new JCheckBox("Today Transaction");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_1.gridx = 5;
		gbc_checkBox_1.gridy = 1;
		panel.add(checkBox_1, gbc_checkBox_1);
		
		JLabel label_2 = new JLabel("Transaction Date");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 0, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 2;
		panel.add(label_2, gbc_label_2);
		
		JLabel label_3 = new JLabel("from");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 0, 5);
		gbc_label_3.gridx = 1;
		gbc_label_3.gridy = 2;
		panel.add(label_3, gbc_label_3);
		
		GridBagConstraints gbc_dtpFrom = new GridBagConstraints();
		gbc_dtpFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtpFrom.gridwidth = 2;
		gbc_dtpFrom.insets = new Insets(0, 0, 0, 5);
		gbc_dtpFrom.gridx = 2;
		gbc_dtpFrom.gridy = 2;
		panel.add(dtpFrom, gbc_dtpFrom);
		
		JLabel label_4 = new JLabel("to");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridx = 4;
		gbc_label_4.gridy = 2;
		panel.add(label_4, gbc_label_4);
		
		GridBagConstraints gbc_dtpTo = new GridBagConstraints();
		gbc_dtpTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtpTo.gridwidth = 2;
		gbc_dtpTo.insets = new Insets(0, 0, 0, 5);
		gbc_dtpTo.gridx = 5;
		gbc_dtpTo.gridy = 2;
		panel.add(dtpTo, gbc_dtpTo);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearch_Clicked(e);
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 7;
		gbc_button.gridy = 2;
		panel.add(button, gbc_button);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jTable_clicked(e);
			}
		});
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane.setRowHeaderView(scrollPane_1);
		
		LoadTable();
	} 
	
	protected void btnSearch_Clicked(ActionEvent e) {
		// TODO Auto-generated method stub
		String customerID = txtCustID.getText();
		String orderID = txtOrderID.getText();
		Date dateFrom = dtpFrom.getDate();
		Date dateTo = dtpTo.getDate();
		
		String selectQuery = "SELECT transaction_id as \"TRANS ID\", user_id as \"USER ID\", transaction_time as \"WAKTU TRANSAKSI\", order_number as \"NOMER ORDER\", invoice_number as \"NOMER INVOICE\", transaction_status \"STATUS\", courier as \"KURIR\" FROM adempiere.app_transaction {0} AND transaction_status = 'PAID' ORDER BY transaction_time DESC"; 
		String fullQuery = "";
		if(customerID.length() == 0 && orderID.length() == 0)
		{
			fullQuery = MessageFormat.format(selectQuery, "");
		}
		if(orderID != null && orderID.length() > 0)
		{
			fullQuery = MessageFormat.format(selectQuery, "WHERE order_number='" + orderID + "'");
		}
		else if(customerID != null && customerID.length() > 0)
		{
			fullQuery = MessageFormat.format(selectQuery, "WHERE user_id=" + customerID);
		}
		else if(dateFrom != null && dateTo != null && customerID.length() > 0)
		{
			Date startDate = dtpFrom.getDate();
			Date endDate = dtpTo.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			fullQuery = MessageFormat.format(selectQuery, "WHERE transaction_time >='" + formatter.format(startDate) + "' AND transaction_time <'" + formatter.format(endDate) + "' AND user_id=" + customerID);
		}
		else if (dateFrom != null && dateTo != null && customerID.length() == 0)
		{
			Date startDate = dtpFrom.getDate();
			Date endDate = dtpTo.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			fullQuery = MessageFormat.format(selectQuery, "WHERE transaction_time >='" + formatter.format(startDate) + "' AND transaction_time <='" + formatter.format(endDate) + "'");
		}
		new Thread(new DataLoader(fullQuery)).start();
	}
		
	protected void jTable_clicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JTable target = (JTable)e.getSource();
		int row = target.getSelectedRow();
		
		String cellValue = target.getValueAt(row, 1).toString();
		long transID = Long.parseLong(cellValue);
		String orderNumber = target.getValueAt(row, 2).toString();
		String currentStatus = target.getValueAt(row, 6).toString();
		DetailPackagingFrame packagingFrame = new DetailPackagingFrame(transID, orderNumber, currentStatus);
		packagingFrame.setVisible(true);
		//DetailOrderFrame orderframe = new DetailOrderFrame(transID, orderNumber, currentStatus);
	}

	void LoadTable()
	{
		new Thread(new DataLoader(baseSelectQuery)).start();
	}
	
	private class DataLoader implements Runnable
	{
		String selectStatement = "";
		
		public DataLoader(String query)
		{
			selectStatement = query;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Connection conn = Database.GetSQLConnection();
			if(conn != null)
			{
				try
				{
					Statement stat = conn.createStatement();
					ResultSet rSet = stat.executeQuery(selectStatement);
					HelperClass.paidTransModelViewer = UIBuilder.buildTableModel(rSet);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							table.setModel(HelperClass.paidTransModelViewer);
						}
					});
					
					stat.close();
					rSet.close();
					conn.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		
	}

}
