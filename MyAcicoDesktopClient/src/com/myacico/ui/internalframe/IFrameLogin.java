package com.myacico.ui.internalframe;

import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import org.jdesktop.swingx.JXImagePanel;

import com.alee.laf.desktoppane.WebInternalFrame;
import com.myacico.ui.frame.MainFrame;
import com.myacico.util.HelperClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IFrameLogin extends WebInternalFrame {
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	JLabel imageLabel = new JLabel("");
	/**
	 * Create the frame.
	 */
	public IFrameLogin() {
		setBounds(100, 100, 534, 385);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{154, 69, 12, 0, 0, 89, 0};
		gridBagLayout.rowHeights = new int[]{56, 83, 60, 20, 20, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 1;
		getContentPane().add(imageLabel, gbc_lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("User Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 3;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		txtUserName = new JTextField();
		GridBagConstraints gbc_txtUserName = new GridBagConstraints();
		gbc_txtUserName.anchor = GridBagConstraints.NORTH;
		gbc_txtUserName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUserName.insets = new Insets(0, 0, 5, 5);
		gbc_txtUserName.gridx = 2;
		gbc_txtUserName.gridy = 3;
		getContentPane().add(txtUserName, gbc_txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.fill = GridBagConstraints.BOTH;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		txtPassword = new JPasswordField();
		GridBagConstraints gbc_txtPassword = new GridBagConstraints();
		gbc_txtPassword.anchor = GridBagConstraints.NORTH;
		gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
		gbc_txtPassword.gridx = 2;
		gbc_txtPassword.gridy = 4;
		getContentPane().add(txtPassword, gbc_txtPassword);
		
		JButton btnLogin = new JButton("");
		btnLogin.setIcon(new ImageIcon(IFrameLogin.class.getResource("/com/myacico/ui/image/login.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLogin_actionPerformed(arg0);
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.anchor = GridBagConstraints.NORTH;
		gbc_btnLogin.insets = new Insets(0, 0, 0, 5);
		gbc_btnLogin.gridwidth = 2;
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 5;
		getContentPane().add(btnLogin, gbc_btnLogin);
		
		imageLabel.setIcon(new ImageIcon(IFrameLogin.class.getResource("/com/myacico/ui/image/acico_logo.png")));
	}
	
	protected void btnLogin_actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String userName = txtUserName.getText();
		String password = txtPassword.getText();
		boolean isLogin = false;
		if(userName.equalsIgnoreCase("Finance") && password.equalsIgnoreCase("finance2017&"))
		{
			HelperClass.loginAs = "Finance";
			isLogin = true;
		}
		if(userName.equalsIgnoreCase("Warehouse") && password.equalsIgnoreCase("warehouse123#"))
		{
			HelperClass.loginAs = "Warehouse";
			isLogin = true;
		}
		if(userName.equalsIgnoreCase("CS") && password.equalsIgnoreCase("csmyacico6^1!"))
		{
			HelperClass.loginAs = "CS";
			isLogin = true;
		}
		if(userName.equalsIgnoreCase("it") && password.equalsIgnoreCase("it123"))
		{
			HelperClass.loginAs = "IT";
			isLogin = true;
		}
		if(isLogin)
		{
			MainFrame frame = (MainFrame)this.getTopLevelAncestor();
			frame.fileMenu.setEnabled(true);
			this.dispose();
		}
		else 
		{
			JOptionPane.showMessageDialog(this, "Please login using correct credential");
		}	
	}

	public Image GetAcicoLogo()
	{
		try
		{
//			/URL url = new URL("https://storage.googleapis.com/myacico/image/avatar/avatar.png");
			//File imageFile = new File("/com/myacico/res/acico_logo.png");
			
			URL imageURL = this.getClass().getResource("/com/myacico/ui/image/acico_logo.png");
			BufferedImage buffImage = ImageIO.read(imageURL);
			
			int w = buffImage.getWidth();
		    int h = buffImage.getHeight();
		    
		    BufferedImage dimg = new BufferedImage(362, 104, buffImage.getType());
		    Graphics2D g = dimg.createGraphics();
		    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    g.drawImage(buffImage, 0, 0, imageLabel.getWidth(), imageLabel.getHeight(), 0, 0, w, h, null);
		    g.dispose();
		    
			return dimg;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
