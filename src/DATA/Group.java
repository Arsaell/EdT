import java.util.HashMap;
import java.util.List;

public class Group implements Timeable, Constrainable, People	{

	private char ID;
	private String name;

	protected int effectif;
	private HashMap<Field, Teacher> teachers;
	private HashMap<Field, Time> classes;
	private HashMap<Field, Double> constraints;

	private Group parent;
	private Group[] children;
	
	public Group(char aID, int aEff)	{
		
		this.ID = aID;
		this.effectif = aEff;
		this.classes = null;	//Implement
	}
	
		//Renvoie la première matière dans classes qui n'est pas attribuée dans teachers
		//(Pour l'attribution des profs)
	public Field getNextUnattributedClass()	{
	
		for (Field f : this.classes.keySet())	{
		
			if (!teachers.containsKey(f))
				return f;
		}
		
		return null;
	}
	
	public boolean setTeacher (Teacher teach, Field f)	{
	
		if (teach != null && !this.teachers.containsKey(f) && teach.canTeach(f, this))	{
			this.teachers.put(f, teach);
			return true;
		}
		
		else
			return false;
	}
	
	public HashMap<Field, Double> getConstraint()	{
	
		this.updateConstraint();
		return this.constraints;
	}
	
	public HashMap<Field, Double> getConstraint(List source)	{
	
		return null;
	}
	
	private void updateConstraint()	{
	
	}
	
	public String getMail()	{
		return "pcg" ;//+ Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
	
	public HashMap<Field, Teacher> getTeachers()	{
		return this.teachers;
	}
	
	public HashMap<Field, Time> getClasses()	{
		return this.classes;
	}
	
	public Slot getNextFreeSlot(Time start, Time duration)	{
	
		return null;
	}
	
	public Slot[] getAllFreeSlots(Time duration)	{
	
		return null;
	}
	
}
