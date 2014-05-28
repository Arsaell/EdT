package GUI;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import DATA.DataStore;
import DATA.Filler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.io.File;

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
		this.setBounds(100, 100, 250, 140);
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
				JFileChooser fc = new JFileChooser();
				
			    fc.setFileFilter(new FileNameExtensionFilter("Emploi du Temps", "EdT"));
				
			    int res = fc.showOpenDialog(frame);
				
			    File f = null;
				if (res == JFileChooser.APPROVE_OPTION)	{
					f = fc.getSelectedFile();
					new MainFrame(new Filler(System.out, new DataStore(f)));
				}
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
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 23, 23));
		
		this.getContentPane().add(b1);
		this.getContentPane().add(b2);
		this.getContentPane().add(b3);
		this.getContentPane().add(b4);
		
	}

}
