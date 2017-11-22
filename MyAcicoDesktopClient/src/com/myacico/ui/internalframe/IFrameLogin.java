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

import javax.swing.JLabel;
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
	private JTextField textField;
	private JPasswordField passwordField;
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
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 3;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.fill = GridBagConstraints.BOTH;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 4;
		getContentPane().add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.NORTH;
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 4;
		getContentPane().add(passwordField, gbc_passwordField);
		
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
		MainFrame frame = (MainFrame)this.getTopLevelAncestor();
		frame.fileMenu.setEnabled(true);
		
		this.dispose();
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
