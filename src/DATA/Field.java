import java.util.HashMap;

public class Field	{

	private char ID;
	private char type;
	private String name;
	private static HashMap<Character, Slot> times;		//Associer un type à un slot (minTime, maxTime)
	private static HashMap<Character, String> names;	//Associer un type à un nom (TD, TP, amphi ...)
	
	static	{
	
		times = new HashMap<Character, Slot>();
		names = new HashMap<Character, String>();
		
		//	[...]
	}
	
	public Field(char aID, char aType, String aName)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
	}
	
	public String toString()	{
		return this.name;
	}
}
