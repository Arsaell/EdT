

public interface Timeable	{

	WeekTable timeTable = WeekTable.getDefault();//.copy();
	
	public Slot getNextFreeSlot(Time start, Time duration);
	public Slot[] getAllFreeSlots(Time duration);
	
	
}
