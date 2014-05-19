

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Choice;
import javax.swing.JInternalFrame;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Label;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JCheckBox;
import java.awt.ComponentOrientation;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class ResearchWindow {

	private JFrame frame;
	private JTextField txtRechercheDeSalle;
	private JTextField txtPlageHoraire;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResearchWindow window = new ResearchWindow();
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
	public ResearchWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 439, 392);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		txtRechercheDeSalle = new JTextField();
		txtRechercheDeSalle.setText("Recherche de salle");
		frame.getContentPane().add(txtRechercheDeSalle, "4, 2, fill, default");
		txtRechercheDeSalle.setColumns(10);
		
		JCheckBox chckbxAmphittre = new JCheckBox("Amphitéâtre");
		frame.getContentPane().add(chckbxAmphittre, "4, 6");
		
		JCheckBox chckbxSalleDeTd = new JCheckBox("Salle de TD");
		frame.getContentPane().add(chckbxSalleDeTd, "4, 8");
		
		JCheckBox chckbxSalleDeTp = new JCheckBox("Salle de TP Chimie");
		frame.getContentPane().add(chckbxSalleDeTp, "4, 10");
		
		JCheckBox chckbxSalleDeTp_1 = new JCheckBox("Salle de TP Physique");
		frame.getContentPane().add(chckbxSalleDeTp_1, "4, 12");
		
		txtPlageHoraire = new JTextField();
		txtPlageHoraire.setText("Plage horaire : de");
		frame.getContentPane().add(txtPlageHoraire, "4, 14, fill, default");
		txtPlageHoraire.setColumns(10);
		
		JSpinner spinner = new JSpinner();
		frame.getContentPane().add(spinner, "4, 16, left, default");
		
		textField = new JTextField();
		textField.setText("à");
		frame.getContentPane().add(textField, "4, 18, fill, default");
		textField.setColumns(10);
		
		JSpinner spinner_1 = new JSpinner();
		frame.getContentPane().add(spinner_1, "4, 20, left, default");
		
		JButton btnRechercherUneSalle = new JButton("Rechercher une salle disponible");
		frame.getContentPane().add(btnRechercherUneSalle, "4, 24, center, default");
		

		
		
	}

}
