package GUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.border.EmptyBorder;
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

	private DataStore ds;
	public FieldPanel fp;
	public WeekPanel wp;
	public TeacherPanel tp;
	public ClassroomPanel cp;
	public LinkPanel lp;
	public GroupPanel gp;
	public JTabbedPane tabbedPane;
	
	public StartFrame() {
		super();

		this.ds = new DataStore();
		
		this.fp = new FieldPanel(this);
		this.wp = new WeekPanel(this);
		this.tp = new TeacherPanel(this, this.ds);
		this.cp = new ClassroomPanel(this);
		this.lp = new LinkPanel(this);
		this.gp = new GroupPanel(this);
		
		
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
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public DataStore getDS()	{
		return this.ds;
	}
	
	public ArrayList<Field> getFieldsList()	{
		return this.ds.getFields();
	}

	public ArrayList<ClassType> getTypes()	{
		return this.ds.getTypes();
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
