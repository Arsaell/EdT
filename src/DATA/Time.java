package DATA;

public class Time	{

	private  byte day, hour, min;
	
	public Time (byte d, byte h, byte m)	{
	
		this.day = d;
		this.hour = h;
		this.min = m;
		
		/*
		if (this.day > 6 || this.hour > 23 || this.min > 59)
			System.out.println("Error while creating a Time : " + this);
		*/
		
		this.format();
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
	}
	
	public Time(int i)	{
		this((byte)(i/10000), (byte)((i%10000)/100), (byte)(i%100));
		//ex : new Time(52359);
		//			   5d 23h 59min
	}
 	
	public Time ()	{
		this(0);
	}
	
	public Time clone()	{
		return new Time(this.day, this.hour, this.min);
	}
	
	public Time add (Time t)	{
	
		//System.out.println("Time.add() : " + this + " " + t);
		
		Time temp = new Time((byte)(this.day + t.getDay()), (byte)(this.hour + t.hour), this.min);
		
		temp.format();
		
		if (temp.day > 6)
			System.out.println("Time.add() : changed week" + temp + " " + t);
		
		temp.day = (byte)(temp.day % 7);
		
		return temp;
	}
	
	public Time substract(Time t)	{
	

		int min = this.toMin() - t.toMin();
		
		Time temp = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		temp.format();
		
		return temp;
	}
	
	public Time multiplyBy(double x)	{

		int min = (int)(this.toMin() * x);
		
		Time t = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		t.format();
		
		return t;
	}
	
	public Time divideBy(double x)	{
		
		if (x == 0)
			return null;

		int min = (int)(this.toMin() / x);
		
		Time t = new Time((byte)(min / 1440), (byte)(min % 1440 / 60), (byte)(min % 60));
		
		if (x < 1)
			t.format();
		
		return t;
	}
	
	public double divideBy(Time t)	{
		if (t.equals(new Time()))
			return (Double) null;
		return this.toMin() / t.toMin();
	}
	
	public boolean isLessThan(Time t)	{
		return (this.toMin() < t.toMin());
	}
	
	public boolean isMoreThan(Time t)	{
		return (this.toMin() > t.toMin());
	}
	
	public boolean equals (Time t)	{
		this.format();
		t.format();
		return this.day == t.day && this.hour == t.hour && this.min == t.min;
	}
	
	//Gère les exceptions du type 25h --> 1day 1hour ; 92min --> 1h32min
	private void format()	{
		this.hour += this.min / 60;
		this.min = (byte) (this.min % 60);
		this.day += this.hour / 24;
		this.hour = (byte) (this.hour % 24);
		this.day = (byte) (this.day % 7);
	}
	
	//Renvoie l'équivalent de this en minutes.
	private int toMin()	{
		return this.day * 1440 + this.hour * 60 + this.min;
	}
	
	//Renvoie this dans le format utilisé par le constructeur new Time(int i);
	
	private int toInt()	{
		return this.day * 10000 + this.hour * 100 + this.min;
	}
	
	public String toString()	{
	
		return ((this.day == 0 ? "Mon " : (this.day == 1 ? "Tue " : (this.day == 2 ? "Wed " : (this.day == 3 ? "Thu " : (this.day == 4 ? "Fri " : (this.day == 5 ? "Sat " : (this.day == 6 ? "Sun " : "Unknown " + this.day + " "))))))) + (int)this.hour + "h" + (int)this.min + "m");												
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
