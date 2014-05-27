package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;

import DATA.Filler;
import DATA.WeekTable;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private JMenuItem mntmTeachers;
	private JProgressBar pb;
	
	public MainFrame(Filler filler) {
		
		// On charge le datastore.
		//this.dataStore = new DataStore();
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
		
		mntmTeachers = new JMenuItem("Enseignants");
		mntmTeachers.addActionListener(this);
		mnParamtrage.add(mntmTeachers);
		
		JMenuItem mntmSalles = new JMenuItem("Salles");
		mnParamtrage.add(mntmSalles);
		
		JMenuItem mntmCrneauxHoraires = new JMenuItem("Créneaux horaires");
		mnParamtrage.add(mntmCrneauxHoraires);
		
		JMenu mnEmploiDuTemps = new JMenu("Emploi du temps");
		menuBar.add(mnEmploiDuTemps);
		
		JMenuItem mntmViewEdT = new JMenuItem("Visualiser un emploi du temps");
		mntmViewEdT.addActionListener(new ViewEdTListener());
		mnEmploiDuTemps.add(mntmViewEdT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.pb = new JProgressBar();
		pb.setValue(46);
		contentPane.add(pb, BorderLayout.SOUTH);
		setContentPane(contentPane);
		setVisible(true);
	}

	class ViewEdTListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new EdTViewerWindow(WeekTable.getDefault());
		}
	}
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == this.mntmTeachers) {
//			TeacherPanel teacherWindow = new TeacherPanel(this, this.dataStore);
//			teacherWindow.getRootPane().setVisible(true);
//		}
		
	}
}
