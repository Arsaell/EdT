import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class GroupWindow {

	private JFrame frame;
	private JTextField textField;
	private JTable tableau;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupWindow window = new GroupWindow();
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
	public GroupWindow() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 345);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		
		//Arbre PCC
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("PCC");
		buildTree(top);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		JTree tree = new JTree(top);
		tree.setVisibleRowCount(15);
		tree.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent arg0) {
			}
			public void treeExpanded(TreeExpansionEvent arg0) {
				
				frame.setBounds(100, 100, 701, 345);
				frame.setBounds(100, 100, 700, 345);
			}
		});
		panel_1.setSize(100, 200);
		panel_1.add(tree);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		panel_1.add(scrollPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Informations générales", null, panel, null);
		panel.setLayout(new GridLayout(6, 2, 50, 20));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Nom du groupe");
		panel_3.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nombre d'élèves");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		lblNewLabel.setMaximumSize(new Dimension(75, 15));
		
		JSpinner spinner = new JSpinner();
		
		panel.add(spinner);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("");
		panel.add(label_2);
		
		JLabel label = new JLabel("");
		panel.add(label);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Matières", null, panel_2, null);
		
		tableau = new JTable();
		tableau.setPreferredSize(new Dimension(300, 700));
		panel_2.add(tableau);
	
		
	}
	
	private void buildTree(DefaultMutableTreeNode top){

		
		//Ajout de branches à la racine top
		      DefaultMutableTreeNode first = new DefaultMutableTreeNode("1A");
		      DefaultMutableTreeNode second = new DefaultMutableTreeNode("2A");
		      DefaultMutableTreeNode third = new DefaultMutableTreeNode("SHN");
		      
		 top.add(first);
		 top.add(second);
		 top.add(third);
		 
		 //Ajout de feuilles a la branche first
		 		DefaultMutableTreeNode rep10 = new DefaultMutableTreeNode("ASINSA");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 1; k < 4; k++)
		            rep10.add(new DefaultMutableTreeNode("9" + k));
		          
		 		DefaultMutableTreeNode rep11 = new DefaultMutableTreeNode("AMERINSA");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 1; k < 5; k++)
		            rep11.add(new DefaultMutableTreeNode("8" + k));
		          
		 		DefaultMutableTreeNode rep12 = new DefaultMutableTreeNode("EURINSA");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 1; k < 5; k++)
		            rep12.add(new DefaultMutableTreeNode("10" + k));
		          
		 		DefaultMutableTreeNode rep13 = new DefaultMutableTreeNode("FAS");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 0; k < 1; k++)
		            rep13.add(new DefaultMutableTreeNode("3" + k));
		 		
		 		DefaultMutableTreeNode rep14 = new DefaultMutableTreeNode("LANIERE A");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 1; k < 5; k++)
		            rep14.add(new DefaultMutableTreeNode("" + k));
		 
		 		DefaultMutableTreeNode rep15 = new DefaultMutableTreeNode("LANIERE B");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 5; k < 9; k++)
		            rep15.add(new DefaultMutableTreeNode(k));
		          
		 		DefaultMutableTreeNode rep16 = new DefaultMutableTreeNode("LANIERE C");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 9; k < 13; k++)
		            rep16.add(new DefaultMutableTreeNode( k));
		          
		 		DefaultMutableTreeNode rep17 = new DefaultMutableTreeNode("LANIERE D");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 3; k < 7; k++)
		            rep17.add(new DefaultMutableTreeNode("1" + k));
		          
		 		DefaultMutableTreeNode rep18 = new DefaultMutableTreeNode("SCAN");
		 		//Cette fois, on ajoute les feuilles
		          for(int k = 0; k < 2; k++)
		            rep18.add(new DefaultMutableTreeNode("6" + k));
		 		
		 first.add(rep10);
		 first.add(rep11);
		 first.add(rep12);
		 first.add(rep13);
		 first.add(rep14);
		 first.add(rep15);
		 first.add(rep16);
		 first.add(rep17);
		 first.add(rep18);
	 		
		//Ajout de feuilles a la branche second
	 		DefaultMutableTreeNode rep20 = new DefaultMutableTreeNode("ASINSA");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 6; k < 9; k++)
	            rep20.add(new DefaultMutableTreeNode("9" + k));
	          
	 		DefaultMutableTreeNode rep21 = new DefaultMutableTreeNode("AMERINSA");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 6; k < 9; k++)
	            rep21.add(new DefaultMutableTreeNode("8" + k));
	          
	 		DefaultMutableTreeNode rep22 = new DefaultMutableTreeNode("EURINSA");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 1; k < 5; k++)
	            rep22.add(new DefaultMutableTreeNode("20" + k));
	          
	 		DefaultMutableTreeNode rep23 = new DefaultMutableTreeNode("FAS");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 1; k < 2; k++)
	            rep23.add(new DefaultMutableTreeNode("3" + k));
	          
	 		DefaultMutableTreeNode rep24 = new DefaultMutableTreeNode("LANIERE K");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 1; k < 5; k++)
	            rep24.add(new DefaultMutableTreeNode("4" + k));
	          
	 		DefaultMutableTreeNode rep25 = new DefaultMutableTreeNode("LANIERE L");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 5; k < 9; k++)
	            rep25.add(new DefaultMutableTreeNode("4" + k));
	          
	 		DefaultMutableTreeNode rep26 = new DefaultMutableTreeNode("LANIERE M");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 49; k < 53; k++)
	            rep26.add(new DefaultMutableTreeNode(k));
	          
	 		DefaultMutableTreeNode rep27 = new DefaultMutableTreeNode("LANIERE N");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 3; k < 7; k++)
	            rep27.add(new DefaultMutableTreeNode("5" + k));
	          
	 		DefaultMutableTreeNode rep28 = new DefaultMutableTreeNode("SCAN");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 1; k < 5; k++)
	            rep28.add(new DefaultMutableTreeNode("" + k));
	 		
	 second.add(rep20);
	 second.add(rep21);
	 second.add(rep22);
	 second.add(rep23);
	 second.add(rep24);
	 second.add(rep25);
	 second.add(rep26);
	 second.add(rep27);
	 second.add(rep28);
	 
	
		//Ajout de feuilles a la branche third
	 		DefaultMutableTreeNode rep30 = new DefaultMutableTreeNode("SHN 0");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 5; k < 6; k++)
	            rep30.add(new DefaultMutableTreeNode("3" + k));
	          
	 		DefaultMutableTreeNode rep31 = new DefaultMutableTreeNode("SHN 1");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 6; k < 7; k++)
	            rep31.add(new DefaultMutableTreeNode("3" + k));
	          
	 		DefaultMutableTreeNode rep32 = new DefaultMutableTreeNode("SHN 2");
	 		//Cette fois, on ajoute les feuilles
	          for(int k = 7; k < 8; k++)
	            rep32.add(new DefaultMutableTreeNode("3" + k));
	 		
	 	third.add(rep30);
	 	third.add(rep31);
	 	third.add(rep32);
	 	

		}
//Je suis bloqué ici, je n'arrive pas à afficher le tableau des matières et professeurs dans le jpanel_2 qui correspond à l'onglet matières
//je te laisse regarder
	public void buildJTable() {
		
		//Les titres des colonnes
		String[] columnNames = {"Matières",
                				"Professeurs"};
		
		//Les données du tableau
		Object[][] data = {
				{"Maths","Boveto"},
				{"",""}
				};
	
	JTable tableau = new JTable(data, columnNames);
	
	}
}

