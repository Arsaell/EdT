package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class StartFrame extends JFrame {

	private JFrame frame;

	
	public StartFrame() {
		super();
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBorder(new LineBorder(Color.RED, 1, true));
		tabbedPane.addTab("Fields", new FieldPanel());
		tabbedPane.addTab("Teachers", new TeacherPanel());
		tabbedPane.addTab("Classrooms", new ClassroomPanel());
		tabbedPane.addTab("Groups", new GroupPanel());
		tabbedPane.setSelectedIndex(0);
		
		getContentPane().add(tabbedPane);
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
