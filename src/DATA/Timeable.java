package DATA;
import java.util.ArrayList;



public abstract class Timeable extends Constrainable	{

	protected WeekTable timeTable;
	
	public Timeable()	{
		this.timeTable = new WeekTable(WeekTable.getDefault(), this);
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
		return this.timeTable.getNextFreeSlot(start, duration);
	}
	
	public ArrayList<Slot> getAllFreeSlots(Time duration)	{
		return this.timeTable.getAllFreeSlots(duration);
	}
	
	public boolean addLesson(Lesson l)	{
		return this.timeTable.addLesson(l);
	}
	
	public WeekTable getWeekTable()	{
		return this.timeTable;
	}
}
