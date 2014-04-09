
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

public class Group implements Timeable, Constrainable, People	{

	private int ID;
	private String name;

	protected int effectif;
	public HashMap<Field, Teacher> teachers;
	public HashMap<Field, Time> classes;
	private HashMap<Field, Double> constraints;

	private Group parent;
	private Group[] children;
	
	/*
	static	{
		types.put((char)0, "PC");
		types.put((char)1, "PC1A");
		types.put((char)2, "PC2A");
		types.put((char)3, "PCC1A");
		types.put((char)4, "PCC2A");
		types.put((char)5, "Eur1A");
		types.put((char)6, "Eur2A");
		types.put((char)7, "Amer1A");
		types.put((char)8, "Amer2A");
		types.put((char)9, "As1A");
		types.put((char)10, "As2A");
		types.put((char)11, "LanIP1A");
		types.put((char)12, "LanIP2A");
		types.put((char)13, "FAS1A");
		types.put((char)14, "FAS2A");
		
	}
	*/
	
	public Group(int aID,String aName, int aEff)	{
		
		this.ID = aID;
		this.name = aName;
		this.effectif = aEff;
		this.classes = null;	//Implement
		this.teachers = new HashMap<Field, Teacher>();
		this.classes = new HashMap<Field, Time>();
		this.constraints = new HashMap<Field, Double>();
	}
	
		//Renvoie la première matière dans classes qui n'est pas attribuée dans teachers
		//(Pour l'attribution des profs)
	public Field getNextUnattributedClass()	{
	
		//System.out.println("Group.getNextUnattributedClass() : " + this + " " + this.classes);
		
		Iterator<Field> iter = this.classes.keySet().iterator();

		Field f;
		
		while (iter.hasNext())	{
			
			f = iter.next();		
			if (!this.teachers.containsKey(f))
				return f;
		}
		
		return null;
	}
	
	public boolean setTeacher (Teacher teach, Field f)	{
	
		//System.out.println("Group.setTeacher() : " + this + " " + teach + " " + f);
		
		if (teach != null && !this.teachers.containsKey(f) && teach.canTeach(f, this))	{
			this.teachers.put(f, teach);
			return true;
		}
		
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
		return "pcg" + Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
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
	
	public Group setClasses(HashMap<Field, Time> aClasses)	{
		
		//System.out.println("Group.setClasses() : " + this + " " + aClasses);
		
		if (aClasses != null)
			this.classes = aClasses;
		return this;
	}
	
	public Group setParent(Group aParent)	{
		
		this.parent = aParent;
		return this;
	}
	
	public String toString()	{
		
		String res = this.parent == null ? "" : this.parent.toString();
		res += " " + this.name;
		return res;
	}

	public Group setChildren(Group[] aChildren) {
		this.children = aChildren;
		return this;
	}
}
