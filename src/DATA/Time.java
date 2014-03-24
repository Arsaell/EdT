

public class Time	{

	private  byte day, hour, min;
	
	public Time (byte d, byte h, byte m)	{
	
		this.day = d;
		this.hour = h;
		this.min = m;
	}
	
	public Time (String s)	{
	
		if (s.length() >= 3)	{
		
			this.day = (byte) s.charAt(0);
			this.hour = (byte) s.charAt(1);
			this.min = (byte) s.charAt(2);
		}
		
		if (this.day > 6 || this.hour > 23 || this.min > 59 || s.length() < 3)
			System.out.println("Error while attempting t create a time from : " + s);
	}
	
	public void add(Time t)	{
	
		this.min += t.getMin();
		this.hour += t.getHour() + (byte)(int)(this.min / 60);
		this.min = (byte)(this.min % 60);
		this.day += t.getDay() + (byte)(int)(this.hour / 24);
		this.hour = (byte)(this.hour % 24);
		if (this.day > 6)
			System.out.println("Time.add() : changed week" + this + " " + t);
		this.day = (byte)(this.day % 7);
	}
	
	public Time substract(Time t)	{
	
		Time temp = new Time((byte)(this.min - t.min), (byte)(this.hour - t.hour - this.min / 59 + 1), (byte)(this.day - t.day - this.hour / 24 + 1));
		
		temp.min = (byte)(this.min % 60);
		temp.hour = (byte)(this.hour % 24);
		if (temp.day < 0)
			System.out.println("Time.substract() : changed week (==> negative result)");
		temp.day = (byte)(this.day % 7);
		
		return temp;
	}
	
	public String toString()	{
	
		return ("Time : " + (this.day == 0 ? "Mon " : (this.day == 1 ? "Tue " : (this.day == 2 ? "Wed " : (this.day == 3 ? "Thu " : (this.day == 4 ? "Fri " : (this.day == 5 ? "Sat " : (this.day == 6 ? "Sun " : "Unknown "))))))) + (int)this.hour + "h" + (int)this.min + "m");												
	}
	
	public byte getMin()	{
		return this.min;
	}
	
	public byte getHour()	{
		return this.hour;
	}
	
	public byte getDay()	{
		return this.day;
	}
}
