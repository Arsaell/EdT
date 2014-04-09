package GUI;

import DATA.DataStore;
import DATA.Field;
import DATA.Teacher;
import DATA.Time;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.JTabbedPane;

import java.awt.Insets;

import javax.swing.AbstractListModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class TeacherWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	
	private DataStore dataStore;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherWindow window = new TeacherWindow();
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
	public TeacherWindow() {
		initialize();
		Teacher[] teacherss = new Teacher[5];
		
		//teacherss[0]= new Teacher(1, "test", null, new Time(2500));
		
		this.dataStore = new DataStore();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel teacherListPanel = new JPanel();
		teacherListPanel.setPreferredSize(new Dimension(200, 200));
		frame.getContentPane().add(teacherListPanel, BorderLayout.WEST);
		GridBagLayout gbl_teacherListPanel = new GridBagLayout();
		gbl_teacherListPanel.columnWidths = new int[] {0, 1};
		gbl_teacherListPanel.rowHeights = new int[] {0, 0, 0, 1};
		gbl_teacherListPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_teacherListPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		teacherListPanel.setLayout(gbl_teacherListPanel);
		
		JLabel lblListeDesEnseignants = new JLabel("Liste des enseignants :");
		GridBagConstraints gbc_lblListeDesEnseignants = new GridBagConstraints();
		gbc_lblListeDesEnseignants.anchor = GridBagConstraints.WEST;
		gbc_lblListeDesEnseignants.insets = new Insets(0, 0, 5, 0);
		gbc_lblListeDesEnseignants.gridx = 0;
		gbc_lblListeDesEnseignants.gridy = 0;
		teacherListPanel.add(lblListeDesEnseignants, gbc_lblListeDesEnseignants);
		
		JList<Teacher> list = new JList<Teacher>(new TeacherListModel(dataStore.getTeachers()));
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		teacherListPanel.add(list, gbc_list);
		
		JButton addTeacherBtn = new JButton("Ajouter un enseignant");
		GridBagConstraints gbc_addTeacherBtn = new GridBagConstraints();
		gbc_addTeacherBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addTeacherBtn.gridx = 0;
		gbc_addTeacherBtn.gridy = 2;
		teacherListPanel.add(addTeacherBtn, gbc_addTeacherBtn);
		
		JTabbedPane teacherTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(teacherTabbedPane, BorderLayout.CENTER);
		
		JPanel infoPanel = new JPanel();
		teacherTabbedPane.addTab("Informations générales", null, infoPanel, null);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		infoPanel.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNom = new JLabel("Nom :");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panel.add(lblNom, gbc_lblNom);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblPrnom = new JLabel("Prénom :");
		GridBagConstraints gbc_lblPrnom = new GridBagConstraints();
		gbc_lblPrnom.anchor = GridBagConstraints.EAST;
		gbc_lblPrnom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrnom.gridx = 0;
		gbc_lblPrnom.gridy = 1;
		panel.add(lblPrnom, gbc_lblPrnom);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 1;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JLabel lblAdresseEmail = new JLabel("Adresse email :");
		GridBagConstraints gbc_lblAdresseEmail = new GridBagConstraints();
		gbc_lblAdresseEmail.anchor = GridBagConstraints.EAST;
		gbc_lblAdresseEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblAdresseEmail.gridx = 0;
		gbc_lblAdresseEmail.gridy = 2;
		panel.add(lblAdresseEmail, gbc_lblAdresseEmail);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		infoPanel.add(panel_1, BorderLayout.SOUTH);
		
		JButton deleteTeacherBtn = new JButton("Supprimer l'enseignant");
		panel_1.add(deleteTeacherBtn);
		
		JButton saveTeachBtn = new JButton("Enregistrer les modifications");
		panel_1.add(saveTeachBtn);
		
		JPanel fieldPanel = new JPanel();
		teacherTabbedPane.addTab("Matières enseignées", null, fieldPanel, null);
		fieldPanel.setLayout(new BorderLayout(0, 0));
		
		JTable table = getJTableField();
		fieldPanel.add(new JScrollPane(table));
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		mnFichier.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnFichier);
		
		JMenuItem mntmImporterDesEnseignants = new JMenuItem("Importer des enseignants...");
		mntmImporterDesEnseignants.setBackground(Color.WHITE);
		mnFichier.add(mntmImporterDesEnseignants);
		
		JMenuItem mntmExporterDesEnseignants = new JMenuItem("Exporter la liste...");
		mntmExporterDesEnseignants.setBackground(Color.WHITE);
		mnFichier.add(mntmExporterDesEnseignants);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setBackground(Color.WHITE);
		mnFichier.add(mntmQuitter);
	}
	
	private JTable getJTableField() {
		String[] columnNames = {"Matière", "Groupe"};
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Maths amphi");
		comboBox.addItem("Maths TD");
		comboBox.addItem("Physique amphi");
		comboBox.addItem("Physique TD");
		comboBox.addItem("Physique TP");
		Object[][] data = {{" ", " "}};
		JTable table = new JTable(new FieldTableModel());
		/*table.setRowSelectionAllowed(false);
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(comboBox));*/
		return table;
	}

	
	public class TeacherListModel extends AbstractListModel<Teacher> {

		private Teacher[] data;
		
		public TeacherListModel(Teacher[] teachers) {
			this.data = teachers;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return data.length;
		}

		public Teacher getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.data[index];
		}
		
	}
	
	public class FieldTableModel extends AbstractTableModel {
	    private final Field[] fields;
	 
	    private final String[] entetes = {"Matière", "Groupe"};
	 
	    public FieldTableModel() {
	        super();
	 
	        fields = new Field[]{
	                new Field(1, 't', "Physique TD")
	        };
	    }
	 
	    public int getRowCount() {
	        return fields.length;
	    }
	 
	    public int getColumnCount() {
	        return entetes.length;
	    }
	 
	    public String getColumnName(int columnIndex) {
	        return entetes[columnIndex];
	    }
	 
	    public Object getValueAt(int rowIndex, int columnIndex) {
	        switch(columnIndex){
	            case 0:
	                return fields[rowIndex].getName();
	            default:
	                return null; //Ne devrait jamais arriver
	        }
	    }
	}
}


