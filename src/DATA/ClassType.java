package DATA;

public class ClassType {
	
	private String name, shortName;
	
	public ClassType(String aName, String aShortName)	{
		
		this.name = aName;
		this.shortName = aShortName;
	}

	public String getName()	{
		return this.name;
	}
	
	public String getShortName()	{
		return this.shortName;
	}
	
	public String toString()	{
		return this.name + " (" + this.shortName + ")";
	}
}
