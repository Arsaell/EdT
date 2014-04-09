package DATA;

import java.util.HashMap;

public class Classroom implements Timeable	{

	private int ID;
	private char type;
	private String name;
	private int effectif;
	private WeekTable timeTable;
	public static HashMap<Character, String> types;	//Les noms des types de salle
	
	
	static	{
	
		types = new HashMap<Character, String>();
		
		types.put((char)0, "TD");
		types.put((char)1, "Amphithéâtre");
		types.put((char)2, "TP Physique");
		types.put((char)3, "Labo Chimie");
		types.put((char)4, "TP Construction");
		types.put((char)5, "Salle Conception");
		types.put((char)6, "Salle Informatique");
		types.put((char)7, "Gymnase");
		types.put((char)8, "Glandouille pelouse");
	}
	
	
	public Classroom(int aID, char aType, String aName, int aEff)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
		this.effectif = aEff;
	}
	
	public char getType()	{
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
		return (types.get(this.type) + " " + this.name);
	}
}
