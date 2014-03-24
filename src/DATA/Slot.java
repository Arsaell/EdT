

public class Slot	{

	private Time begin, end;
	
	public Slot(Time b, Time e)	{
	
		this.begin = b;
		this.end = e;
	}
	
	public Time duration()	{
	
		return this.end.substract(this.begin);
	}
	
	public Time getBegin()	{
		return this.begin;
	}
	
	public Time getEnd()	{
		return this.end;
	}
	
	public void setBegin(Time aBegin)	{
		this.begin = aBegin;
	}
	
	public void setEnd(Time aEnd)	{
		this.end = aEnd;
	}
}