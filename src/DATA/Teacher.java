package DATA;
import java.util.HashMap;
import java.util.List;

public class Teacher implements People, Timeable, Constrainable	{

	private int ID;
	private String name;
	private Time maxWeekWorkedHours, currentWeekWorkedHours;
	private Field[] fields;
	private HashMap<Group, Field> students;
	private HashMap<Field, Double> constraints;
	
	public Teacher(int aID, String aName, Field[] aFields, Time aMWWH)	{
	
		this.ID = aID;
		this.name = aName;
		this.fields = aFields;
		this.maxWeekWorkedHours = aMWWH;
		
		this.students = new HashMap<Group, Field>();
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
	
		return (this.name.toLowerCase().replace(' ', '.') + "@insa-lyon.fr");
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots(Time duration)	{
	
		return null;
	}
	
	public String toString()	{
		return this.name;
	}

	
	
}
