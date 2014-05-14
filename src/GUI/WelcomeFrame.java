package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WelcomeFrame extends JFrame {

	private JFrame frame;
	/**
	 * Create the application.
	 */
	public WelcomeFrame() {
		super();
		setTitle("EdT");
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton b1 = new JButton("Nouveau");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new StartFrame();
				setVisible(false);
			}
		});
		
		JButton b2 = new JButton("Ouvrir");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new OpenFrame();
				setVisible(false);
			}
		});
		
		JButton b3 = new JButton("À Propos");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AboutFrame();
			}
		});
		
		JButton b4 = new JButton("Quitter");
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		this.getContentPane().setLayout(new GridLayout(2,2, 10, 10));
		
		this.getContentPane().add(b1);
		this.getContentPane().add(b2);
		this.getContentPane().add(b3);
		this.getContentPane().add(b4);
		
	}

}
