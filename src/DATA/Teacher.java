import java.util.HashMap;
import java.util.List;

public class Teacher implements People, Timeable, Constrainable	{

	private char ID;
	private String name;
	private Time maxWeekWorkedHours, currentWeekWorkedHours;
	private Field[] fields;
	private HashMap<Group, Field> students;
	private HashMap<Field, Double> constraints;
	
	public Teacher(char aID, String aName, Field[] aFields)	{
	
		this.ID = aID;
		this.name = aName;
		this.fields = aFields;
	}
	
	public boolean canTeach(Field aField, Group aGroup)	{
	
		boolean res = false;
		for (int i = 0 ; i < this.fields.length ; i++)
			if (this.fields[i] == aField)
				res = true;
		
		if (!(res && aGroup.getClasses().get(aField).add(this.currentWeekWorkedHours).isLessThan((this.maxWeekWorkedHours))))
			res = false;
		
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
