package GUI;

import DATA.DataStore;
import DATA.Field;
import DATA.Teacher;
import DATA.Time;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Component;
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
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.ListSelectionModel;

import java.awt.event.KeyAdapter;


public class TeacherWindow implements ActionListener, KeyListener, ListSelectionListener{

	private JFrame frame;
	private JPanel infoPanel;
	private JPanel infosPanel;
	private JPanel disabledPanel;
	private JButton deleteTeacherBtn;
	private JTextField teacherLastNameField;
	private JTextField teacherFirstNameField;
	private JLabel teacherMailLabel;
	private JTable table;
	private JList<Teacher> teacherList;
	
	private TeacherListModel teacherListModel;
	private Teacher selectedTeacher;
	
	private DataStore dataStore;

	/**
	 * Create the application.
	 */
	public TeacherWindow(DataStore dataStore) {
		this.dataStore = dataStore;
		this.teacherListModel = new TeacherListModel(this.dataStore.getTeachers());
		initialize();
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
		
		teacherList = new JList<Teacher>(this.teacherListModel);
		teacherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		teacherListModel.sort();
		teacherList.addListSelectionListener(this);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		teacherListPanel.add(teacherList, gbc_list);
		
		JButton addTeacherBtn = new JButton("Ajouter un enseignant");
		addTeacherBtn.setActionCommand("addTeacher");
		addTeacherBtn.addActionListener(this);
		GridBagConstraints gbc_addTeacherBtn = new GridBagConstraints();
		gbc_addTeacherBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_addTeacherBtn.gridx = 0;
		gbc_addTeacherBtn.gridy = 2;
		teacherListPanel.add(addTeacherBtn, gbc_addTeacherBtn);
		
		JTabbedPane teacherTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(teacherTabbedPane, BorderLayout.CENTER);
		
		infoPanel = new JPanel();
		teacherTabbedPane.addTab("Informations générales", null, infoPanel, null);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		disabledPanel = new JPanel();
		disabledPanel.setLayout(new BorderLayout(0,0));
		disabledPanel.setEnabled(true);
		infoPanel.add(disabledPanel, BorderLayout.CENTER);
		
		JLabel disabledLabel = new JLabel("Sélectionnez un enseignant, ou ajoutez-en un.");
		disabledLabel.setHorizontalAlignment(SwingConstants.CENTER);
		disabledPanel.add(disabledLabel, BorderLayout.CENTER);
		
		infosPanel = new JPanel();
		infosPanel.setEnabled(false);
		//infoPanel.add(infosPanel, BorderLayout.CENTER);
		GridBagLayout gbl_infosPanel = new GridBagLayout();
		gbl_infosPanel.columnWidths = new int[]{0, 0, 0};
		gbl_infosPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_infosPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_infosPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		infosPanel.setLayout(gbl_infosPanel);
		
		JLabel lblNom = new JLabel("Nom :");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		infosPanel.add(lblNom, gbc_lblNom);
		
		teacherLastNameField = new JTextField();
		teacherLastNameField.addKeyListener(this);
		teacherLastNameField.addActionListener(this);
		GridBagConstraints gbc_teacherLastNameField = new GridBagConstraints();
		gbc_teacherLastNameField.insets = new Insets(0, 0, 5, 0);
		gbc_teacherLastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_teacherLastNameField.gridx = 1;
		gbc_teacherLastNameField.gridy = 0;
		infosPanel.add(teacherLastNameField, gbc_teacherLastNameField);
		teacherLastNameField.setColumns(10);
		
		JLabel lblPrnom = new JLabel("Prénom :");
		GridBagConstraints gbc_lblPrnom = new GridBagConstraints();
		gbc_lblPrnom.anchor = GridBagConstraints.EAST;
		gbc_lblPrnom.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrnom.gridx = 0;
		gbc_lblPrnom.gridy = 1;
		infosPanel.add(lblPrnom, gbc_lblPrnom);
		
		teacherFirstNameField = new JTextField();
		teacherFirstNameField.addActionListener(this);
		teacherFirstNameField.addKeyListener(this);
		GridBagConstraints gbc_teacherFirstNameField = new GridBagConstraints();
		gbc_teacherFirstNameField.insets = new Insets(0, 0, 5, 0);
		gbc_teacherFirstNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_teacherFirstNameField.gridx = 1;
		gbc_teacherFirstNameField.gridy = 1;
		infosPanel.add(teacherFirstNameField, gbc_teacherFirstNameField);
		teacherFirstNameField.setColumns(10);
		
		JLabel lblAdresseEmail = new JLabel("Adresse email :");
		GridBagConstraints gbc_lblAdresseEmail = new GridBagConstraints();
		gbc_lblAdresseEmail.anchor = GridBagConstraints.EAST;
		gbc_lblAdresseEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblAdresseEmail.gridx = 0;
		gbc_lblAdresseEmail.gridy = 2;
		infosPanel.add(lblAdresseEmail, gbc_lblAdresseEmail);
		
		teacherMailLabel = new JLabel("");
		GridBagConstraints gbc_teacherMailLabel = new GridBagConstraints();
		gbc_teacherMailLabel.gridx = 1;
		gbc_teacherMailLabel.gridy = 2;
		infosPanel.add(teacherMailLabel, gbc_teacherMailLabel);
		
		JPanel panel_1 = new JPanel();
		infoPanel.add(panel_1, BorderLayout.SOUTH);
		
		deleteTeacherBtn = new JButton("Supprimer l'enseignant");
		deleteTeacherBtn.setVisible(false);
		panel_1.add(deleteTeacherBtn);
		
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
		
		System.out.println("Test");
	}
	
