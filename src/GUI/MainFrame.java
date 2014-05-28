package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import DATA.Classroom;
import DATA.DataStore;
import DATA.Filler;
import DATA.Teacher;
import DATA.WeekTable;
import GUI.TeacherPanel.TeacherListModel;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JToolBar;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.JLabel;

public class MainFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private DataStore dataStore;
	private JButton btnSave;
	private JButton btnOpen;
	private JButton btnNew;
	private JButton btnTeachers;
	private AbstractButton btnClassrooms;
	private JButton btnGroups;
	private JButton btnFields;
	private JButton btnWeekTable;
	private JButton btnFillFrame;
	private JPanel listPanel;
	private JPanel EdTPanel;
	private JLabel teacherLabel;
	
	private Filler filler;
	
	public MainFrame(Filler filler, DataStore ds) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		this.dataStore = ds;
		filler = new Filler(ds);
		filler.fill(filler.computeConstraints(true), Filler.IGNORE);
		
		contentPane = new ContentPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setResizable(false);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setMargin(new Insets(5, 5, 5, 5));
		toolBar.setBackground(new Color(230, 230, 230));
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		try {
			btnSave = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/disk.png"))));
			btnSave.setText("Enregistrer");
			
			btnOpen = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/folder.png"))));
			btnOpen.setText("Ouvrir");
			
			btnNew = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/table_add.png"))));
			btnNew.setText("Nouveau");

			btnTeachers = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/user_red.png"))));
			btnTeachers.setText("Enseignants");
			btnTeachers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new TeacherPanel(dataStore));
					frame.setVisible(true);
				}
			});
			
			btnClassrooms = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/door_open.png"))));
			btnClassrooms.setText("Salles");
			btnClassrooms.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new ClassroomPanel(dataStore));
					frame.setVisible(true);
				}
			});

			btnGroups = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/group.png"))));
			btnGroups.setText("Groupes");
			btnGroups.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new GroupPanel(dataStore));
					frame.setVisible(true);
				}
			});
			
			btnFields = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/book_open.png"))));
			btnFields.setText("Matières");
			btnFields.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame frame = new JFrame();
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new FieldPanel(dataStore));
					frame.setVisible(true);
				}
			});
			
			btnWeekTable = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/table.png"))));
			btnWeekTable.setText("Emploi du temps");
			btnWeekTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(btnWeekTable.isSelected()) {
						EdTPanel.setVisible(false);
						listPanel.setVisible(false);
						btnWeekTable.setSelected(false);
						teacherLabel.setVisible(false);
					} else {
						EdTPanel.setVisible(true);
						listPanel.setVisible(true);
						btnWeekTable.setSelected(true);
						teacherLabel.setVisible(true);
						
					}
				}
				
			});
			
			btnFillFrame = new JButton(new ImageIcon(ImageIO.read(new File("img/icons/book_open.png"))));
			btnFillFrame.setText("Construire l'emploi du temps");
			btnFillFrame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/*JFrame frame = new 
					frame.setBounds(100, 100, 750, 550);
					frame.getContentPane().add(new FieldPanel(dataStore));
					frame.setVisible(true);*/
				}
			});
			


		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		toolBar.add(btnNew);
		toolBar.add(btnOpen);
		toolBar.add(btnSave);
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		Dimension size = new Dimension(
			    separator.getPreferredSize().width,
			    separator.getMaximumSize().height);
		separator.setMaximumSize(size);
		toolBar.add(separator);
		toolBar.add(btnTeachers);
		toolBar.add(btnClassrooms);
		toolBar.add(btnGroups);
		toolBar.add(btnFields);
		JSeparator separator_1 = new JSeparator(JSeparator.VERTICAL);
		separator_1.setMaximumSize(size);
		toolBar.add(separator_1);
		toolBar.add(btnWeekTable);
		
		
		JPanel leftContainer = new JPanel();
		
		EdTPanel = new JPanel();
		getContentPane().add(EdTPanel, BorderLayout.CENTER);
		EdTPanel.setVisible(false);
		
		listPanel = new ListPanel(dataStore, this.EdTPanel);
		leftContainer.add(listPanel);
		FlowLayout fl_leftContainer = new FlowLayout(FlowLayout.LEFT, 5, 5);
		leftContainer.setLayout(fl_leftContainer);
		teacherLabel = new JLabel("Enseignants :");
		leftContainer.add(teacherLabel);
		
		getContentPane().add(leftContainer, BorderLayout.WEST);
		listPanel.setVisible(false);
		teacherLabel.setVisible(false);
		
		
	}
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == this.mntmTeachers) {
//			TeacherPanel teacherWindow = new TeacherPanel(this, this.dataStore);
//			teacherWindow.getRootPane().setVisible(true);
//		}
		
	}
	
	class ContentPane extends JPanel {
		private Image img;

		public ContentPane() {
			this.img = new ImageIcon("edt.png").getImage();
			Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
			setPreferredSize(size);
			setMinimumSize(size);
			setMaximumSize(size);
			setSize(size);
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, this.getWidth() / 2 - this.img.getWidth(null) / 2, this.getHeight() / 2 - this.img.getHeight(null) / 2, null);
		}
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
	
	public class ClassroomListModel extends AbstractListModel<Classroom> {

		private ArrayList<Classroom> classrooms;
		
		public ClassroomListModel(ArrayList<Classroom> classrooms) {
			this.classrooms = classrooms;
		}
		
		public int getSize() {
			// TODO Auto-generated method stub
			return this.classrooms.size();
		}

		public Classroom getElementAt(int index) {
			// TODO Auto-generated method stub
			return this.classrooms.get(index);
		}

		public void addElement(Classroom newClassroom) {
			this.classrooms.add(newClassroom);
			fireContentsChanged(this, 0, classrooms.size());
		}
	}

	class ListPanel extends JPanel implements ListSelectionListener {
		
		private ArrayList<Teacher> teachers;
		private JList<Teacher> teacherList;
		private TeacherListModel teacherListModel;
		private JPanel EdTPanel;
		private Teacher selectedTeacher;
		
		public ListPanel(DataStore dataStore, JPanel EdTPanel) {
			
			this.EdTPanel = EdTPanel;
			this.teachers = dataStore.getTeachers();
			this.teacherListModel = new TeacherListModel(dataStore.getTeachers());
			
			teacherList = new JList<Teacher>(this.teacherListModel);
			teacherList.setPreferredSize(new Dimension(180, 800));
			teacherList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			teacherListModel.sort();
			teacherList.addListSelectionListener(this);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.insets = new Insets(0, 0, 5, 0);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 0;
			gbc_list.gridy = 1;
			this.add(new JScrollPane(teacherList), BorderLayout.CENTER);
			
			this.setPreferredSize(new Dimension(200, 800));
		}

		public void valueChanged(ListSelectionEvent e) {
			// Si un élément de la liste est sélectionné.
			if(!e.getValueIsAdjusting() && teacherList.getSelectedValuesList().size() > 0) {
				// On affiche les champs.
				
				// On y place les bonnes infos.
				selectedTeacher = teacherList.getSelectedValue();
				System.out.println(selectedTeacher);
				// On affiche l'emploi du temps du prof
				EdTPanel.setMinimumSize(new Dimension(500, 500));
				EdTPanel.add(new EdTViewerPanel(selectedTeacher.getWeekTable()));
			}
		}
	}
}
