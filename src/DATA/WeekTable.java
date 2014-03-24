

public class WeekTable implements Timeable	{

	private Lesson[][] slots;
	private People owner;
	
	private static WeekTable defaultWeek;
	
	public WeekTable()	{
	
	}
	
	public WeekTable(WeekTable source)	{
		
	}
	
	public void setSlot (Lesson aSlot)	{
	
		//Changer la deusième dimension pour bien correspondre au slot (une méthode timeToSlot() ?)
		this.slots[aSlot.getSlot().getBegin().getDay()][aSlot.getSlot().getBegin().getHour()] = aSlot;
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots (Time duration)	{
	
		return null;
	}
	
	public void setDefault(WeekTable aDefault)	{
	
		defaultWeek = aDefault;
	}
	
	public static WeekTable getDefault()	{
	
		return defaultWeek;
	}
}
