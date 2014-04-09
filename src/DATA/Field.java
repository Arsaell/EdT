package DATA;

import java.util.HashMap;
import java.util.List;

public class Field implements Constrainable	{

	private int ID;
	private char type;
	private HashMap<Character, Double> constraints;
	public static HashMap<Character, Slot> times;				//Associer un type à un slot (minTime, maxTime)
	public static HashMap<Character, String> names;				//Associer un type à un nom (TD, TP, amphi ...)
	public static HashMap<Character, Character> classrooms;		//Associer un type de cours à un type de salle
	
	static	{
	
		times = new HashMap<Character, Slot>();
		names = new HashMap<Character, String>();
		classrooms = new HashMap<Character, Character>();
		
		//	[...]
	}
	
	public Field(int aID, char aType)	{
	
		this.ID = aID;
		this.type = aType;
	}
	
	public char getType() {
		return type;
	}

	public HashMap<Character, Double> getConstraint()	{
	
		this.updateConstraint();
		return this.constraints;
	}
	
	public HashMap<Character, Double> getConstraint(List source)	{
	
		return null;
	}
	
	private void updateConstraint()	{
	
	}
	
	public Field setClassroomType(char aType)	{
		
		classrooms.put(this.type, aType);
		return this;
	}
	
	public Field setName(String aName)	{
		names.put(this.type, aName);
		return this;
	}
	
	public Field setTimes (Slot aTimes)	{
		times.put(this.type, aTimes);
		return this;
	}
	
	public String toString()	{
		return names.get(this.getType());
	}
}
