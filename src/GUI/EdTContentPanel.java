package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EdTContentPanel extends JPanel {
	
	private JPanel monday;
	private JPanel tuesday;
	private JPanel wednesday;
	private JPanel thursday;
	private JPanel friday;
	private JPanel[] days;
	
	/*private JLabel emptyLabel;
	private JLabel mondayLabel;
	private JLabel tuesdayLabel;
	private JLabel wednesdayLabel;
	private JLabel thursdayLabel;
	private JLabel fridayLabel;*/
	
	
	private int dayWidth;
	private int panelHeight;
	
	public EdTContentPanel() {
		
		dayWidth = 200;
		panelHeight = 550;
		
		FlowLayout flowLayout = (FlowLayout) this.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		this.setPreferredSize(new Dimension(1005, panelHeight));
		
		monday = new JPanel();
		monday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//monday.setBackground(Color.yellow);
		monday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		FlowLayout mflowLayout = (FlowLayout) monday.getLayout();
		mflowLayout.setVgap(3);
		mflowLayout.setHgap(3);
		mflowLayout.setAlignment(FlowLayout.LEFT);
		this.add(monday);

		tuesday = new JPanel();
		tuesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//tuesday.setBackground(Color.yellow);
		tuesday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(tuesday);

		wednesday = new JPanel();
		wednesday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//wednesday.setBackground(Color.yellow);
		wednesday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(wednesday);

		thursday = new JPanel();
		thursday.setPreferredSize(new Dimension(dayWidth, panelHeight));
		//thursday.setBackground(Color.yellow);
		thursday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.black));
		this.add(thursday);

		friday = new JPanel();
		friday.setPreferredSize(new Dimension(dayWidth + 1, panelHeight));
		//friday.setBackground(Color.yellow);
		friday.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
		this.add(friday);
		
		days = new JPanel[]{monday, tuesday, wednesday, thursday, friday};
		fillEdT();
	}
	
	public void fillEdT() {
		for (int i = 0; i < days.length; i++) {
			if(i == 0) {
				JPanel empty = new JPanel();
				empty.setPreferredSize(new Dimension(dayWidth - 7, 17));
				days[i].add(empty);
				
				
				JPanel panel = new JPanel();
				panel.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel.setBackground(Color.white);
				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
				days[i].add(panel);

				JPanel panel2 = new JPanel();
				panel2.setPreferredSize(new Dimension(dayWidth - 7, 47));
				panel2.setBackground(Color.white);
				panel2.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
				days[i].add(panel2);
				
				JPanel panel3 = new JPanel();
				panel3.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel3.setBackground(Color.white);
				panel3.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
				days[i].add(panel3);
				
				JPanel panel4 = new JPanel();
				panel4.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel4.setBackground(Color.white);
				panel4.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
				days[i].add(panel4);
				JPanel panel5 = new JPanel();
				panel5.setPreferredSize(new Dimension(dayWidth - 7, 97));
				panel5.setBackground(Color.white);
				panel5.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
				days[i].add(panel5);
			}
		}
	}
}
