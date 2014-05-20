package GUI;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.AbstractListModel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class LinkPanel extends JPanel {

	private StartFrame container;
	
	public LinkPanel(StartFrame aContainer) {

		super();
		setBorder(new EmptyBorder(23, 23, 23, 23));
		this.container = aContainer;
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane);
		
		JPanel pane = new JPanel();
		pane.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Liens existants", TitledBorder.LEFT, TitledBorder.TOP, null, null), new EmptyBorder(15, 5, 15, 5)));
		pane.setLayout(new BorderLayout(0, 0));
		splitPane.setLeftComponent(pane);
		JPanel panel = new JPanel();
		JScrollPane scrollPane_3 = new JScrollPane(panel);
		pane.add(scrollPane_3, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Twilight Sparkle", "Rarity", "Rainbow Dash", "Apple Jack"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list, BorderLayout.WEST);
		
		JList list_1 = new JList();
		list_1.setModel(new AbstractListModel() {
			String[] values = new String[] {"LanIP", "g46", "g42", "g2116"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list_1, BorderLayout.CENTER);
		
		JList list_2 = new JList();
		list_2.setModel(new AbstractListModel() {
			String[] values = new String[] {"Maths TD", "Maths Cours", "TP Physique", "Physique Cours"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list_2, BorderLayout.EAST);
		
		JButton btnRemove = new JButton("Enlever");
		pane.add(btnRemove, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Nouveau Lien", TitledBorder.LEFT, TitledBorder.TOP, null, null), new EmptyBorder(15, 5, 15, 5)));
		splitPane.setRightComponent(panel_1);
		splitPane.setDividerLocation(200);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(600, 50));
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGroupes = new JLabel("Groupes");
		panel_2.add(lblGroupes, BorderLayout.WEST);
		lblGroupes.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblEnseignants = new JLabel("Enseignants");
		panel_2.add(lblEnseignants, BorderLayout.CENTER);
		lblEnseignants.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMatieres = new JLabel("Matières");
		panel_2.add(lblMatieres, BorderLayout.EAST);
		lblMatieres.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));
		
		JList list_3 = new JList();
		panel_3.add(list_3);
		
		JList list_4 = new JList();
		panel_3.add(list_4);
		
		JList list_5 = new JList();
		panel_3.add(list_5);
		
		JPanel panel_4 = new JPanel();
		panel_4.setMaximumSize(new Dimension(500, 50));
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JButton btnditer = new JButton("Éditer");
		panel_4.add(btnditer, BorderLayout.WEST);
		
		JButton btnAjouter = new JButton("Ajouter");
		panel_4.add(btnAjouter, BorderLayout.EAST);
		
		JLabel label = new JLabel("");
		panel_1.add(label);
	}

}
