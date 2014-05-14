package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;

import net.miginfocom.swing.MigLayout;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import DATA.ClassType;
import DATA.Field;
import DATA.Slot;
import DATA.Time;

import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JSpinner;

public class FieldPanel extends JPanel {
	
	private ArrayList<ClassType> classtypes;
	private ArrayList<Field> fields;
	
	private JTextField textField;
	private JTextField textField_1;
	private JSpinner textField_2;
	private JSpinner textField_3;
	private JSpinner textField_4;
	private JSpinner textField_5;
	private JTextField textField_6;
	private JList list_1;
	/**
	 * Create the panel.
	 */
	public FieldPanel() {

		super();
		
		this.classtypes = new ArrayList<ClassType>();
		this.fields = new ArrayList<Field>();
		
		this.setMinimumSize(new Dimension(450, 325));
		setLayout(new MigLayout("", "[450px]", "[67px][67px][67px][67px]"));
		
		JLabel lblTypes = new JLabel("Types");
		add(lblTypes, "cell 0 0,grow");
		
		JSplitPane splitPane_1 = new JSplitPane();
		add(splitPane_1, "cell 0 1,grow");
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);
		panel_1.setLayout(new GridLayout(5, 3, 10, 10));
		
		JLabel lblNom = new JLabel("Nom");
		panel_1.add(lblNom);
		
		textField_1 = new JTextField();
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblType_1 = new JLabel("Type de Salle");
		panel_1.add(lblType_1);
		
		textField_6 = new JTextField();
		panel_1.add(textField_6);
		
		JLabel lblTempsMinimum = new JLabel("Temps Minimum");
		panel_1.add(lblTempsMinimum);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		textField_2 = new JSpinner();
		panel_2.add(textField_2);
		
		JLabel lblHeures = new JLabel("heures");
		panel_2.add(lblHeures);
		
		textField_3 = new JSpinner();
		panel_2.add(textField_3);
		
		JLabel lblMinutes = new JLabel("minutes");
		panel_2.add(lblMinutes);
		
		JLabel lblTempsMaximum = new JLabel("Temps Maximum");
		panel_1.add(lblTempsMaximum);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		textField_4 = new JSpinner();
		panel_3.add(textField_4);
		
		JLabel lblHeures_1 = new JLabel("heures");
		panel_3.add(lblHeures_1);
		
		textField_5 = new JSpinner();
		panel_3.add(textField_5);
		
		JLabel lblMinutes_1 = new JLabel("minutes");
		panel_3.add(lblMinutes_1);
		
		JLabel label = new JLabel("");
		panel_1.add(label);
		
		JButton btnAjouter_1 = new JButton("Ajouter");
		btnAjouter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textField_1.getText();
				String shortName = textField_6.getText();
				int hmin = (Integer) textField_2.getValue();
				int mmin = (Integer) textField_3.getValue();
				int hmax = (Integer) textField_4.getValue();
				int mmax = (Integer) textField_5.getValue();
				
				try	{
					
					ClassType ct = new ClassType(name, shortName, new Slot(new Time(hmin * 100 + mmin), new Time(hmax * 100 + mmax)));
					classtypes.add(ct);
					
					textField_1.setText("");
					textField_6.setText("");
					textField_2.setValue(0);
					textField_3.setValue(0);
					textField_4.setValue(0);
					textField_5.setValue(0);
					
				} catch (Exception e)	{
					e.printStackTrace();
					System.out.println("GUI.FieldPanel : tried to add an unavalid ClassType");
				}
			}
		});
		panel_1.add(btnAjouter_1);
		
		JPanel panel_4 = new JPanel();
		splitPane_1.setLeftComponent(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		list_1 = new JList();
		panel_4.add(new JScrollPane(list_1), BorderLayout.NORTH);
		list_1.setBorder(new LineBorder(Color.RED, 1, true));
		
		ListModel alm = new AbstractListModel() {
			Object[] values = classtypes.toArray();
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
			
		};
		
		list_1.setModel(alm);
		
		JButton btnEnlever = new JButton("Enlever");
		panel_4.add(btnEnlever, BorderLayout.SOUTH);
		
		JLabel lblMatires = new JLabel("Matières");
		add(lblMatires, "cell 0 2,grow");
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, "cell 0 3,grow");
		
		JList list = new JList();
		list.setBorder(new LineBorder(Color.RED, 1, true));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Maths Cours", "Maths TD", "Physique Cours", "Physique TD"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		splitPane.setLeftComponent(new JScrollPane(list));
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new GridLayout(3, 3, 10, 10));
		
		JLabel lblNewLabel = new JLabel("Nom");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		panel.add(lblType);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		panel.add(comboBox);
		
		JButton btnAjouter = new JButton("Ajouter");
		panel.add(btnAjouter);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{list, textField}));
	}

}
