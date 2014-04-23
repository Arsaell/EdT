package DATA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

public class Group extends Timeable implements People	{

	private int ID;
	private String name;
	private int level;

	protected int effectif;
	public LinksList links;
	public HashMap<Field, Time> classes;
	private HashMap<Field, Double> constraints;
	private HashMap<Field, Boolean> done;

	private Group parent;
	private Group[] children;
	
	
	public Group(int aID,String aName, int aEff)	{
		
		this.ID = aID;
		this.name = aName;
		this.effectif = aEff;
		this.classes = null;	//Implement
		this.links = new LinksList();
		this.classes = new HashMap<Field, Time>();
		this.constraints = new HashMap<Field, Double>();
		this.done = new HashMap<Field, Boolean>();
	}
	
		//Renvoie la première matière dans classes qui n'est pas attribuée dans teachers
		//(Pour l'attribution des profs)
	public Field getNextUnattributedClass()	{
	
		//System.out.println("Group.getNextUnattributedClass() : " + this + " " + this.classes);
		
		Iterator<Field> iter = this.classes.keySet().iterator();

		Field f;
		
		while (iter.hasNext())	{
			
			f = iter.next();		
			if (this.links.getLinks(f).size() == 0)
				return f;
		}
		
		return null;
	}
	
	public boolean setTeacher (Teacher teach, Field f)	{
	
		//System.out.println("Group.setTeacher() : " + this + " " + teach + " " + f);
		
		if (teach != null && f != null && (this.links.getLinks(f).size() == 0) && teach.canTeach(f, this))	{
			this.links.add(new Link(teach, this, f));
			return true;
		}
		
		System.out.println("Group.setTeacher() : " + this + " " + teach + " " + f + " " + this.links.getLinks(f) + " " + teach.canTeach(f, this));
		
		return false;
	}
	
	public Teacher getTeacher(Field f)	{
		//System.out.println("Group.getTeacher(" + f + ") " + this + " " + this.links.size() + " " + this.links.getLinks(f));
		return (this.links.getLinks(f).size() != 0 ? this.links.getLinks(f).get(0).getTeacher() : null);
	}
	
	public boolean addLink(Link l)	{
		if (this.classes.containsKey(l.getField()) && this == l.getGroup() && this.links.getLinks(l.getField()).size() == 0)	{
			this.links.add(l);
			return true;
		}
		return false;
	}
	
	public String getMail()	{
		return "pcg" + Integer.toString((int)this.ID + 1) + "@insa-lyon.fr";
	}
	
	public int getEffectif()	{
		return this.effectif;
	}
	
	public LinksList getLinks()	{
		return this.links;
	}
	
	public HashMap<Field, Time> getClasses()	{
		return this.classes;
	}
	
	public Group setClasses(HashMap<Field, Time> aClasses)	{
		
		//System.out.println("Group.setClasses() : " + this + " " + aClasses);
		
		if (aClasses != null)
			this.classes = aClasses;
		
		for (Field f : this.classes.keySet())
			if (!this.done.containsKey(f))
				this.done.put(f, false);
		
		return this;
	}
	
	public Group setParent(Group aParent)	{
		
		this.parent = aParent;
		return this;
	}
	
	public String toString()	{
		
		return (this.parent == null ? "" : (this.parent.toString() + " ")) + this.name;
	}

	public Group[] getChildren()	{
		return this.children;
	}
	
	public Group setChildren(Group[] aChildren) {
		this.children = aChildren;
		return this;
	}

	
	public void printWeekTable() {
		this.timeTable.print();
	}

	public WeekTable getWeekTable() {
		return this.timeTable;
	}
}
