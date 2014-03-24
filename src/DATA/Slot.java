

public class Slot	{

	private Time begin, end;
	
	public Slot(Time b, Time e)	{
	
		this.begin = b;
		this.end = e;
	}
	
	public Time duration()	{
	
		return this.end.substract(this.begin);
	}
}
