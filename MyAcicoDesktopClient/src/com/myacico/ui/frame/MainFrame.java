package com.myacico.ui.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alee.laf.WebLookAndFeel;
import com.myacico.ui.internalframe.IFrameTransactionViewer;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JDesktopPane desktopPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install();
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 412);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuItemViewTrans = new JMenuItem("View Transaction");
		menuBar.add(fileMenu);
		fileMenu.add(menuItemViewTrans);
		
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
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

}
