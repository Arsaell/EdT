package DATA;

import java.util.HashMap;
import java.util.List;

public class Field implements Constrainable	{

	private int ID;
	private ClassType type;
	private String name;
	private Slot times;				//Associer un type à un slot (minTime, maxTime)
	private HashMap<Character, Double> constraints;
	
	public Field(int aID, ClassType aType, String aName)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
	}
	
	public ClassType getType() {
		return this.type;
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
	
	public Field setName(String aName)	{
		this.name = aName;
		return this;
	}
	
	public Field setTimes (Slot aTimes)	{
		this.times = aTimes;
		return this;
	}
	
	public String toString()	{
		return (this.type.getShortName() + " " + this.name);
	}
}
