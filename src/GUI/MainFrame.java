
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenu mnParamtrage = new JMenu("Paramétrage");
		menuBar.add(mnParamtrage);
		
		JMenuItem mntmMatires = new JMenuItem("Matières");
		mnParamtrage.add(mntmMatires);
		
		JMenuItem mntmEnseignants = new JMenuItem("Enseignants");
		mnParamtrage.add(mntmEnseignants);
		
		JMenuItem mntmSalles = new JMenuItem("Salles");
		mnParamtrage.add(mntmSalles);
		
		JMenuItem mntmCrneauxHoraires = new JMenuItem("Créneaux horaires");
		mnParamtrage.add(mntmCrneauxHoraires);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
