package GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DATA.WeekTable;

import java.awt.Color;
import java.awt.FlowLayout;

public class EdTViewerWindow {

	private JFrame frame;
	
	private EdTHourPanel hourPanel;
	private EdTDayPanel dayPanel;
	private EdTContentPanel contentPanel;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EdTViewerWindow window = new EdTViewerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public EdTViewerWindow(WeekTable wt) {
		initialize(wt);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(WeekTable wt) {
		frame = new JFrame();
		frame.setBounds(100, 100, 1056, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel classSelectorPanel = new JPanel();
		//classSelectorPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(classSelectorPanel, BorderLayout.NORTH);
		
		JPanel timetableLayoutPanel = new JPanel();
		timetableLayoutPanel.setPreferredSize(new Dimension(1056,800));
		JScrollPane scrollPane = new JScrollPane(timetableLayoutPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		timetableLayoutPanel.setLayout(new BorderLayout(0, 0));
		
		hourPanel = new EdTHourPanel();
		timetableLayoutPanel.add(hourPanel, BorderLayout.WEST);
		
		dayPanel = new EdTDayPanel();
		timetableLayoutPanel.add(dayPanel, BorderLayout.NORTH);
		
		contentPanel = new EdTContentPanel(wt);
		timetableLayoutPanel.add(contentPanel, BorderLayout.CENTER);
		
		frame.setTitle("Emploi du temps de : " + wt.getOwner().toString());
		frame.setVisible(true);
	}
	
	private void paintComponent(Graphics g) {
		
		g.setColor(Color.white);
		
		// On s'occupe des heures.
		
		
	}

}
