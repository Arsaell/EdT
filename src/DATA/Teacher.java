

public class Teacher extends People	{

	private String name;
	private Field[] fields;
	
	public Teacher(char aID, String aName, Field[] aFields)	{
	
		this.ID = aID;
		this.name = aName;
		this.fields = aFields;
	}
	
	public boolean canTeach(Field aField)	{
	
		for (Field f : this.fields)
			if (f == aField)
				return true;
		
		return false;
	}
	
	public String getMail()	{
	
		return (this.name.toLowerCase().replace(' ', '.') + "@insa-lyon.fr");
	}
	
	public String toString()	{
		return this.name;
	}
}
