package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.myacico.sql.Database;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DetailB2BRegistrationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtCompanyName;
	private JTextField txtPhone;
	private JTextField txtPhone2;
	private JTextPane txtCompanyAddress;
	private JLabel imageContainer;
	private JTextField txtRegisteredUser;
	private JTextField txtCity;
	private JTextField txtEmail;
	private JTextField imgUrl;
	private BufferedImage originalImage;
	/**
	 * Create the frame.
	 */
	public DetailB2BRegistrationFrame(String userEmail) {
		setTitle("B2B Registration Detail");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 926, 385);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		imageContainer = new JLabel("");
		imageContainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				imageContainer_mouseClicked(arg0);
			}
		});
		imageContainer.setBounds(511, 14, 303, 296);
		contentPane.add(imageContainer);
		
		JLabel lblNewLabel = new JLabel("Company Name");
		lblNewLabel.setBounds(5, 47, 100, 14);
		contentPane.add(lblNewLabel);
		
		txtCompanyName = new JTextField();
		txtCompanyName.setBounds(127, 41, 243, 27);
		contentPane.add(txtCompanyName);
		txtCompanyName.setColumns(10);
		
		JLabel lblCompanyAddress = new JLabel("Company Address");
		lblCompanyAddress.setBounds(5, 101, 112, 14);
		contentPane.add(lblCompanyAddress);
		
		txtCompanyAddress = new JTextPane();
		txtCompanyAddress.setBounds(127, 76, 243, 68);
		contentPane.add(txtCompanyAddress);
		
		JLabel lblCompanyPhone = new JLabel("Company Phone");
		lblCompanyPhone.setBounds(5, 161, 96, 14);
		contentPane.add(lblCompanyPhone);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(127, 155, 243, 27);
		txtPhone.setColumns(10);
		contentPane.add(txtPhone);
		
		JLabel lblCompanyPhone_1 = new JLabel("Company Phone 2");
		lblCompanyPhone_1.setBounds(5, 199, 100, 14);
		contentPane.add(lblCompanyPhone_1);
		
		txtPhone2 = new JTextField();
		txtPhone2.setBounds(127, 193, 243, 27);
		txtPhone2.setColumns(10);
		contentPane.add(txtPhone2);
		
		JLabel lblRegisteredUserName = new JLabel("Registered User");
		lblRegisteredUserName.setBounds(5, 22, 106, 14);
		contentPane.add(lblRegisteredUserName);
		
		txtRegisteredUser = new JTextField();
		txtRegisteredUser.setColumns(10);
		txtRegisteredUser.setBounds(127, 11, 106, 27);
		contentPane.add(txtRegisteredUser);
		
		JLabel lblCompanyCity = new JLabel("Company City");
		lblCompanyCity.setBounds(5, 237, 87, 14);
		contentPane.add(lblCompanyCity);
		
		txtCity = new JTextField();
		txtCity.setColumns(10);
		txtCity.setBounds(127, 231, 243, 27);
		contentPane.add(txtCity);
		
		JComboBox cbxStatus = new JComboBox();
		cbxStatus.setModel(new DefaultComboBoxModel(new String[] {"Active", "Reject"}));
		cbxStatus.setBounds(127, 262, 243, 27);
		contentPane.add(cbxStatus);
		
		JLabel lblRegistrationStatus = new JLabel("Registration Status");
		lblRegistrationStatus.setBounds(5, 268, 100, 14);
		contentPane.add(lblRegistrationStatus);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(5, 323, 89, 23);
		contentPane.add(btnSave);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(243, 11, 127, 27);
		contentPane.add(txtEmail);
		
		imgUrl = new JTextField();
		imgUrl.setColumns(10);
		imgUrl.setBounds(543, 322, 243, 25);
		contentPane.add(imgUrl);
		new Thread(new DataLoader(userEmail)).start();;
	}
	
	protected void imageContainer_mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//new ImageViewerFrame(originalImage).setVisible(true);
	}

	private class DataLoader implements Runnable
	{
		String userEmail = "";
		private final String baseDownloadURL = "http://acc.myacico.co.id/myacico-account/GetB2BRegistrationImage";
		public DataLoader(String userEmailParameter)
		{
			this.userEmail = userEmailParameter;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String queryToLoad = "SELECT ad.ad_user_id,ad.isactive, ad.name, ad.email, ad.emailuser, ad.emailuserpw,ad.phone,ad.phone2,ad.emailverify,ad.emailverifydate,ad.notificationtype,ad.datelastlogin,\r\n" + 
					"    cl.address1,cl.address2,cl.address3,cl.address4,cl.city,cl.postal,acbpg.name as role,\r\n" + 
					"    acbp.name companyname,acbp.tax_image\r\n" + 
					"FROM adempiere.ad_user ad\r\n" + 
					"INNER JOIN adempiere.c_bpartner acbp ON acbp.c_bpartner_id=ad.c_bpartner_id\r\n" + 
					"INNER JOIN adempiere.c_bp_group acbpg ON acbpg.c_bp_group_id=acbp.c_bp_group_id\r\n" + 
					"LEFT JOIN adempiere.c_bpartner_location acbl ON acbl.c_bpartner_location_id=ad.c_bpartner_location_id\r\n" + 
					"LEFT JOIN adempiere.c_location cl ON cl.c_location_id=acbl.c_location_id\r\n" + 
					"LEFT JOIN adempiere.c_country cc ON cc.c_country_id= cl.c_country_id   \r\n" + 
					"LEFT JOIN adempiere.c_region cr ON cr.c_region_id=cl.c_region_id\r\n" + 
					"where ad.ad_client_id=1000000 AND ad.ad_org_id=1000000 AND ad.email='" + this.userEmail + "'";
			
			Connection conn = Database.GetSQLConnection();
			try
			{
				Statement stat = conn.createStatement();
				ResultSet rSet = stat.executeQuery(queryToLoad);
				while(rSet.next())
				{
					String registeredUser = rSet.getString("name");
					String emailUser = rSet.getString("email");
					String phone = rSet.getString("phone");
					String phone2 = rSet.getString("phone2");
					String companyAddress = rSet.getString("address1");
					String companyCity = rSet.getString("city") + " - " +  rSet.getString("postal");
					String companyName = rSet.getString("companyname");
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtRegisteredUser.setText(registeredUser);
							txtEmail.setText(emailUser);
							txtPhone.setText(phone);
							txtPhone2.setText(phone2);
							txtCompanyAddress.setText(companyAddress);
							txtCompanyName.setText(companyName);
							txtCity.setText(companyCity);
							
							URL url = null;
							URL imageNotFoundURL = null;
							try {
								url = new URL(baseDownloadURL + "?email=" + emailUser);
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
									originalImage = ImageIO.read(url);
									try
									{
										Image resizedImage = image.getScaledInstance(imageContainer.getWidth(), imageContainer.getHeight(), Image.SCALE_SMOOTH);
										ImageIcon icon = new ImageIcon(resizedImage);
										imageContainer.setIcon(icon);
									}
									catch(Exception ex)
									{
										BufferedImage tempImage = ImageIO.read(imageNotFoundURL);
										Image resizedImage = tempImage.getScaledInstance(imageContainer.getWidth(), imageContainer.getHeight(), Image.SCALE_SMOOTH);
										ImageIcon icon = new ImageIcon(resizedImage);
										imageContainer.setIcon(icon);
									}
								}
								else
								{
									//FileUtils.copyURLToFile(new URL("https://storage.googleapis.com/myacicoidbucketmultiregional/image-not-found.png"), savedImage);
									BufferedImage image = ImageIO.read(imageNotFoundURL);
									Image resizedImage = image.getScaledInstance(imageContainer.getWidth(), imageContainer.getHeight(), Image.SCALE_SMOOTH);
									ImageIcon icon = new ImageIcon(resizedImage);
									imageContainer.setIcon(icon);
								}
								//loadTransactionDetail();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			}
			
			//load image from server
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}
}
