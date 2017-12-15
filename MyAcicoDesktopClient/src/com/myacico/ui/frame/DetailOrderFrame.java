package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jdesktop.swingx.JXDatePicker;

import com.alee.laf.desktoppane.WebInternalFrame;
import com.myacico.sql.Database;
import com.myacico.ui.internalframe.IFrameLogin;
import com.myacico.ui.internalframe.IFrameTransactionViewer;
import com.myacico.util.HelperClass;
import com.myacico.util.TransactionDetail;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JEditorPane;

public class DetailOrderFrame extends JInternalFrame {

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
	private final String baseDownloadURL = "https://api.myacico.co.id/myacico-service/GetDataFromServer";
	private JEditorPane editorPane;
	private JTextField imgUrl;
	/**
	 * Create the frame.
	 */
	public DetailOrderFrame(String transID, String orderID, String customerID, String invoiceNumber) {
		this.transID = transID;
		this.orderID = orderID;
		this.customerID_order = customerID;
		this.invoiceNumber = invoiceNumber;
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setClosable(true);
		setBounds(100, 100, 856, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane);
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
		cbTransStatus.setModel(new DefaultComboBoxModel(new String[] {"PAID", "VERIFIED", "DELIVERED", "PENDING", "CANCELED"}));
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
		transferReceiptContainer.setBounds(503, 27, 305, 295);
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
		scrollPane.setViewportBorder(new TitledBorder(null, "Detail Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane);
		
		scrollPane.setViewportView(editorPane);
		
		imgUrl = new JTextField();
		imgUrl.setBounds(582, 329, 146, 26);
		contentPane.add(imgUrl);
		imgUrl.setColumns(10);
		
		DataLoader loader = new DataLoader(this.transID, this.customerID_order, this.orderID, this.invoiceNumber);
		new Thread(loader).start();
		loadTransactionDetail();
		
		if(HelperClass.loginAs.equalsIgnoreCase("CS"))
		{
			btnUpdateData.setEnabled(false);
		}
	}
	
	private void btnUpdateData_ActionPerformed(ActionEvent e)
	{
		String updateString = "UPDATE adempiere.app_transaction SET transaction_status ='" + cbTransStatus.getSelectedItem().toString() + "', Transaction_status_code='2' WHERE user_id=" + this.customerID_order + " AND order_number ='" + this.orderID + "'";
		Connection conn = Database.GetSQLConnection();
		int affectedRecord = Database.UpdateDataToServer(updateString, conn);
		
		if(affectedRecord > 0)
		{
			String updateInvoice = "UPDATE adempiere.app_transaction SET invoice_number =(SELECT get_app_nextinvoice FROM adempiere.get_app_nextinvoice(1)) WHERE user_id=" + this.customerID_order + " AND order_number ='" + this.orderID + "'";
			affectedRecord = Database.UpdateDataToServer(updateInvoice, conn);
			
			for(int i=0;i<HelperClass.transModelViewer.getRowCount();i++)
			{
				if(HelperClass.transModelViewer.getValueAt(i, 3).toString().equalsIgnoreCase(this.orderID))
				{
					HelperClass.transModelViewer.setValueAt("PAID", i, 5);
					HelperClass.transModelViewer.fireTableDataChanged();
					break;
				}
			}
			
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (affectedRecord > 0)
			{
				if(cbTransStatus.getSelectedItem().toString().equalsIgnoreCase("CANCELLED"))
				{
					//sending payment confirmation email to client using API
					String urlTarget = "https://api.myacico.co.id/myacico-service/mail/sendInvoice";
					String tokenVal = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYWlsQG1haWwuY29tIiwiYXVkIjoiQURNSU4tQUNDIiwianRpIjoiMjM0MiJ9.i-A1qHNcyoo2z-GTqgue5YKWdDi04qjWER_lDAkG07o";
					HttpPost postMethod = new HttpPost(urlTarget);
					postMethod.addHeader("token", tokenVal);
					HttpClient client = HttpClientBuilder.create().build();
					try {
						HttpResponse response = client.execute(postMethod);
						System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
						
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity().getContent()));
	
							StringBuffer result = new StringBuffer();
							String line = "";
							while ((line = rd.readLine()) != null) {
								result.append(line);
							}
							System.out.println(result.toString());
						if(response.getStatusLine().getStatusCode() == 200)
						{
							JOptionPane.showMessageDialog(this, "Data Updated");
						}
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					//sending payment confirmation email to client using API
					String jsonInput = "{\"orderId\":\"" + txtTransID.getText() + "\"}";
					String urlTarget = "https://api.myacico.co.id/myacico-service/mail/sendInvoice";
					String tokenVal = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYWlsQG1haWwuY29tIiwiYXVkIjoiQURNSU4tQUNDIiwianRpIjoiMjM0MiJ9.i-A1qHNcyoo2z-GTqgue5YKWdDi04qjWER_lDAkG07o";
		            StringEntity requestEntity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
		            HttpPost postMethod = new HttpPost(urlTarget);
		            postMethod.setEntity(requestEntity);
		            postMethod.addHeader("token", tokenVal);
		            HttpClient client = HttpClientBuilder.create().build();
		            try {
						HttpResponse response = client.execute(postMethod);
						System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
						
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(response.getEntity().getContent()));
	
							StringBuffer result = new StringBuffer();
							String line = "";
							while ((line = rd.readLine()) != null) {
								result.append(line);
							}
							System.out.println(result.toString());
						if(response.getStatusLine().getStatusCode() == 200)
						{
							JOptionPane.showMessageDialog(this, "Data Updated");
						}
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//new IFrameTransactionViewer().LoadInitialData();
			}
		}
		else
			JOptionPane.showMessageDialog(this, "Error Updating Data. Please Try Again");
	}
	
	void loadTransactionDetail()
	{
		TransactionDetail transDetail;
		try {
			/*transDetail = HelperClass.getiInvoiceDetail(Long.parseLong(this.transID));
			String detail = transDetail.getDetail();
			editorPane.setContentType("text/html");
			detail = "<html><body>" + detail + "</body></html>";
			editorPane.setText(detail);*/
			transDetail = HelperClass.GetTransactionDetail(this.orderID);
			String itemOrderLine = transDetail.getDetail();
			editorPane.setContentType("text/html");
			itemOrderLine = "<html><body>" + itemOrderLine + "</body></html>";
			editorPane.setText(itemOrderLine);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
					String shippingAddress = Database.GetShippingForGudang(Long.parseLong(transID), conn);
					String billingAddress = Database.GetBillingAddressForGudang(Long.parseLong(transID), conn);
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
					String dateTime = formatter.format(rSet.getTimestamp("transaction_time"));
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtTransID.setText(orderID);
							txtCustID.setText(customerID);
							txtTransTime.setText(dateTime);
							txtCustName.setText(customerName);
							txtBillingAddress.setText(billingAddress);
							txtShippingAddress.setText(shippingAddress);
							
							URL url = null;
							URL imageNotFoundURL = null;
							try {
								url = new URL(baseDownloadURL + "?userid=" + customerID + "&orderid=" + orderID);
								imageNotFoundURL = new URL("https://storage.googleapis.com/myacicoidbucketmultiregional/image-not-found.png");
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							String tempImageLocation = "/image/";
							/*File imageLocation = new File(tempImageLocation);
							
							if(!imageLocation.exists())
								imageLocation.mkdir();*/
							
							try {
								/*File savedImage = new File(imageLocation, "image.jpeg");
								FileUtils.copyURLToFile(url, savedImage);*/
								System.out.println("Image URL :  " + url);
								imgUrl.setText(url.toExternalForm());
								if(url != null){
									BufferedImage image = ImageIO.read(url);
									try
									{
										Image resizedImage = image.getScaledInstance(transferReceiptContainer.getWidth(), transferReceiptContainer.getHeight(), Image.SCALE_SMOOTH);
										ImageIcon icon = new ImageIcon(resizedImage);
										transferReceiptContainer.setIcon(icon);
									}
									catch(Exception ex)
									{
										BufferedImage tempImage = ImageIO.read(imageNotFoundURL);
										Image resizedImage = tempImage.getScaledInstance(transferReceiptContainer.getWidth(), transferReceiptContainer.getHeight(), Image.SCALE_SMOOTH);
										ImageIcon icon = new ImageIcon(resizedImage);
										transferReceiptContainer.setIcon(icon);
									}
								}
								else
								{
									//FileUtils.copyURLToFile(new URL("https://storage.googleapis.com/myacicoidbucketmultiregional/image-not-found.png"), savedImage);
									BufferedImage image = ImageIO.read(imageNotFoundURL);
									Image resizedImage = image.getScaledInstance(transferReceiptContainer.getWidth(), transferReceiptContainer.getHeight(), Image.SCALE_SMOOTH);
									ImageIcon icon = new ImageIcon(resizedImage);
									transferReceiptContainer.setIcon(icon);
								}
								loadTransactionDetail();
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
