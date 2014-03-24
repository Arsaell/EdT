

public class Field	{

	private char ID;
	private char type;
	private String name;
	
	public Field(char aID, char aType, String aName)	{
	
		this.ID = aID;
		this.type = aType;
		this.name = aName;
	}
	
	public String toString()	{
		return this.name;
	}
}
