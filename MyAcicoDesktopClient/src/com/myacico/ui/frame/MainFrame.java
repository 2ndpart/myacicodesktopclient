package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import com.myacico.ui.internalframe.IFrameLogin;
import com.myacico.ui.internalframe.IFrameTransactionViewer;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.jdesktop.swingx.JXTaskPaneContainer;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	public JDesktopPane desktopPane;
	final String liquidLFString = "com.birosoft.liquid.LiquidLookAndFeel";
	final String nimbusLFString = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	final String webLFString = WebLookAndFeel.class.getCanonicalName();
	public JMenu fileMenu = new JMenu("Menu");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//WebLookAndFeel.install();
					//JFrame.setDefaultLookAndFeelDecorated(true);
					MainFrame frame = new MainFrame();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	void setLookAndFeel(String lfName)
	{
		try {
			UIManager.setLookAndFeel(lfName);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 412);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem menuItemViewTrans = new JMenuItem("View Transaction");
		menuBar.add(fileMenu);
		fileMenu.add(menuItemViewTrans);
		
		JMenuItem menuItemViewB2BReg = new JMenuItem("B2B Registration Approval");
		fileMenu.add(menuItemViewB2BReg);
		
		JMenu mnLookAndFeel = new JMenu("Look And Feel");
		menuBar.add(mnLookAndFeel);
		
		JMenuItem mntmWebLf = new JMenuItem("Web L&f");
		mntmWebLf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLookAndFeel(webLFString);
			}
		});
		mnLookAndFeel.add(mntmWebLf);
		
		JMenuItem mntmNimbusLf = new JMenuItem("Nimbus L&F");
		mntmNimbusLf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setLookAndFeel(nimbusLFString);
			}
		});
		mnLookAndFeel.add(mntmNimbusLf);
		
		JMenuItem mntmQuauaLf = new JMenuItem("Liquid L&F");
		mntmQuauaLf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setLookAndFeel(liquidLFString);
			}
		});
		mnLookAndFeel.add(mntmQuauaLf);
		
		menuItemViewTrans.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IFrameTransactionViewer transViewer = new IFrameTransactionViewer();
				desktopPane.add(transViewer);
				transViewer.setVisible(true);
			}
		});
		
		contentPane = new JPanel();
		desktopPane = new JDesktopPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(desktopPane, BorderLayout.CENTER);
		setContentPane(contentPane);
		
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		IFrameLogin loginForm = new IFrameLogin();
		this.desktopPane.add(loginForm);
		loginForm.setVisible(true);
		
		fileMenu.setEnabled(false);
		
		setLookAndFeel(nimbusLFString);
	}
}
