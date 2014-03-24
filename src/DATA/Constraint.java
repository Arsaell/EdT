

public class Constraint	{

	private char type;
	private Class object, subject;
	
	public Constraint (char aType, Class o, Class s)	{
	
		this.type = aType;
		this.object = o;
		this.subject = s;
	}
	
	public boolean canApply(object o, subject s)	{
	
		switch (this.type)	{
		
			default :
				return true;
				break;
		}
	}
}
