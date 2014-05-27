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
	
	public void removeLesson(Lesson l)	{
		int i = this.timeTable.getSlots().indexOf(l);
		this.timeTable.getSlots().remove(i);
		this.timeTable.getSlots().add(i, l.getSlot());
	}
	
	public WeekTable getWeekTable()	{
		return this.timeTable;
	}
}