	private JTable getJTableField() {
		
		String[] columnNames = {"Matière"};
		
		Object[][] data = {{" ", " "}};
		JTable table = new JTable(new FieldTableModel(columnNames));
		
		return table;
	}
	
	public class TeacherListModel extends AbstractListModel<Teacher> {

		private ArrayList<Teacher> teachers;
		
		public TeacherListModel(ArrayList<Teacher> teachers) {
			this.teachers = teachers;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return this.teachers.size();
		}

		public Teacher getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.teachers.get(index);
		}
		
		public void sort() {
		    Collections.sort(teachers);
		    fireContentsChanged(this, 0, teachers.size());
		}

		public void addElement(Teacher newTeacher) {
			this.teachers.add(newTeacher);
			fireContentsChanged(this, 0, teachers.size());
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		// Si un élément de la liste est sélectionné.
		if(!e.getValueIsAdjusting() && teacherList.getSelectedValuesList().size() > 0) {
			// On affiche les champs.
			infoPanel.add(infosPanel, BorderLayout.CENTER);
			// On y place les bonnes infos.
			selectedTeacher = teacherList.getSelectedValue();
			teacherFirstNameField.setText(selectedTeacher.getFirstName());
			teacherLastNameField.setText(selectedTeacher.getLastName());
			teacherMailLabel.setText(selectedTeacher.getMail());
		} else {
			// Sinon, on affiche le message qui prie l'utilisateur de sélectionner un professeur.
			infoPanel.add(disabledPanel, BorderLayout.CENTER);	
		}
	}	
	
	public class FieldTableModel extends AbstractTableModel {
	    private final Field[] fields;
	    private String[] titles;
	    
	    public FieldTableModel(String[] titles) {
	        super();
	        this.titles = titles;
	        fields = new Field[]{ };
	    }
	 
	    public int getRowCount() {
	        return fields.length;
	    }
	 
	    public int getColumnCount() {
	        return titles.length;
	    }
	 
	    public String getColumnName(int columnIndex) {
	        return titles[columnIndex];
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
	
	public class ComboRenderer extends JComboBox implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {   
			this.addItem("Très bien");
			this.addItem("Bien");
			this.addItem("Mal");
			return this;
		}
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Si le bouton "Ajouter enseignant est cliqué".
		if(command.matches("addTeacher")) {
			Teacher newTeacher = new Teacher(0, "", "", new Field[]{}, new Time((byte)1, (byte)1, (byte)1));
			((TeacherListModel) teacherList.getModel()).addElement(newTeacher);
			teacherList.setSelectedValue(newTeacher, true);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		// On met à jour l'enseignant et on repaint la JList
		selectedTeacher = teacherList.getSelectedValue();
		selectedTeacher.setFirstName(teacherFirstNameField.getText()).setLastName(teacherLastNameField.getText());
		teacherMailLabel.setText(selectedTeacher.getMail());
		teacherList.repaint();
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}


