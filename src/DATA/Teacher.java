package DATA;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements People, Timeable, Constrainable	{

	private int ID;
	private String firstName, lastName;
	private Time maxWeekWorkedHours, currentWeekWorkedHours;
	private Field[] fields;
	private ArrayList<Link> students;
	private HashMap<Field, Double> constraints;
	
	public Teacher(int aID, String aFirstName, String aLastName, Field[] aFields, Time aMWWH)	{
	
		this.ID = aID;
		this.firstName = aFirstName;
		this.lastName = aLastName;
		this.fields = aFields;
		this.maxWeekWorkedHours = aMWWH;
		
		this.students = new ArrayList<Link>();
		this.constraints = new HashMap<Field, Double>();
		this.currentWeekWorkedHours = new Time((byte) 0, (byte) 0, (byte) 0);
	}
	
	public boolean canTeach(Field aField, Group aGroup)	{
	
		//System.out.println("Teacher.canTeach() : " + this + " " + aField + " " + aGroup);
		
		boolean res = false;
		
		if (aGroup.getClasses().get(aField).add(this.currentWeekWorkedHours).isMoreThan((this.maxWeekWorkedHours)))
			return false;
		
		for (int i = 0 ; i < this.fields.length ; i++)
			if (this.fields[i] == aField)
				res = true;
		
		if (res)
			this.currentWeekWorkedHours = this.currentWeekWorkedHours.add(aGroup.getClasses().get(aField));
		
		//System.out.println("Res (Teacher.canTeach()) : --> " + res);
		
		return res;
	}
	
	public boolean linkGroup(Group g, Field f)	{
		return this.linkGroup(new Link(this, g, f));
	}
	
	private boolean linkGroup(Link link) {
		
		//	Links points to this teacher	Group has the field pointed by link						group doesn't have a teacher for the field
		if (link.getTeach() == this && link.getGroup().getClasses().get(link.getField()) != null && link.getGroup().getTeachers().get(link.getField()) == null)	{
			this.students.add(link);
			return true;
		}
		return false;
	}

	public HashMap<Field, Double> getConstraint()	{
	
		this.updateConstraint();
		return this.constraints;
	}
	
	public HashMap<Character, Double> getConstraint(List source)	{
	
		return null;
	}
	
	private void updateConstraint()	{
	
	}
	
	public Field[] getFields()	{
		return this.fields;
	}

	public Time getMWWH() {
		return this.maxWeekWorkedHours;
	}
	
	public Time getCWWH()	{
		return this.currentWeekWorkedHours;
	}
	
	public String getMail()	{
	
		return (this.firstName.toLowerCase() + "." + this.lastName + "@insa-lyon.fr");
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots(Time duration)	{
	
		return null;
	}
	
	public String toString()	{
		return this.firstName + " " + this.lastName;
	}	
	
}
