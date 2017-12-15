package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.myacico.sql.Database;
import com.myacico.util.HelperClass;
import com.myacico.util.TransactionDetail;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class DetailPackagingFrame extends JFrame {

	private JPanel contentPane;
	private long transId = 0;
	private String orderNumber = "";
	private String currentStatus = "";
	private JLabel lblDetailOrder = new JLabel("");
	private JEditorPane editorPane = new JEditorPane();
	private JTextPane shippingInfo = new JTextPane();
	private JTextPane billingInfo = new JTextPane();
	private JTextField txtAwbNumber;
	private JTextField txtShipmentCharge;
	/**
	 * Create the frame.
	 */
	public DetailPackagingFrame(long transID, String orderNumber, String currentStatus) {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 936, 658);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Info Customer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel shippingInfoPanel = new JPanel();
		shippingInfoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Shipping Address Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		shippingInfoPanel.setBounds(16, 20, 281, 279);
		panel.add(shippingInfoPanel);
		shippingInfoPanel.setLayout(new BorderLayout(0, 0));
		
		shippingInfoPanel.add(shippingInfo, BorderLayout.CENTER);
		
		JPanel billingInfoPanel = new JPanel();
		billingInfoPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Billing Address Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		billingInfoPanel.setBounds(309, 20, 281, 279);
		panel.add(billingInfoPanel);
		billingInfoPanel.setLayout(new BorderLayout(0, 0));
		
		billingInfoPanel.add(billingInfo, BorderLayout.CENTER);
		
		JButton btnShip = new JButton("Send Item");
		btnShip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnShip_clicked(arg0);
			}
		});
		btnShip.setBounds(602, 263, 113, 36);
		panel.add(btnShip);
		
		JButton btnDelivered = new JButton("Delivered");
		btnDelivered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelivered_Clicked(e);
			}
		});
		btnDelivered.setBounds(710, 263, 113, 36);
		panel.add(btnDelivered);
		
		JLabel lblNewLabel = new JLabel("AWB Number");
		lblNewLabel.setBounds(612, 227, 93, 24);
		panel.add(lblNewLabel);
		
		txtAwbNumber = new JTextField();
		txtAwbNumber.setBounds(728, 226, 148, 26);
		panel.add(txtAwbNumber);
		txtAwbNumber.setColumns(10);
		
		txtShipmentCharge = new JTextField();
		txtShipmentCharge.setColumns(10);
		txtShipmentCharge.setBounds(728, 196, 148, 26);
		panel.add(txtShipmentCharge);
		
		JLabel lblOngkir = new JLabel("Shipment Charge");
		lblOngkir.setBounds(612, 197, 113, 24);
		panel.add(lblOngkir);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Order Line Item", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_1.add(editorPane, BorderLayout.CENTER);
		
		this.transId = transID;
		this.orderNumber = orderNumber;
		editorPane.setEditable(false);
		loadTransactionInfo();
		loadShippingAddress();
		loadBillingAddress();
		
		if(currentStatus.equalsIgnoreCase("PAID"))
			btnDelivered.setEnabled(false);
		
		if(currentStatus.equalsIgnoreCase("DELIVERED") || currentStatus.equalsIgnoreCase("RECEIVED") || currentStatus.equalsIgnoreCase("SHIPPING"))
		{
			btnDelivered.setEnabled(true);
			btnShip.setEnabled(false);
		}
		
		if(HelperClass.loginAs.equalsIgnoreCase("CS"))
		{
			btnShip.setEnabled(false);
			btnDelivered.setEnabled(false);
		}
	}
	
	protected void btnDelivered_Clicked(ActionEvent e) {
		// TODO Auto-generated method stub
		String updateStatement = "UPDATE adempiere.app_transaction SET Transaction_status='DELIVERED' WHERE transaction_id=" + transId;
		Connection conn = Database.GetSQLConnection();
		int affectedRecord = Database.UpdateDataToServer(updateStatement, conn);
		if(affectedRecord > 0)
		{
			for(int i=0;i<HelperClass.paidTransModelViewer.getRowCount();i++)
			{
				if(HelperClass.paidTransModelViewer.getValueAt(i, 2).toString().equalsIgnoreCase(this.orderNumber))
				{
					HelperClass.paidTransModelViewer.setValueAt("DELIVERED", i, 6);
					HelperClass.paidTransModelViewer.fireTableDataChanged();
					break;
				}
			}
			String jsonInput = "{\"orderId\":\"" + this.orderNumber + "\",\"status\":\"DELIVERED\"}";
			String urlTarget = "https://api.myacico.co.id/myacico-service/mail/sendemailorderstatus";
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
	}

	protected void btnShip_clicked(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String updateStatement = "UPDATE adempiere.app_transaction SET Transaction_status='SHIPPED', awb_number='" + txtAwbNumber.getText() + "' WHERE transaction_id=" + transId;
		Connection conn = Database.GetSQLConnection();
		int affectedRecord = Database.UpdateDataToServer(updateStatement, conn);
		if(affectedRecord > 0)
		{
			for(int i=0;i<HelperClass.paidTransModelViewer.getRowCount();i++)
			{
				if(HelperClass.paidTransModelViewer.getValueAt(i, 2).toString().equalsIgnoreCase(this.orderNumber))
				{
					HelperClass.paidTransModelViewer.setValueAt("SHIPPED", i, 6);
					HelperClass.paidTransModelViewer.fireTableDataChanged();
					break;
				}
			}
			String jsonInput = "{\"orderId\":\"" + this.orderNumber + "\",\"awbNumber\":\"" + txtAwbNumber.getText() + "\",\"status\":\"SHIPPING\"}";
			String urlTarget = "https://api.myacico.co.id/myacico-service/mail/sendemailorderstatus";
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
	}

	private void loadTransactionInfo()
	{
		try {
			/*TransactionDetail transDetail = HelperClass.getiInvoiceDetail(transId);
			String detail = transDetail.getDetail();
			editorPane.setContentType("text/html");
			detail = "<html><body>" + detail + "</body></html>";
			editorPane.setText(detail);*/
			TransactionDetail transDetail = HelperClass.GetTransactionDetail(this.orderNumber);
			String itemOrderLine = transDetail.getDetail();
			editorPane.setContentType("text/html");
			itemOrderLine = "<html><body>" + itemOrderLine + "</body></html>";
			editorPane.setText(itemOrderLine);
			txtShipmentCharge.setText(transDetail.getCourierAmount().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadAWBInfo()
	{
		
	}
	
	private void loadShippingAddress()
	{
		Connection conn = Database.GetSQLConnection();
		String shippingAddressInfo = Database.GetShippingForGudang(this.transId, conn);
		shippingInfo.setText(shippingAddressInfo);
	}
	
	private void loadBillingAddress()
	{
		Connection conn = Database.GetSQLConnection();
		String billingAddressInfo = Database.GetBillingAddressForGudang(this.transId, conn);
		billingInfo.setText(billingAddressInfo);
	}
}
