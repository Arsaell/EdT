package DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Classroom extends Timeable	{

	private int ID;
	private ClassType type;
	private String name;
	private int effectif;
	private WeekTable timeTable;
	
	public Classroom(int aID, ClassType aType, String aName, int aEff)	{
	
		super();
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
	
 	public String toString()	{
		return (this.type.getShortName() + " " + this.name);
	}
}
