

public class Classroom	{

	private char ID;
	private char type;
	private String name;
	private int effectif;
	
	public Classroom(char aID, char aType, String aName, int aEff)	{
	
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
}
