import java.util.HashMap;
import java.util.List;

public class Field implements Constrainable	{

	private char ID;
	private char type;
	private HashMap<Character, Double> constraints;
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
		this.names.put(this.type, aName);
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
	
	public String toString()	{
		return this.names.get(this.type);
	}
}
