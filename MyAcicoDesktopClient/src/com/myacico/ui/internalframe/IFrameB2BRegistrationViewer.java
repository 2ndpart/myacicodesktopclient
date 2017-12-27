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

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import org.jdesktop.swingx.JXDatePicker;

import com.myacico.sql.Database;
import com.myacico.ui.builder.UIBuilder;
import com.myacico.ui.frame.DetailB2BRegistrationFrame;
import com.myacico.util.HelperClass;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IFrameB2BRegistrationViewer extends JInternalFrame {
	private JTextField emailField;
	private JTable table;
	String basicSelectStatement = "SELECT ad.name, ad.isactive, ad.email,ad.emailverifydate, " + 
			" cl.address1, acbp.name companyname" + 
			" FROM adempiere.ad_user ad " + 
			" INNER JOIN adempiere.c_bpartner acbp ON acbp.c_bpartner_id=ad.c_bpartner_id " + 
			" INNER JOIN adempiere.c_bp_group acbpg ON acbpg.c_bp_group_id=acbp.c_bp_group_id " + 
			" LEFT JOIN adempiere.c_bpartner_location acbl ON acbl.c_bpartner_location_id=ad.c_bpartner_location_id " + 
			" LEFT JOIN adempiere.c_location cl ON cl.c_location_id=acbl.c_location_id " + 
			" LEFT JOIN adempiere.c_country cc ON cc.c_country_id= cl.c_country_id    " + 
			" LEFT JOIN adempiere.c_region cr ON cr.c_region_id=cl.c_region_id " + 
			" WHERE ad.ad_client_id=1000000 AND ad.ad_org_id=1000000 AND acbpg.name='B2B' AND ad.isactive='N'";
	private JTextField txtEmail;
	private JTextField txtCompanyName;
	

	/**
	 * Create the frame.
	 */
	public IFrameB2BRegistrationViewer() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 835, 414);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableCell_Clicked(arg0);
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
//		/scrollPane.setColumnHeaderView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{230, 0, 46, 86, 0, 46, 0, 86, 0};
		gbl_panel.rowHeights = new int[]{20, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblEmail = new JLabel("User Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 0;
		panel.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.gridwidth = 2;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.gridx = 2;
		gbc_txtEmail.gridy = 0;
		panel.add(txtEmail, gbc_txtEmail);
		
		JLabel lblCompanyName = new JLabel("Company Name");
		GridBagConstraints gbc_lblCompanyName = new GridBagConstraints();
		gbc_lblCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompanyName.gridx = 0;
		gbc_lblCompanyName.gridy = 1;
		panel.add(lblCompanyName, gbc_lblCompanyName);
		
		txtCompanyName = new JTextField();
		txtCompanyName.setColumns(10);
		GridBagConstraints gbc_txtCompanyName = new GridBagConstraints();
		gbc_txtCompanyName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompanyName.anchor = GridBagConstraints.NORTH;
		gbc_txtCompanyName.gridwidth = 2;
		gbc_txtCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_txtCompanyName.gridx = 2;
		gbc_txtCompanyName.gridy = 1;
		panel.add(txtCompanyName, gbc_txtCompanyName);
		
		JCheckBox chckbxTodayRegistration = new JCheckBox("Today Registration");
		GridBagConstraints gbc_chckbxTodayRegistration = new GridBagConstraints();
		gbc_chckbxTodayRegistration.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTodayRegistration.gridx = 5;
		gbc_chckbxTodayRegistration.gridy = 1;
		panel.add(chckbxTodayRegistration, gbc_chckbxTodayRegistration);
		
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
		
		JXDatePicker datePicker = new JXDatePicker();
		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePicker.gridwidth = 2;
		gbc_datePicker.insets = new Insets(0, 0, 0, 5);
		gbc_datePicker.gridx = 2;
		gbc_datePicker.gridy = 2;
		panel.add(datePicker, gbc_datePicker);
		
		JLabel label_4 = new JLabel("to");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 0, 5);
		gbc_label_4.gridx = 4;
		gbc_label_4.gridy = 2;
		panel.add(label_4, gbc_label_4);
		
		JXDatePicker datePicker_1 = new JXDatePicker();
		GridBagConstraints gbc_datePicker_1 = new GridBagConstraints();
		gbc_datePicker_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePicker_1.gridwidth = 2;
		gbc_datePicker_1.insets = new Insets(0, 0, 0, 5);
		gbc_datePicker_1.gridx = 5;
		gbc_datePicker_1.gridy = 2;
		panel.add(datePicker_1, gbc_datePicker_1);
		
		JButton button = new JButton("Search");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 7;
		gbc_button.gridy = 2;
		panel.add(button, gbc_button);
		getContentPane().add(panel, BorderLayout.NORTH);
		new Thread(new DataLoader(this.basicSelectStatement)).start();
	}
	
	protected void tableCell_Clicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JTable target = (JTable)e.getSource();
		int row = target.getSelectedRow();
		
		String email = target.getValueAt(row, 2).toString();
		DetailB2BRegistrationFrame b2bRegFrame = new DetailB2BRegistrationFrame(email);
		b2bRegFrame.setVisible(true);
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
					HelperClass.b2bRegModelViewer = UIBuilder.buildTableModel(rSet);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							table.setModel(HelperClass.b2bRegModelViewer);
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
