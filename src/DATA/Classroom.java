package DATA;

import java.util.HashMap;

public class Classroom implements Timeable	{

	private int ID;
	private ClassType type;
	private String name;
	private int effectif;
	private WeekTable timeTable;
	
	public Classroom(int aID, ClassType aType, String aName, int aEff)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
		this.effectif = aEff;
	}
	
	public ClassType getType()	{
		return this.type;
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots(Time duration)	{
	
		return null;
	}
	
 	public String toString()	{
		return (this.type.getName() + " " + this.name);
	}
}
