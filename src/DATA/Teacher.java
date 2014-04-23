package DATA;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Teacher extends Timeable implements People, Comparable<Teacher>	{

	private int ID;
	private String firstName, lastName;
	private Time maxWeekWorkedHours, currentWeekWorkedHours;
	private Field[] fields;
	private LinksList students;
	
	public Teacher(int aID, String aFirstName, String aLastName, Field[] aFields, Time aMWWH)	{
	
		this.ID = aID;
		this.firstName = aFirstName;
		this.lastName = aLastName;
		this.fields = aFields;
		this.maxWeekWorkedHours = aMWWH;
		
		this.students = new LinksList();
		this.currentWeekWorkedHours = new Time();
	}
	
	public boolean canTeach(Field aField, Group aGroup)	{
	
		//System.out.println("Teacher.canTeach() : " + this + " " + aField + " " + aGroup + " " + this.maxWeekWorkedHours.substract(this.currentWeekWorkedHours) + " " + aGroup.classes.get(aField));
		
		boolean res = false;
		
		if (aGroup.getClasses().get(aField).add(this.currentWeekWorkedHours).isMoreThan((this.maxWeekWorkedHours)))
			return false;
		
		for (int i = 0 ; i < this.fields.length ; i++)
			if (this.fields[i] == aField)
				res = true;
//		
//		if (res)
//			this.currentWeekWorkedHours = this.currentWeekWorkedHours.add(aGroup.getClasses().get(aField));
//		
		//System.out.println("Res (Teacher.canTeach()) : --> " + res);
		
		return res;
	}
	
	public boolean knows(Field aField)	{
		for (int i = 0 ; i < this.fields.length ; i++)
			if (this.fields[i] == aField)
				return true;
		return false;
	}
	
	public boolean linkGroup(Group g, Field f)	{
		return this.addLink(new Link(this, g, f));
	}
	
	public boolean addLink(Link link) {
		
		//	Links points to this teacher	Group has the field pointed by link						group doesn't have a teacher for the field
		if (link.getTeacher() == this && link.getGroup().getClasses().get(link.getField()) != null && link.getGroup().getLinks().getLinks(link.getField()).size() == 0)	{
			this.students.add(link);
			this.currentWeekWorkedHours = this.currentWeekWorkedHours.add(link.getGroup().getClasses().get(link.getField()));
			return true;
		}
		return false;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}
	
	public Field[] getFields()	{
		return this.fields;
	}

	public LinksList getLinks()	{
		return this.students;
	}
	
	public Time getMWWH() {
		return this.maxWeekWorkedHours;
	}
	
	public Time getCWWH()	{
		return this.currentWeekWorkedHours;
	}
	
	public Teacher setCWWH(Time aCWWH)	{
		this.currentWeekWorkedHours = aCWWH == null ? new Time() : aCWWH;
		return this;
	}
	
	public Teacher addToCWWH (Time t)	{
		if (t!= null)
			this.currentWeekWorkedHours = this.currentWeekWorkedHours.add(t);
		return this;
	}
	
	public String getFirstName()	{
		
		return this.firstName;
	}
	
	public String getLastName()	{
		
		return this.lastName;
	}
	
	public String getMail()	{
		
		return (this.firstName.toLowerCase() + "." + this.lastName.toLowerCase() + "@insa-lyon.fr");
	}
	
	public Teacher setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public Teacher setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public String toString()	{
		return this.firstName + " " + this.lastName;
	}
	
	public int compareTo(Teacher t) {
		return this.getName().toLowerCase().compareTo(t.getName().toLowerCase());
	}

}
