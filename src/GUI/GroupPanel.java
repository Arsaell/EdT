package GUI;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import DATA.Field;
import DATA.Group;
import DATA.Time;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EmptyBorder;

public class GroupPanel extends JPanel {
	
	private Vector<Group> groups;
	private HashMap<Group, DefaultMutableTreeNode> nodes;
	private HashMap<Field, Time> classes;	
	
	private StartFrame container;
	
	private Group parentSelected;
	
	private JTree groupsTree;
	private JSpinner hours;
	private JSpinner minutes;
	private JTextField groupName;
	private JList fieldsList;
	private JList classesList;
	private JButton addGroup;
	private JButton addClass;
	private JComboBox groupsCBox;
	private JSplitPane splitPane;

	public GroupPanel(StartFrame aContainer) {
		
		super();
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				
				groupsCBox = new JComboBox(groups.toArray());
				fieldsList.setListData(container.getFieldsList().toArray());
				classesList.setListData(classes.keySet().toArray());
				
				if (container.getFieldsList().size() == 0)
					fieldsList.setListData(new String[]{"Veuillez ajouter", "les matières", "dans l'onglet \"Fields\"."});
				if (classes.size() == 0)
					classesList.setListData(new String[]{"Veuillez sélectionner", "une matière", "dans la liste", "à droite."});
				
				splitPane.setDividerLocation(0.25);
			}
		});
		
		this.container = aContainer;
		
		this.groups = new Vector<Group>();
		this.classes = new HashMap<Field, Time>();
		this.nodes = new HashMap<Group, DefaultMutableTreeNode>();
