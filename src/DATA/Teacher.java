import java.util.HashMap;

public class Teacher implements People, Timeable	{

	private char ID;
	private String name;
	private Field[] fields;
	
	public Teacher(char aID, String aName, Field[] aFields)	{
	
		this.ID = aID;
		this.name = aName;
		this.fields = aFields;
	}
	
	public boolean canTeach(Field aField)	{
	
		for (int i = 0 ; i < this.fields.length ; i++)
			if (this.fields[i] == aField)
				return true;
		
		return false;
	}
	
	public String getMail()	{
	
		return (this.name.toLowerCase().replace(' ', '.') + "@insa-lyon.fr");
	}
	
	public String toString()	{
		return this.name;
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots(Time duration)	{
	
		return null;
	}
	
}
