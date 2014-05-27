package GUI;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import javax.swing.AbstractListModel;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import DATA.Field;
import DATA.Link;
import DATA.Teacher;
import DATA.Group;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LinkPanel extends JPanel {

	private int selRow;
	private StartFrame container;
	private ArrayList<Link> links;
	private JList<Teacher> lTeach, listTeach;
	private JList<Group> lGroup, listGroup;
	private JList<Field> lField,listField;
	private JButton btnEnlever, btnEditer, btnAjouter;
	private JPanel panel;

	public LinkPanel(StartFrame aContainer) {

		super();
		setBorder(new EmptyBorder(23, 23, 23, 23));
		setLayout(new GridLayout(0, 1, 0, 0));

		this.container = aContainer;
		this.links = new ArrayList<Link>();
		this.lTeach = new JList<Teacher>();
		this.listTeach = new JList<Teacher>();
		this.lField = new JList<Field>();
		this.listField = new JList<Field>();
		this.lGroup = new JList<Group>();
		this.listGroup = new JList<Group>();
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane);

		JPanel pane = new JPanel();
		pane.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Liens existants",
				TitledBorder.LEFT, TitledBorder.TOP, null, null),
				new EmptyBorder(15, 5, 15, 5)));
		pane.setLayout(new BorderLayout(0, 0));
		splitPane.setLeftComponent(pane);
		
		this.panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		pane.add(scrollPane, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		setUpLinksLists();
		
		this.btnEnlever = new JButton("Enlever");
		btnEnlever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				links.remove(selRow);
				setUpLinksLists();
				checkEnableButtons();
			}
		});
		pane.add(btnEnlever, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(
				new Color(184, 207, 229)), "Nouveau Lien", TitledBorder.LEFT,
				TitledBorder.TOP, null, null), new EmptyBorder(15, 5, 15, 5)));
		splitPane.setRightComponent(panel_1);
		splitPane.setDividerLocation(200);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		panel_2.setMaximumSize(new Dimension(600, 50));
		panel_1.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblEnseignants = new JLabel("	Enseignants");
		panel_2.add(lblEnseignants, BorderLayout.WEST);
		lblEnseignants.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblGroupes = new JLabel("Groupes");
		panel_2.add(lblGroupes, BorderLayout.CENTER);
		lblGroupes.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblMatieres = new JLabel("Matières	");
		panel_2.add(lblMatieres, BorderLayout.EAST);
		lblMatieres.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));

		this.listTeach = new JList();
		listTeach.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				checkEnableButtons();
			}
		});
		listTeach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTeach.setModel(new AbstractListModel() {
			String[] values = new String[] { "Twilight Sparkle", "Rarity",
					"Rainbow Dash", "Apple Jack" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel_3.add(listTeach);

		this.listGroup = new JList();
		listGroup.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				checkEnableButtons();
			}
		});
		
		listGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listGroup.setModel(new AbstractListModel() {
			String[] values = new String[] { "LanIP", "g46", "g42", "g2116" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		panel_3.add(listGroup);

		this.listField = new JList();
		listField.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				checkEnableButtons();
			}
		});
		
		listField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listField.setModel(new AbstractListModel() {
			String[] values = new String[] { "Maths TD", "Maths Cours",
					"TP Physique", "Physique Cours" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		panel_3.add(listField);

		JPanel panel_4 = new JPanel();
		panel_4.setMaximumSize(new Dimension(500, 50));
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		this.btnEditer = new JButton("Éditer");
		btnEditer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Link newLink = new Link((Teacher)listTeach.getSelectedValue(), (Group)listGroup.getSelectedValue(), (Field)listField.getSelectedValue());
				links.remove(selRow);
				links.add(selRow, newLink);
				
				setUpLinksLists();
				checkEnableButtons();
			}
		});
		panel_4.add(btnEditer, BorderLayout.WEST);

		this.btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Link newLink = new Link((Teacher)listTeach.getSelectedValue(), (Group)listGroup.getSelectedValue(), (Field)listField.getSelectedValue());
				links.add(newLink);
				setUpLinksLists();
				checkEnableButtons();
			}
		});
		panel_4.add(btnAjouter, BorderLayout.EAST);

		JLabel label = new JLabel("");
		panel_1.add(label);
		checkEnableButtons();
	}

	private void setUpLinksLists() {

		this.lTeach = new JList<Teacher>();
		lTeach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lTeach.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selRow = lTeach.getSelectedIndex();
				//System.out.println("lTeach " + selRow);
				lGroup.setSelectedIndex(selRow);
				lField.setSelectedIndex(selRow);
				listTeach.setSelectedValue(lTeach.getSelectedValue(), true);
				listTeach.grabFocus();
				listTeach.requestFocus();
				checkEnableButtons();
			}
		});
		lTeach.setModel(new AbstractListModel<Teacher>() {
			
			public int getSize() {
				return container.ds.getTeachers().size();
			}

			public Teacher getElementAt(int index) {
				return container.ds.getTeachers().get(index);
			}
		});
		panel.add(lTeach, BorderLayout.WEST);

		this.lGroup = new JList<Group>();
		lGroup.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lGroup.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selRow = lGroup.getSelectedIndex();
				//System.out.println("lGroup " + selRow);
				lTeach.setSelectedIndex(selRow);
				lField.setSelectedIndex(selRow);
				listGroup.setSelectedValue(lGroup.getSelectedValue(), true);
				listGroup.grabFocus();
				listGroup.requestFocus();
				checkEnableButtons();
			}
		});

		lGroup.setModel(new AbstractListModel<Group>() {

			public int getSize() {
				return container.ds.getGroups().size();
			}

			public Group getElementAt(int index) {
				return container.ds.getGroups().get(index);
			}
		});
		panel.add(lGroup, BorderLayout.CENTER);

		this.lField = new JList<Field>();
		lField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lField.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selRow = lField.getSelectedIndex();
				//System.out.println("lField " + selRow);
				lTeach.setSelectedIndex(selRow);
				lGroup.setSelectedIndex(selRow);
				listField.setSelectedValue(lField.getSelectedValue(), true);
				listField.grabFocus();
				listField.requestFocus();
				checkEnableButtons();
			}
		});

		lField.setModel(new AbstractListModel<Field>() {
			
			public int getSize() {
				return container.ds.getFields().size();
			}

			public Field getElementAt(int index) {
				return container.ds.getFields().get(index);
			}
		});
		panel.add(lField, BorderLayout.EAST);
		
		if (links.size() == 0)	{
			
			lGroup.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

			lTeach.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

			lField.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

			listTeach.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

			listGroup.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

			listField.setModel(new AbstractListModel() {

				String[] values = new String[]{"Va", "te", "faire", "voir"};
				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});
			
		}

	}
	
	private void checkEnableButtons()	{
		
		//System.out.println("btnEnlever : " + lTeach.getSelectedIndex() + " " + lGroup.getSelectedIndex() + " " + lField.getSelectedIndex());
		btnEnlever.setEnabled(lTeach.getSelectedIndex() != -1 && lGroup.getSelectedIndex() != -1 && lField.getSelectedIndex() != -1);
		
		//System.out.println("btnEditer : " + listTeach.getSelectedValue() + " " + listGroup.getSelectedValue() + " " + listField.getSelectedValue());
		btnEditer.setEnabled(lTeach.getSelectedIndex() != -1 && lGroup.getSelectedIndex() != -1 && lField.getSelectedIndex() != -1 && (listTeach.getSelectedValue() != lTeach.getSelectedValue() || listGroup.getSelectedValue() != lGroup.getSelectedValue() || listField.getSelectedValue() != lField.getSelectedValue()));
		
		//System.out.println("btnAjouter");
		btnAjouter.setEnabled(listTeach.getSelectedIndex() != -1 && listGroup.getSelectedIndex() != -1 && listField.getSelectedIndex() != -1);
	}
	
}
