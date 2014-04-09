package DATA;

public class Time	{

	private  byte day, hour, min;
	
	public Time (byte d, byte h, byte m)	{
	
		this.day = d;
		this.hour = h;
		this.min = m;
		
		if (this.day > 6 || this.hour > 23 || this.min > 59)
			System.out.println("Error while creating a Time : " + this);
		//System.out.println("New Time : " + this);
	}
	
	public Time (String s)	{
	
		this ((byte)0, (byte)0, (byte)0);
		
		if (s.length() >= 3)	{
			this.day = (byte) s.charAt(0);
			this.hour = (byte) s.charAt(1);
			this.min = (byte) s.charAt(2);
		}
		
		else
			System.out.println("Error while creating Time from string : " + s);
		//System.out.println("New Time : " + this);
		
		if (this.day > 6 || this.hour > 23 || this.min > 59 || s.length() < 3)
			System.out.println("Error while attempting to create a Time from : " + s);
	}
	
	public Time(int i)	{
		this((byte)(i/10000), (byte)((i%10000)/100), (byte)(i%100));
		//ex : new Time(52359);
		//			   5d 23h 59min
	}
 	
	public Time ()	{
		this(0);
	}
	
	public Time add (Time t)	{
	
		//System.out.println("Time.add() : " + this + " " + t);
		
		Time temp = new Time(this.day, this.hour, this.min);
		
		temp.min += t.getMin();
		temp.hour += t.getHour() + (byte)(int)(temp.min / 60);
		temp.min = (byte)(temp.min % 60);
		temp.day += t.getDay() + (byte)(int)(temp.hour / 24);
		temp.hour = (byte)(temp.hour % 24);
		
		if (temp.day > 6)
			System.out.println("Time.add() : changed week" + temp + " " + t);
		
		temp.day = (byte)(temp.day % 7);
		
		return temp;
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
	
	public Time divideBy(double x)	{
		
		if (x == 0)
			return null;
		
		Time t = new Time((byte)(this.day / x), (byte)(this.hour / x), (byte)(this.min / x));
		
		if (x < 1)	{
			t.hour += t.min / 60;
			t.min = (byte) (t.min % 60);
			t.day += t.hour / 24;
			t.hour = (byte) (t.hour % 24);
			t.day = (byte) (t.day % 7);
		}
		
		return t;
	}
	
	public Time divideBy(Time t)	{
		return new Time((t.getDay() == 0 ? 0 : (byte)(this.day / t.getDay())), (t.getHour() == 0 ? 0 : (byte)(this.hour / t.getHour())), (t.getMin() == 0 ? 0 : (byte)(this.min / t.getMin())));
	}
	
	public boolean isLessThan(Time t)	{
		return ((1440 * this.day + 60 * this.hour + this.min) < (1440 * t.getDay() + 60 * t.getHour() + t.getMin()));
	}
	
	public boolean isMoreThan(Time t)	{
		return ((1440 * this.day + 60 * this.hour + this.min) > (1440 * t.getDay() + 60 * t.getHour() + t.getMin()));
	}
	
	public String toString()	{
	
		return ("Time : " + (this.day == 0 ? "Mon " : (this.day == 1 ? "Tue " : (this.day == 2 ? "Wed " : (this.day == 3 ? "Thu " : (this.day == 4 ? "Fri " : (this.day == 5 ? "Sat " : (this.day == 6 ? "Sun " : "Unknown " + this.day + " "))))))) + (int)this.hour + "h" + (int)this.min + "m");												
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
