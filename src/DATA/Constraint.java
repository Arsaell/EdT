


public class Constraint	{

	private char type;
	private Class object, subject;
	
	public Constraint (char aType, Class o, Class s)	{
	
		this.type = aType;
		this.object = o;
		this.subject = s;
	}
	
	public boolean canApply(Object o, Object s)	{
	
		//if (o instanceof object && s instanceof subject)	{
			switch (this.type)	{
		
				default :
					return true;
			}
		//}
		//return true;
	}
}
