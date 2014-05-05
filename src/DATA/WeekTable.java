package DATA;

import java.util.ArrayList;



public class WeekTable {
	
	private ArrayList<Slot> slots;
	private Timeable owner;

	private static Time minDelta = new Time(1);
	private static WeekTable defaultWeek;
	
	public WeekTable(Timeable aOwner)	{
	
		this(defaultWeek, aOwner);
	}
	
	public WeekTable(ArrayList<Slot> aSlots, Timeable aOwner)	{
		this.slots = aSlots;
		this.owner = aOwner;
	}
	
	public WeekTable(WeekTable source, Timeable aOwner)	{
	
		this.owner = aOwner;
		this.slots = new ArrayList<Slot>();
		
		for (Slot s : source.slots)
			this.slots.add(s.clone());
	}
	
	public void setSlot(Slot aSlot)	{
	
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		if (this.indexForTime(start) == -1)	{
			
			System.out.println("\n\n\t\t##########\n\n\tWeekTable.getNextFreeSlot(Time start) : Starting time out of table !\n\n\t\t##########\n\n");
			return null;
		}
		
		Slot curr = null;
		
		for (int i = this.indexForTime(start) ; i < this.slots.size() ; i++)	{
			
			curr = this.slots.get(i);
			
			if (!(curr instanceof Lesson) && curr.getDuration().isntLessThan(duration))	{
				return curr;
			}
		}
		
		return null;
	}
	
	public ArrayList<Slot> getAllFreeSlots (Time duration)	{
	
		//System.out.println("\n\tWeekTable.getAllFreeSlots() : " + duration + "\n" + this.slots);
		
		ArrayList<Slot> res = new ArrayList<Slot>();
		Time current = this.slots.get(0).getBegin();
		
		for (Slot s : this.slots)	{
			//System.out.println("WeekTable.getAllFreeSlots() : " + s);
			if (!(s instanceof Lesson) && s.getDuration().isntLessThan(duration))	{
				//System.out.println("\t\t\t\t\t\t\t was added");
				res.add(s);
			}
		}
		
		return res;
	}
	
	public ArrayList<Lesson> getSlotsConcerning(Constrainable c)	{
		
		ArrayList<Lesson> res = new ArrayList<Lesson>();
		
		for (Slot s : this.slots)
			if (s instanceof Lesson)	{
				Lesson l = (Lesson) s;
				if (l.getField().equals(c) || l.getPlace().equals(c) || l.getStudents().equals(c) || l.getTeacher().equals(c) || l.getType().equals(c))
					res.add(l);
				
			}
		/*
		for (Slot s : res)	{
			System.out.println("WeekTable.getSlotsConcerning( " + c + " ) --> " + s);
		}
		*/
		return res;
	}

	public boolean fieldHappensInDay(Field f, byte day)	{
		
		//System.out.print("WeekTable<" + this.owner + ">.fieldHappensInDay (" + f + ", " + day + ") ");
		for (Slot s : this.slots)	{
			if (s instanceof Lesson && ((Lesson) s).getField().equals(f) && s.getBegin().getDay() == day)	{
				//System.out.println("true.");
				return true;
			}
		}
		//System.out.println("false");
		return false;
	}
	
	public boolean addLesson(Lesson l)	{
		
		int i = this.indexForTime(l.getBegin());
		Slot s = this.slots.get(i);
		
		//System.out.println("WeekTable.addLesson() : " + this.owner + "\n" + l + "\n\n\t" + i + " " + s);
		
		if (!(s instanceof Lesson) && s.getEnd().isntLessThan(l.getEnd()))	{
			
			this.slots.remove(i);
			
			//System.out.println("WeekTable.addLesson() #1 : " + this.slots.size());
			
			if (!l.getBegin().equals(s.getBegin()))	{
				Slot begin = new Slot(s.getBegin(), l.getBegin());
				this.slots.add(i, begin);
				i++;
				
				//System.out.println("WeekTable.addLesson()  #2 : " + begin);
			}
			
			this.slots.add(i, l);
			
			if (l.getEnd().isLessThan(s.getEnd()))	{
				Slot end = new Slot(l.getEnd(), s.getEnd());
				this.slots.add(i + 1, end);
				
				//System.out.println("WeekTable.addLesson()  #3 : " + end);
			}
			
			//System.out.println("WeekTable.addLesson() : Lesson added ! " + this.slots.size());
			
			return true;
		}
		
		return false;
	}
	
	private int  indexForTime(Time t)	{
		
		int i;
		
		for (i = 0 ; i < this.slots.size(); i++)	{
			if (this.slots.get(i).getBegin().isLessThan(t) && this.slots.get(i).getEnd().isMoreThan(t) || this.slots.get(i).getBegin().equals(t) || this.slots.get(i).getBegin().isMoreThan(t))
				return i;
		}
		return -1;
	}
	
	public static void setDefault(WeekTable aDefault)	{
	
		defaultWeek = aDefault;
	}
	
	public static WeekTable getDefault()	{
	
		return defaultWeek;
	}

	public static void setMinDelta(Time aMD)	{
		minDelta = aMD != null ? aMD : new Time();
	}
	
	public static Time getMinDelta()	{
		return minDelta;
	}

	public void print() {
		
		System.out.println("\n\tWeektable : " + this.owner + "\n");
		int currentDay = 0;
		for (Slot s : this.slots)	{
			
			if ((int)s.getBegin().getDay() > currentDay)	{
				System.out.println("");
				currentDay = (int) s.getBegin().getDay();
			}
			
			if (s instanceof Lesson)	{
				System.out.print(((Lesson)s).getSlot() + " : " + ((Lesson)s).getField() + "\t");
			}
			else
				System.out.print(s + "\t");
		}
	}
}
