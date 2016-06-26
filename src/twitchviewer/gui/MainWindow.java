package twitchviewer.gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import twitchviewer.assist.TwitchConnector;

import javax.swing.JEditorPane;
import javax.swing.JComboBox;

public class MainWindow {

	private JFrame frame;
	private JTextField txtTwitchUsername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	private void exitDialog() {
		final Object[] options = {"Igen", "Nem"};
		final int exit = JOptionPane.showOptionDialog(frame, "Bizots, hogy ki akarsz lépni?", "Kilépés", JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, null);
		
		if(exit == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitDialog();
			}
		});
		frame.setBounds(100, 100, 807, 555);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JMenuBar mainMenuBar = new JMenuBar();
		frame.setJMenuBar(mainMenuBar);
		
		JMenu menuMainMenu = new JMenu("Főmenü");
		mainMenuBar.add(menuMainMenu);
		
		JMenuItem mainMenuItemExit = new JMenuItem("Kilépés");
		mainMenuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exitDialog();
			}
		});
		menuMainMenu.add(mainMenuItemExit);
		
		JMenu menuHelpMenu = new JMenu("Súgó");
		mainMenuBar.add(menuHelpMenu);
		
		JMenuItem helpMenuItemAbout = new JMenuItem("Névjegy");
		menuHelpMenu.add(helpMenuItemAbout);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 738, 89);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		txtTwitchUsername = new JTextField();
		txtTwitchUsername.setBounds(12, 31, 581, 25);
		panel.add(txtTwitchUsername);
		txtTwitchUsername.setFont(new Font("Dialog", Font.PLAIN, 16));
		txtTwitchUsername.setHorizontalAlignment(SwingConstants.LEFT);
		txtTwitchUsername.setText("baldher89");
		txtTwitchUsername.setColumns(30);
		
		final JComboBox comboBoxChannels = new JComboBox();
		comboBoxChannels.setBounds(22, 101, 728, 50);
		frame.getContentPane().add(comboBoxChannels);
		
		JButton btnSearch = new JButton("Keresés");
		final Map<Object,ImageIcon> icons = new HashMap<Object,ImageIcon>();
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Map<String,String> channel : TwitchConnector.getFollowedChannels(txtTwitchUsername.getText())) {
					
					
					icons.put(channel.get("url"),icon);
					comboBoxChannels.addItem(channel.get("url"));
				}
				
				comboBoxChannels.setRenderer(new IconListRenderer(icons));
				comboBoxChannels.revalidate();
				comboBoxChannels.repaint();
			}
		});
		
		btnSearch.setBounds(609, 31, 117, 25);
		panel.add(btnSearch);
	}
}
