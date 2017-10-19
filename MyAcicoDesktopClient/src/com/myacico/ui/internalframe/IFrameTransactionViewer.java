package com.myacico.ui.internalframe;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Date;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdesktop.swingx.JXDatePicker;

import com.myacico.sql.Database;
import com.myacico.ui.builder.UIBuilder;
import com.myacico.ui.frame.DetailOrderFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IFrameTransactionViewer extends JInternalFrame {
	private JTable table;
	private JTextField txtCustID;
	private JTextField txtOrderID;
	private JCheckBox chckbxTodayTrans;
	private JXDatePicker dtpFrom;
	private JXDatePicker dtpTo;
	/**
	 * Create the frame.
	 */
	public IFrameTransactionViewer() {
		setBounds(100, 100, 890, 420);
		
		JPanel topPanel = CreateFilterPanel();
		
		getContentPane().add(topPanel, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableCellClicked(e);
			}
		});
		scrollPane.setViewportView(table);
		
		new Thread(new DataLoader()).start();
	}
	
	private JPanel CreateFilterPanel()
	{
		JPanel filterPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{230, 0, 46, 86, 0, 46, 0, 86, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		filterPanel.setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Customer ID");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		filterPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		txtCustID = new JTextField();
		GridBagConstraints gbc_txtCustID = new GridBagConstraints();
		gbc_txtCustID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCustID.gridwidth = 2;
		gbc_txtCustID.anchor = GridBagConstraints.NORTH;
		gbc_txtCustID.insets = new Insets(0, 0, 5, 5);
		gbc_txtCustID.gridx = 2;
		gbc_txtCustID.gridy = 0;
		filterPanel.add(txtCustID, gbc_txtCustID);
		txtCustID.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Order ID");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		filterPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtOrderID = new JTextField();
		GridBagConstraints gbc_txtOrderID = new GridBagConstraints();
		gbc_txtOrderID.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtOrderID.gridwidth = 2;
		gbc_txtOrderID.insets = new Insets(0, 0, 5, 5);
		gbc_txtOrderID.anchor = GridBagConstraints.NORTH;
		gbc_txtOrderID.gridx = 2;
		gbc_txtOrderID.gridy = 1;
		filterPanel.add(txtOrderID, gbc_txtOrderID);
		txtOrderID.setColumns(10);
		
		chckbxTodayTrans = new JCheckBox("Today Transaction");
		GridBagConstraints gbc_chckbxTodayTrans = new GridBagConstraints();
		gbc_chckbxTodayTrans.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTodayTrans.gridx = 5;
		gbc_chckbxTodayTrans.gridy = 1;
		filterPanel.add(chckbxTodayTrans, gbc_chckbxTodayTrans);
		
		JLabel lblTransactionDate = new JLabel("Transaction Date");
		GridBagConstraints gbc_lblTransactionDate = new GridBagConstraints();
		gbc_lblTransactionDate.insets = new Insets(0, 0, 0, 5);
		gbc_lblTransactionDate.gridx = 0;
		gbc_lblTransactionDate.gridy = 2;
		filterPanel.add(lblTransactionDate, gbc_lblTransactionDate);
		
		JLabel lblFrom = new JLabel("from");
		GridBagConstraints gbc_lblFrom = new GridBagConstraints();
		gbc_lblFrom.insets = new Insets(0, 0, 0, 5);
		gbc_lblFrom.gridx = 1;
		gbc_lblFrom.gridy = 2;
		filterPanel.add(lblFrom, gbc_lblFrom);
		
		dtpFrom = new JXDatePicker();
		GridBagConstraints gbc_dtpFrom = new GridBagConstraints();
		gbc_dtpFrom.gridwidth = 2;
		gbc_dtpFrom.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtpFrom.insets = new Insets(0, 0, 0, 5);
		gbc_dtpFrom.gridx = 2;
		gbc_dtpFrom.gridy = 2;
		filterPanel.add(dtpFrom, gbc_dtpFrom);
		
		JLabel lblTo = new JLabel("to");
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.insets = new Insets(0, 0, 0, 5);
		gbc_lblTo.gridx = 4;
		gbc_lblTo.gridy = 2;
		filterPanel.add(lblTo, gbc_lblTo);
		
		dtpTo = new JXDatePicker();
		GridBagConstraints gbc_dtpTo = new GridBagConstraints();
		gbc_dtpTo.fill = GridBagConstraints.HORIZONTAL;
		gbc_dtpTo.gridwidth = 2;
		gbc_dtpTo.insets = new Insets(0, 0, 0, 5);
		gbc_dtpTo.gridx = 5;
		gbc_dtpTo.gridy = 2;
		filterPanel.add(dtpTo, gbc_dtpTo);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSearch_ActionPerformed(arg0);
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.gridx = 7;
		gbc_btnSearch.gridy = 2;
		filterPanel.add(btnSearch, gbc_btnSearch);
		
		return filterPanel;
	}
	
	protected void btnSearch_ActionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String customerID = txtCustID.getText();
		String orderID = txtOrderID.getText();
		Date dateFrom = dtpFrom.getDate();
		Date dateTo = dtpTo.getDate();
		
		String selectQuery = "SELECT user_id as \"USER ID\", transaction_time as \"WAKTU TRANSAKSI\", order_number as \"NOMER ORDER\", invoice_number as \"NOMER INVOICE\", transaction_status \"STATUS\", courier as \"KURIR\" FROM adempiere.app_transaction {0} ORDER BY transaction_time DESC"; 
		
		if(customerID != null && customerID.length() > 0)
		{
			String fullQuery = MessageFormat.format(selectQuery, "WHERE user_id=" + customerID);
		}
	}

	private void tableCellClicked(MouseEvent e)
	{
		
		JTable target = (JTable)e.getSource();
		int row = target.getSelectedRow();
		
		String userID = target.getValueAt(row, 0).toString();
		String orderID = target.getValueAt(row, 2).toString();
		String invoiceNumber = target.getValueAt(row, 3).toString();
		
		DetailOrderFrame detailOrder = new DetailOrderFrame(orderID, userID, invoiceNumber);
		detailOrder.setVisible(true);
	}
	
	private class DataLoader implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String selectQuery = "SELECT user_id as \"USER ID\", transaction_time as \"WAKTU TRANSAKSI\", order_number as \"NOMER ORDER\", invoice_number as \"NOMER INVOICE\", transaction_status \"STATUS\", courier as \"KURIR\" FROM adempiere.app_transaction ORDER BY transaction_time DESC";
			Connection conn = Database.GetSQLConnection();
			if(conn != null)
			{
				try
				{
					Statement stat = conn.createStatement();
					ResultSet rSet = stat.executeQuery(selectQuery);
					DefaultTableModel model = UIBuilder.buildTableModel(rSet);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							table.setModel(model);
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