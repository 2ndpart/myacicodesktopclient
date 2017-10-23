package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.io.FileUtils;
import org.jdesktop.swingx.JXDatePicker;

import com.myacico.sql.Database;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DetailOrderFrame extends JFrame {

	private JPanel contentPane;
	String transID, orderID, customerID_order, invoiceNumber = "";
	private JTextField txtTransID;
	private JTextField txtCustID;
	private JTextField txtTransTime;
	private JTextField txtCustName;
	private JComboBox cbTransStatus;
	private JButton btnUpdateData;
	private JTextArea txtBillingAddress;
	private JTextArea txtShippingAddress;
	private JLabel transferReceiptContainer;
	private JTable table;
	private final String baseDownloadURL = "http://api.myacico.co.id/myacico-service/GetDataFromServer";
	/**
	 * Create the frame.
	 */
	public DetailOrderFrame(String transID, String orderID, String customerID, String invoiceNumber) {
		this.transID = transID;
		this.orderID = orderID;
		this.customerID_order = customerID;
		this.invoiceNumber = invoiceNumber;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 856, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTransactionId = new JLabel("Transaction ID");
		lblTransactionId.setBounds(6, 27, 118, 16);
		contentPane.add(lblTransactionId);
		
		txtTransID = new JTextField();
		txtTransID.setBounds(153, 22, 130, 26);
		contentPane.add(txtTransID);
		txtTransID.setColumns(10);
		
		JLabel lblCustomerId = new JLabel("Customer ID");
		lblCustomerId.setBounds(6, 60, 118, 16);
		contentPane.add(lblCustomerId);
		
		txtCustID = new JTextField();
		txtCustID.setColumns(10);
		txtCustID.setBounds(153, 55, 130, 26);
		contentPane.add(txtCustID);
		
		JLabel lblTransactionTime = new JLabel("Transaction Time");
		lblTransactionTime.setBounds(6, 93, 118, 16);
		contentPane.add(lblTransactionTime);
		
		txtTransTime = new JTextField();
		txtTransTime.setColumns(10);
		txtTransTime.setBounds(153, 88, 130, 26);
		contentPane.add(txtTransTime);
		
		JLabel lblTransactionStatus = new JLabel("Transaction Status");
		lblTransactionStatus.setBounds(6, 126, 118, 16);
		contentPane.add(lblTransactionStatus);
		
		cbTransStatus = new JComboBox();
		cbTransStatus.setModel(new DefaultComboBoxModel(new String[] {"Paid", "Verified", "Pending"}));
		cbTransStatus.setBounds(153, 122, 130, 27);
		contentPane.add(cbTransStatus);
		
		JLabel lblBillingAddress = new JLabel("Billing Address");
		lblBillingAddress.setBounds(6, 158, 118, 16);
		contentPane.add(lblBillingAddress);
		
		txtBillingAddress = new JTextArea();
		txtBillingAddress.setWrapStyleWord(true);
		txtBillingAddress.setLineWrap(true);
		txtBillingAddress.setBounds(153, 158, 288, 90);
		contentPane.add(txtBillingAddress);
		
		txtShippingAddress = new JTextArea();
		txtShippingAddress.setWrapStyleWord(true);
		txtShippingAddress.setLineWrap(true);
		txtShippingAddress.setBounds(153, 258, 288, 90);
		contentPane.add(txtShippingAddress);
		
		JLabel lblShippingAddress = new JLabel("Shipping Address");
		lblShippingAddress.setBounds(6, 258, 118, 16);
		contentPane.add(lblShippingAddress);
		
		transferReceiptContainer = new JLabel("");
		transferReceiptContainer.setBounds(488, 27, 343, 321);
		contentPane.add(transferReceiptContainer);
		
		btnUpdateData = new JButton("Update Data");
		btnUpdateData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUpdateData_ActionPerformed(e);
			}
		});
		btnUpdateData.setBounds(6, 325, 117, 29);
		contentPane.add(btnUpdateData);
		
		txtCustName = new JTextField();
		txtCustName.setColumns(10);
		txtCustName.setBounds(295, 55, 146, 26);
		contentPane.add(txtCustName);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 360, 825, 182);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		DataLoader loader = new DataLoader(this.transID, this.customerID_order, this.orderID, this.invoiceNumber);
		new Thread(loader).start();
	}
	
	private void btnUpdateData_ActionPerformed(ActionEvent e)
	{
		String updateString = "UPDATE adempiere.app_transaction SET transaction_status ='PAID' WHERE user_id=" + this.customerID_order + " AND order_number ='" + this.orderID + "' AND invoice_number ='" + this.invoiceNumber + "'";
		Connection conn = Database.GetSQLConnection();
		int affectedRecord = Database.UpdateDataToServer(updateString, conn);
		
		if(affectedRecord > 0)
			JOptionPane.showMessageDialog(this, "Data Updated");
		else
			JOptionPane.showMessageDialog(this, "Error Updating Data. Please Try Again");
	}
	
	private class DataLoader implements Runnable
	{
		String transID, orderID, customerID, invoiceID = "";
		
		public DataLoader(String transID, String customerID, String orderID, String invoiceID)
		{
			this.transID = transID;
			this.orderID = orderID;
			this.customerID = customerID;
			this.invoiceID = invoiceID;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String selectTransData = "SELECT user_id, transaction_time, order_number, invoice_number, transaction_status , courier, billing_address, shipping_address, avidence_of_transfer FROM adempiere.app_transaction WHERE user_id = " + this.customerID + " AND order_number ='" + this.orderID + "' AND invoice_number = '" + this.invoiceID + "' ORDER BY transaction_time DESC";
			String getNameQuery = "SELECT NAME FROM adempiere.ad_user WHERE ad_user_id=" + customerID_order;
			try
			{
				Connection conn = Database.GetSQLConnection();
				Statement stat = conn.createStatement();
				ResultSet rSet = stat.executeQuery(selectTransData);
				
				while(rSet.next())
				{
					String customerName = Database.GetStringFromDB(getNameQuery, conn);
					String shippingAddress = Database.GetShippingAddress(rSet.getString("shipping_address"), conn);
					String billingAddress = Database.GetBillingAddress(rSet.getString("billing_address"), conn);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtTransID.setText(orderID);
							txtCustID.setText(customerID);
							txtCustName.setText(customerName);
							txtBillingAddress.setText(billingAddress);
							txtShippingAddress.setText(shippingAddress);
							
							URL url = null;
							try {
								url = new URL(baseDownloadURL + "?userid=" + customerID + "&orderid=" + orderID + "&invoiceid=" + invoiceID);
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String tempImageLocation = "/image/";
							File imageLocation = new File(tempImageLocation);
							
							if(!imageLocation.exists())
								imageLocation.mkdir();
							
							try {
								File savedImage = new File(imageLocation, "image.jpeg");
								FileUtils.copyURLToFile(url, savedImage);
								
								if(savedImage != null && savedImage.length() > 0 )
								{
									BufferedImage image = ImageIO.read(savedImage);
									Image resizedImage = image.getScaledInstance(transferReceiptContainer.getWidth(), transferReceiptContainer.getHeight(), Image.SCALE_SMOOTH);
									ImageIcon icon = new ImageIcon(resizedImage);
									transferReceiptContainer.setIcon(icon);
								}
								else
								{
									FileUtils.copyURLToFile(new URL("https://storage.googleapis.com/myacicoidbucketmultiregional/image-not-found.png"), savedImage);
									BufferedImage image = ImageIO.read(savedImage);
									Image resizedImage = image.getScaledInstance(transferReceiptContainer.getWidth(), transferReceiptContainer.getHeight(), Image.SCALE_SMOOTH);
									ImageIcon icon = new ImageIcon(resizedImage);
									transferReceiptContainer.setIcon(icon);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							//load transaction detil data
							
						}
					});
				}
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
