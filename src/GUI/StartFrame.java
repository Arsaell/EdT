package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import DATA.ClassType;
import DATA.DataStore;
import DATA.Field;
import DATA.Filler;
import DATA.Time;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;

public class StartFrame extends JFrame {

	public FieldPanel fp = new FieldPanel(this);
	public WeekPanel wp = new WeekPanel(this);
	public TeacherPanel tp = new TeacherPanel(this);
	public ClassroomPanel cp = new ClassroomPanel(this);
	public LinkPanel lp = new LinkPanel(this);
	public GroupPanel gp = new GroupPanel(this);
	public JTabbedPane tabbedPane;
	
	public StartFrame() {
		super();

		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Week", wp);
		tabbedPane.addTab("Fields", fp);
		tabbedPane.addTab("Teachers", tp);
		tabbedPane.addTab("Classrooms", cp);
		tabbedPane.addTab("Groups", gp);
		tabbedPane.addTab("Links", lp);
		tabbedPane.setSelectedIndex(0);
		
		this.allowTimeableTabs(false);
		JButton btTer = new JButton("Terminer");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//		btTer.addActionListener(new ActionListener()	{
//
//			public void actionPerformed(ActionEvent arg0) {
//				DataStore ds = new DataStore().setTypes(fp.getTypes()).setTeachers(tp.getTeachers()).setGroups(gp.getGroups()).setClassrooms(cp.getClassrooms()).setFields(fp.getFields());
//				new MainWindow(ds);
//			}
//			
//		});
		
		getContentPane().add(tabbedPane);
		getContentPane().add(btTer);
		this.setBounds(200, 200, 650, 500);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public ArrayList<Field> getFieldsList()	{
		return fp.getFields();
	}

	public ArrayList<ClassType> getTypes()	{
		return this.fp.getClassTypes();
	}
	
	public void allowTimeableTabs(Boolean bool) {
		
		this.tabbedPane.setEnabledAt(2, bool);
		this.tabbedPane.setEnabledAt(3, bool);
		this.tabbedPane.setEnabledAt(4, bool);
		
		if (!bool)	{
			this.tabbedPane.setToolTipTextAt(2, "Veuillez compléter l'onglet Week pour pouvoir éditer les professeurs.");
			this.tabbedPane.setToolTipTextAt(3, "Veuillez compléter l'onglet Week pour pouvoir éditer les classes.");
			this.tabbedPane.setToolTipTextAt(4, "Veuillez compléter l'onglet Week pour pouvoir éditer les groupes.");
		}
		
		else	{
			this.tabbedPane.setToolTipTextAt(2, null);
			this.tabbedPane.setToolTipTextAt(3, null);
			this.tabbedPane.setToolTipTextAt(4, null);
		}
	}
}