//		classes.put(new Field(new ClassType("", "", new Slot(new Time(), new Time())), ""), new Time());
		this.setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		this.groupsTree = new JTree(new DefaultMutableTreeNode("All"));
		this.groupsTree.setRootVisible(false);
		scrollPane.setViewportView(groupsTree);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(15, 15, 15, 15));
		
		splitPane.setRightComponent(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {50, 200};
		gbl_panel.rowHeights = new int[] {50, 50, 46, 100, 50, 30};
		gbl_panel.columnWeights = new double[]{1.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.anchor = GridBagConstraints.EAST;
		gbc_lblNom.fill = GridBagConstraints.VERTICAL;
		gbc_lblNom.insets = new Insets(0, 0, 5, 5);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panel.add(lblNom, gbc_lblNom);
		
		this.groupName = new JTextField();
		groupName.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {
				checkAddGroupEnabled();
			}
			public void keyReleased(KeyEvent e) {
				checkAddGroupEnabled();
			}
		});
		
		GridBagConstraints gbc_groupName = new GridBagConstraints();
		gbc_groupName.insets = new Insets(0, 0, 5, 0);
		gbc_groupName.anchor = GridBagConstraints.WEST;
		gbc_groupName.ipadx = 99;
		gbc_groupName.gridx = 1;
		gbc_groupName.gridy = 0;
		panel.add(groupName, gbc_groupName);
		groupName.setColumns(10);
		
		JLabel lblParent = new JLabel("Parent");
		GridBagConstraints gbc_lblParent = new GridBagConstraints();
		gbc_lblParent.anchor = GridBagConstraints.EAST;
		gbc_lblParent.fill = GridBagConstraints.VERTICAL;
		gbc_lblParent.insets = new Insets(0, 0, 5, 5);
		gbc_lblParent.gridx = 0;
		gbc_lblParent.gridy = 1;
		panel.add(lblParent, gbc_lblParent);
		
		
		this.groupsCBox = new JComboBox(groups);
		groupsCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentSelected = (Group)((JComboBox)(e.getSource())).getSelectedItem();
				checkAddGroupEnabled();
			}
		});
		
		
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel.add(groupsCBox, gbc_comboBox);
		
		JLabel lblMatieres = new JLabel("Matières");
		GridBagConstraints gbc_lblMatieres = new GridBagConstraints();
		gbc_lblMatieres.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblMatieres.insets = new Insets(0, 0, 5, 5);
		gbc_lblMatieres.gridx = 0;
		gbc_lblMatieres.gridy = 3;
		panel.add(lblMatieres, gbc_lblMatieres);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new GridLayout(1, 3, 10, 10));
		

		this.fieldsList = new JList(this.container.getFieldsList().toArray());
		
		panel_1.add(fieldsList);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 2, 5, 5));
		
		this.hours = new JSpinner();
		hours.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		
		this.hours.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_2.add(hours);
		
		JLabel lblHeures = new JLabel("heures");
		panel_2.add(lblHeures);
		
		this.minutes = new JSpinner();
		minutes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		
		
		this.minutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		panel_2.add(minutes);
		
		JLabel lblMinutes = new JLabel("minutes");
		panel_2.add(lblMinutes);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(UIManager.getColor("Panel.background"));
		separator.setForeground(UIManager.getColor("Panel.background"));
		panel_2.add(separator);
		
		this.addClass = new JButton(">>>");
		addClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Field f = (Field)fieldsList.getSelectedValue();
				Time t = new Time((Integer)hours.getValue() * 100 + (Integer)minutes.getValue());
				classes.put(f, t);
				hours.setValue(0);
				minutes.setValue(0);
				fieldsList.setSelectedIndex(-1);
				classesList.setListData(classes.keySet().toArray());
				
				checkAddClassEnabled();
				checkAddGroupEnabled();
			}
		});
		panel_2.add(addClass);
		
		this.classesList = new JList(this.classes.keySet().toArray());
		//this.classesList = new JList(new String[]{"1", "2", "3", "4"});
		panel_1.add(classesList);
		
		JLabel label_1 = new JLabel(" ");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.fill = GridBagConstraints.BOTH;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 4;
		panel.add(label_1, gbc_label_1);
		
		this.addGroup = new JButton("Ajouter");
		
		this.addGroup.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				String name = groupName.getText();
				Group parent = parentSelected;
				Group g = new Group(name, 0).setClasses(classes).setParent(parent);
				groups.add(g);
				//System.out.println(name + " " + parent + " " + g + " " + g.classes + groups);
				classes = new HashMap<Field, Time>();
				groupName.setText("");
				
				groupsCBox.addItem(g);
				
				DefaultMutableTreeNode tn = new DefaultMutableTreeNode(g);
				nodes.put(g, tn);
				
				if (g.getParent() == null)	{
					groupsTree = new JTree(tn);
					groupsTree.setRootVisible(false);
					splitPane.setLeftComponent(new JScrollPane(groupsTree));
					splitPane.setDividerLocation(0.25);
				}
				else
					nodes.get(g.getParent()).add(tn);
				
				//groupsTree.collapsePath(null);
				groupsTree.expandPath(new TreePath(tn.getPath()));
				groupsTree.scrollPathToVisible(new TreePath(tn.getPath()));
				checkAddGroupEnabled();
				checkAddClassEnabled();
			}
		});
		
		GridBagConstraints gbc_addGroup = new GridBagConstraints();
		gbc_addGroup.insets = new Insets(0, 0, 5, 0);
		gbc_addGroup.anchor = GridBagConstraints.EAST;
		gbc_addGroup.gridx = 1;
		gbc_addGroup.gridy = 4;
		panel.add(addGroup, gbc_addGroup);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{groupName, hours, minutes, addClass}));
		
		
		checkAddGroupEnabled();
		checkAddClassEnabled();
		
	}

	private void checkAddGroupEnabled()	{
		//System.out.println("Group");
		this.addGroup.setEnabled(this.groupName.getText().length() > 0 && this.classes.size() > 0 && (parentSelected != null || this.groups.size() == 0));
	}
	
	private void checkAddClassEnabled()	{
		//System.out.println("Class");
		this.addClass.setEnabled(container.getFieldsList().size() != 0 && this.fieldsList.getSelectedIndex() != -1 && ((Integer)this.hours.getValue() + (Integer)this.minutes.getValue() > 0));
	}
	
	public ArrayList<Group> getGroups()	{
		
		ArrayList<Group> temp = new ArrayList<Group>();
		for (Group g : this.groups)
			temp.add(g);
		return temp;
	}
}
