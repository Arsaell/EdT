package DATA;

import java.util.HashMap;

public class Field extends Constrainable	{

	private int ID;
	private ClassType type;
	private String name;
	private Slot duration;	
	private int level;
	private HashMap<Character, Double> constraints; //Associer un type à un slot (minTime, maxTime)

	public Field(int aID, ClassType aType, String aName)	{

		this.ID = aID;
		this.type = aType;
		this.name = aName;
		this.duration = this.type.getDuration();
	}
	
	public ClassType getType() {
		return this.type;
	}

	public Field setName(String aName)	{
		this.name = aName;
		return this;
	}
	
	public Field setTimes (Slot aTimes)	{
		this.duration = aTimes;
		return this;
	}
	
	public String toString()	{
		return (this.type.getName() + " " + this.name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Slot getDuration()	{
		return this.duration;
	}
}
