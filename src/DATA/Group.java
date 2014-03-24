import java.util.HashMap;

public class Group implements Timeable, People	{

	private char ID;
	private String name;

	protected int effectif;
	private HashMap<Field, Character/*, Time*/> classes;

	private Group parent;
	private Group[] children;
	
	public Group(char aID, int aEff)	{
		
		this.ID = aID;
		this.effectif = aEff;
		this.classes = null;	//Implement
	}
	
	public String getMail()	{
		return "pcg" ;//+ Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
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
	
}
